package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

/**
 * متتبع الصلوات اليومي
 * يخزن تتبع اليوم في State (في الذاكرة)
 * في الإصدار الكامل، يُحفظ في Room Database
 */
data class PrayerDay(
    val date: String,
    val fajr: Boolean = false,
    val dhuhr: Boolean = false,
    val asr: Boolean = false,
    val maghrib: Boolean = false,
    val isha: Boolean = false,
    val fajrJamaah: Boolean = false,
    val dhuhrJamaah: Boolean = false,
    val asrJamaah: Boolean = false,
    val maghribJamaah: Boolean = false,
    val ishaJamaah: Boolean = false
) {
    val totalPrayed: Int
        get() = listOf(fajr, dhuhr, asr, maghrib, isha).count { it }
    val totalJamaah: Int
        get() = listOf(fajrJamaah, dhuhrJamaah, asrJamaah, maghribJamaah, ishaJamaah).count { it }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTrackerView(onBack: () -> Unit) {
    // In-memory tracker (last 7 days)
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val dayFormat = remember { SimpleDateFormat("EEEE", Locale("ar")) }

    var today by remember {
        mutableStateOf(
            PrayerDay(date = dateFormat.format(Date()))
        )
    }

    val weekData = remember {
        val calendar = Calendar.getInstance()
        (0..6).map { offset ->
            calendar.add(Calendar.DAY_OF_YEAR, -offset)
            PrayerDay(
                date = dateFormat.format(calendar.time),
                fajr = offset != 0,
                dhuhr = offset != 0,
                asr = offset != 0,
                maghrib = offset != 0,
                isha = offset != 0
            )
        }.reversed()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("متتبع الصلوات اليومي", fontWeight = FontWeight.Bold, color = Color.White) },
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
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Today's progress
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D4A30))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "اليوم: ${dayFormat.format(Date())}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "${today.totalPrayed} / 5 صلوات",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC9A84C)
                    )
                    Text(
                        "${today.totalJamaah} صلاة في جماعة",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { today.totalPrayed / 5f },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFC9A84C),
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "صلوات اليوم:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Prayer checkboxes
            PrayerCheckbox("🌅 الفجر", today.fajr) {
                today = today.copy(fajr = it, fajrJamaah = it && today.fajrJamaah)
            }
            PrayerCheckboxWithJamaah(
                "☀️ الظهر",
                today.dhuhr,
                today.dhuhrJamaah,
                onPrayerChange = { today = today.copy(dhuhr = it) },
                onJamaahChange = { today = today.copy(dhuhrJamaah = it) }
            )
            PrayerCheckboxWithJamaah(
                "🌤️ العصر",
                today.asr,
                today.asrJamaah,
                onPrayerChange = { today = today.copy(asr = it) },
                onJamaahChange = { today = today.copy(asrJamaah = it) }
            )
            PrayerCheckboxWithJamaah(
                "🌅 المغرب",
                today.maghrib,
                today.maghribJamaah,
                onPrayerChange = { today = today.copy(maghrib = it) },
                onJamaahChange = { today = today.copy(maghribJamaah = it) }
            )
            PrayerCheckboxWithJamaah(
                "🌙 العشاء",
                today.isha,
                today.ishaJamaah,
                onPrayerChange = { today = today.copy(isha = it) },
                onJamaahChange = { today = today.copy(ishaJamaah = it) }
            )

            Spacer(Modifier.height(20.dp))

            // Weekly stats
            Text(
                "📊 إحصائيات الأسبوع:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val weekTotal = weekData.sumOf { it.totalPrayed }
            val weekJamaah = weekData.sumOf { it.totalJamaah }
            val weekMax = 5 * 7

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("الصلوات المؤداة:", fontWeight = FontWeight.Bold)
                        Text("$weekTotal / $weekMax")
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("في الجماعة:", fontWeight = FontWeight.Bold)
                        Text("$weekJamaah / $weekMax", color = Color(0xFF0D4A30))
                    }
                    Spacer(Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { weekTotal.toFloat() / weekMax },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF0D4A30)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Daily breakdown chart
            Text(
                "تفصيل الأيام:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            weekData.forEach { day ->
                val dayCalendar = Calendar.getInstance().apply {
                    time = dateFormat.parse(day.date) ?: Date()
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (day.date == today.date)
                            Color(0xFF0D4A30).copy(alpha = 0.1f)
                        else Color.Transparent
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            dayFormat.format(dayCalendar.time).take(3),
                            modifier = Modifier.width(60.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "${day.totalPrayed}/5",
                            modifier = Modifier.width(50.dp),
                            fontSize = 14.sp
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .background(
                                    Color.Gray.copy(alpha = 0.2f),
                                    shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(fraction = day.totalPrayed / 5f)
                                    .background(
                                        Color(0xFF0D4A30),
                                        shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PrayerCheckbox(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onCheckedChange(!checked) }) {
            Icon(
                if (checked) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (checked) Color(0xFF0D4A30) else Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }
        Text(label, fontSize = 16.sp, fontWeight = if (checked) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
fun PrayerCheckboxWithJamaah(
    label: String,
    prayerChecked: Boolean,
    jamaahChecked: Boolean,
    onPrayerChange: (Boolean) -> Unit,
    onJamaahChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onPrayerChange(!prayerChecked) }) {
            Icon(
                if (prayerChecked) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (prayerChecked) Color(0xFF0D4A30) else Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }
        Text(label, fontSize = 16.sp, fontWeight = if (prayerChecked) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f))

        if (prayerChecked) {
            FilterChip(
                selected = jamaahChecked,
                onClick = { onJamaahChange(!jamaahChecked) },
                label = { Text("جماعة", fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF0D4A30),
                    selectedLabelColor = Color.White
                )
            )
        }
    }
}
