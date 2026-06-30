package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

/**
 * متتبع الصيام
 * - الأيام البيض (13، 14، 15 من كل شهر هجري)
 * - الإثنين والخميس
 * - أيام الست من شوال
 * - عاشوراء وعاشوراء + يوم
 * - صيام داود (يوم ويوم)
 */

data class FastingDay(
    val date: String,
    val hijriDate: String,
    val type: String, // white_days, monday_thursday, shawwal, ashura, david
    val completed: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiteDaysFastingView(onBack: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val fastingDays = remember { mutableStateListOf<FastingDay>() }
    val currentMonth = Calendar.getInstance()

    // Generate sample fasting days for current month
    LaunchedEffect(Unit) {
        // White days (13, 14, 15)
        for (day in 13..15) {
            val cal = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, day) }
            fastingDays.add(FastingDay(
                date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time),
                hijriDate = "13-15 الشهر",
                type = "white_days"
            ))
        }
        // Monday and Thursday
        for (week in 0..3) {
            for (dayOfWeek in listOf(Calendar.MONDAY, Calendar.THURSDAY)) {
                val cal = Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_WEEK, dayOfWeek)
                    add(Calendar.WEEK_OF_MONTH, week)
                }
                if (cal.timeInMillis >= System.currentTimeMillis() - 86400000L * 30) {
                    fastingDays.add(FastingDay(
                        date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time),
                        hijriDate = SimpleDateFormat("EEEE", Locale("ar")).format(cal.time),
                        type = "monday_thursday"
                    ))
                }
            }
        }
        // Sort by date
        fastingDays.sortBy { it.date }
    }

    val completed = fastingDays.count { it.completed }
    val total = fastingDays.size
    val whiteDaysCompleted = fastingDays.count { it.type == "white_days" && it.completed }
    val mondayThursdayCompleted = fastingDays.count { it.type == "monday_thursday" && it.completed }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🌙 متتبع الصيام", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "رجوع", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B5E20))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header stats
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("📊 إحصائيات الصيام", fontSize = 14.sp, color = Color(0xFFC9A84C))
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "$completed / $total",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text("يوم صيام", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))

                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatItem("الأيام البيض", "$whiteDaysCompleted/3", Color(0xFFC9A84C))
                        StatItem("الإثنين/الخميس", "$mondayThursdayCompleted", Color.White)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Types of Sunnah fasting
            Text(
                "📚 أنواع صيام السنة:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            FastingTypeCard(
                "🌕 الأيام البيض",
                "13، 14، 15 من كل شهر هجري",
                "سنة مؤكدة، تكفر ذنوب السنة",
                Color(0xFFFFF3E0),
                Color(0xFFE65100)
            )
            FastingTypeCard(
                "📅 الإثنين والخميس",
                "كل إثنين وخميس من الأسبوع",
                "أحب الأعمال إلى الله",
                Color(0xFFE8F5E9),
                Color(0xFF1B5E20)
            )
            FastingTypeCard(
                "🌸 ستة من شوال",
                "6 أيام من شهر شوال",
                "كأنك صامت السنة كاملة",
                Color(0xFFFCE4EC),
                Color(0xFFC2185B)
            )
            FastingTypeCard(
                "🌙 عاشوراء",
                "9 و10 محرم",
                "يكفر ذنوب السنة السابقة",
                Color(0xFFE3F2FD),
                Color(0xFF1565C0)
            )
            FastingTypeCard(
                "⚖️ صيام داود",
                "يوم صيام ويوم إفطار",
                "أفضل الصيام عند الله",
                Color(0xFFF3E5F5),
                Color(0xFF6A1B9A)
            )

            Spacer(Modifier.height(16.dp))

            // Days list
            Text(
                "📅 الأيام القادمة:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            fastingDays.forEach { day ->
                FastingDayItem(
                    day = day,
                    onToggle = { isCompleted ->
                        val index = fastingDays.indexOf(day)
                        if (index >= 0) {
                            fastingDays[index] = day.copy(completed = isCompleted)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
        Text(label, fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
    }
}

@Composable
fun FastingTypeCard(
    title: String,
    days: String,
    benefit: String,
    bgColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = textColor)
            Text(days, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
            Text(
                benefit,
                fontSize = 11.sp,
                color = textColor.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun FastingDayItem(day: FastingDay, onToggle: (Boolean) -> Unit) {
    val typeLabel = when (day.type) {
        "white_days" -> "🌕 يوم أبيض"
        "monday_thursday" -> "📅 ${day.hijriDate}"
        "shawwal" -> "🌸 من شوال"
        "ashura" -> "🌙 عاشوراء"
        "david" -> "⚖️ صيام داود"
        else -> day.type
    }

    val typeColor = when (day.type) {
        "white_days" -> Color(0xFFFF6F00)
        "monday_thursday" -> Color(0xFF1B5E20)
        "shawwal" -> Color(0xFFC2185B)
        "ashura" -> Color(0xFF1565C0)
        "david" -> Color(0xFF6A1B9A)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (day.completed) Color(0xFFE8F5E9) else Color(0xFFFAFAFA)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onToggle(!day.completed) }) {
                Icon(
                    if (day.completed) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = if (day.completed) Color(0xFF1B5E20) else Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(typeLabel, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = typeColor)
                Text(
                    day.date,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
            if (day.completed) {
                Text("✅ صُمت", fontSize = 11.sp, color = Color(0xFF1B5E20), fontWeight = FontWeight.Bold)
            }
        }
    }
}
