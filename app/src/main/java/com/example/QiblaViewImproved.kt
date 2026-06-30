package com.example

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.roundToInt

/**
 * بوصلة القبلة المحسّنة
 * - تستخدم TYPE_ROTATION_VECTOR الحديث
 * - تتحرك بسلاسة مع حركة التليفون
 * - تظهر إذا كنت موجّه للقبلة
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QiblaViewImproved(
    viewModel: IslamicViewModel,
    settings: SettingsEntity,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // State for sensor heading (0-360)
    var deviceHeading by remember { mutableStateOf(0f) }

    // State for Qibla angle (0-360)
    var qiblaAngle by remember { mutableStateOf(0f) }
    var isCalibrated by remember { mutableStateOf(false) }

    // Calculate Qibla angle once
    LaunchedEffect(settings.latitude, settings.longitude) {
        val compass = QiblaCompassManager(context) { }
        qiblaAngle = compass.calculateQiblaAngle(settings.latitude, settings.longitude)
        isCalibrated = true
    }

    // Setup sensor listener
    DisposableEffect(Unit) {
        val compassManager = QiblaCompassManager(context) { heading ->
            deviceHeading = heading
        }
        compassManager.start()

        onDispose {
            compassManager.stop()
        }
    }

    // Smooth animations
    val animatedHeading by animateFloatAsState(
        targetValue = deviceHeading,
        animationSpec = tween(durationMillis = 200),
        label = "heading"
    )

    val animatedQibla by animateFloatAsState(
        targetValue = qiblaAngle,
        animationSpec = tween(durationMillis = 500),
        label = "qibla"
    )

    // Calculate difference
    val diff = ((deviceHeading - qiblaAngle + 540f) % 360f) - 180f
    val isAligned = kotlin.math.abs(diff) <= 5f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("اتجاه القبلة", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "رجوع", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D4A30))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Info
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "مكة المكرمة • ${qiblaAngle.roundToInt()}° من الشمال",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "📍 ${settings.city}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Compass
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .background(
                        if (isAligned) Color(0xFF0D4A30).copy(alpha = 0.1f)
                        else Color.Gray.copy(alpha = 0.05f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // The compass rotates so North always points up
                CompassDial(
                    heading = animatedHeading,
                    qiblaAngle = animatedQibla,
                    isAligned = isAligned
                )
            }

            // Status
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (isAligned) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF0D4A30),
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        "✅ أنت موجّه للقبلة",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D4A30)
                    )
                } else {
                    val direction = if (diff > 0) "← يسار" else "يمين →"
                    Text(
                        "↻ حرّك الهاتف ${kotlin.math.abs(diff).roundToInt()}° $direction",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC9A84C)
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    "اتجاه الجهاز: ${deviceHeading.roundToInt()}° • الزاوية: ${diff.roundToInt()}°",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            // Tips
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        "💡 نصائح للمعايرة:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0xFF6D4C00)
                    )
                    Text(
                        "• ابتعد عن المعادن والمغناطيس\n" +
                        "• حرّك التليفون بحركة 8\n" +
                        "• تأكد أن البوصلة تتغير مع حركة التليفون",
                        fontSize = 11.sp,
                        color = Color(0xFF6D4C00)
                    )
                }
            }
        }
    }
}

@Composable
fun CompassDial(
    heading: Float,
    qiblaAngle: Float,
    isAligned: Boolean
) {
    val density = LocalDensity.current
    val primaryColor = Color(0xFF0D4A30)
    val qiblaColor = Color(0xFFC9A84C)
    val arrowColor = if (isAligned) Color(0xFF0D4A30) else Color(0xFFE53935)
    val tickColor = Color.Gray

    Box(modifier = Modifier.fillMaxSize()) {
        // Outer ring with cardinal directions
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val outerRadius = size.minDimension / 2 * 0.95f
            val cardinalRadius = outerRadius * 0.85f

            // Outer circle
            drawCircle(
                color = primaryColor.copy(alpha = 0.3f),
                radius = outerRadius,
                center = center,
                style = Stroke(width = 4f)
            )

            // Draw cardinal directions (N, E, S, W) - they rotate with device
            rotate(degrees = -heading, pivot = center) {
                // North (Red) - always points to true north
                drawLine(
                    color = Color.Red,
                    start = Offset(center.x, center.y - outerRadius * 0.92f),
                    end = Offset(center.x, center.y - outerRadius * 0.75f),
                    strokeWidth = 6f
                )
                // East, South, West ticks
                for (angle in 0 until 360 step 15) {
                    val isMajor = angle % 90 == 0
                    val isMid = angle % 45 == 0
                    val length = when {
                        isMajor -> outerRadius * 0.18f
                        isMid -> outerRadius * 0.10f
                        else -> outerRadius * 0.05f
                    }
                    val rad = Math.toRadians(angle.toDouble())
                    val innerR = outerRadius * 0.92f
                    val outerR = outerRadius * 0.92f - length
                    drawLine(
                        color = if (isMajor) Color.Black else tickColor,
                        start = Offset(
                            (center.x + innerR * sin(rad)).toFloat(),
                            (center.y - innerR * cos(rad)).toFloat()
                        ),
                        end = Offset(
                            (center.x + outerR * sin(rad)).toFloat(),
                            (center.y - outerR * cos(rad)).toFloat()
                        ),
                        strokeWidth = if (isMajor) 4f else 2f
                    )
                }
            }

            // Qibla arrow (doesn't rotate with device, but moves to point to Mecca)
            // The arrow needs to point to the qibla direction relative to North
            val relativeQiblaAngle = qiblaAngle - heading
            rotate(degrees = -relativeQiblaAngle, pivot = center) {
                // Qibla marker
                val qiblaPath = androidx.compose.ui.graphics.Path().apply {
                    moveTo(center.x, center.y - outerRadius * 0.7f)
                    lineTo(center.x - 20f, center.y - outerRadius * 0.55f)
                    lineTo(center.x + 20f, center.y - outerRadius * 0.55f)
                    close()
                }
                drawPath(
                    path = qiblaPath,
                    color = qiblaColor
                )
            }

            // Center dot
            drawCircle(
                color = primaryColor,
                radius = 8f,
                center = center
            )
        }

        // Text overlay (stays fixed)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                "N",
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
