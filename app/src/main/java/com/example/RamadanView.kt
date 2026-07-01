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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

/**
 * ميزات رمضان الكاملة:
 * - إمساكية (جدول يومي للإمساك والإفطار)
 * - تراويح (عداد ركعات)
 * - قيام الليل
 * - اعتكاف (مدة، أذكار)
 * - الختم في رمضان
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RamadanView(onBack: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("إمساكية", "التراويح", "القيام", "الاعتكاف", "الختم")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🌙 رمضان كريم", fontWeight = FontWeight.Bold, color = Color.White) },
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
        ) {
            // Countdown banner
            RamadanCountdownCard()

            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFF1B5E20).copy(alpha = 0.05f)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontSize = 12.sp) }
                    )
                }
            }

            // Content based on tab
            when (selectedTab) {
                0 -> ImsakiyaView()
                1 -> TarawihView()
                2 -> QiyamView()
                3 -> ItikafView()
                4 -> KhatmaTrackerView()
            }
        }
    }
}

@Composable
fun RamadanCountdownCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFC9A84C), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("🌙", fontSize = 28.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "رمضان قريب",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "استعد لرمضان بالذكر والتلاوة",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
            Surface(
                color = Color(0xFFC9A84C),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "يوم 1",
                    modifier = Modifier.padding(8.dp),
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * الإمساكية - جدول يومي
 */
@Composable
fun ImsakiyaView() {
    val prayer = PrayerCacheEntity(
        dateKey = "today",
        city = "الجزائر",
        Fajr = "05:12",
        Sunrise = "06:34",
        Dhuhr = "12:45",
        Asr = "16:08",
        Maghrib = "18:56",
        Isha = "20:18",
        hijriDate = "1 رمضان 1447",
        gregorianDate = "18 مارس 2026"
    )

    val imsakTime = "05:02"  // 10 دقائق قبل الفجر
    val iftarTime = prayer.Maghrib.split(" ")[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "📅 إمساكية اليوم - ${prayer.hijriDate}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "${prayer.gregorianDate} • ${prayer.city}",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        // Imsak card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🌅 الإمساك", fontSize = 14.sp, color = Color(0xFFE65100))
                Text(imsakTime, fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                Text("قبل الفجر بـ 10 دقائق", fontSize = 11.sp, color = Color.Gray)
            }
        }

        Spacer(Modifier.height(12.dp))

        // Iftar card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🌇 الإفطار", fontSize = 14.sp, color = Color(0xFFC9A84C))
                Text(iftarTime, fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("عند أذان المغرب", fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("🕐 مواقيت الصلاة:", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(8.dp))

        ImsakiyaRow("🌅 الفجر", prayer.Fajr.split(" ")[0], "صلاة الفجر")
        ImsakiyaRow("🌄 الشروق", prayer.Sunrise.split(" ")[0], "وقت الشروق")
        ImsakiyaRow("☀️ الظهر", prayer.Dhuhr.split(" ")[0], "صلاة الظهر")
        ImsakiyaRow("🌤️ العصر", prayer.Asr.split(" ")[0], "صلاة العصر")
        ImsakiyaRow("🌇 المغرب", prayer.Maghrib.split(" ")[0], "إفطار الصائم")
        ImsakiyaRow("🌙 العشاء", prayer.Isha.split(" ")[0], "صلاة العشاء")

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("💡 آداب الصيام:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6D4C00))
                Text(
                    "• السحور بركة، لا تتركه ولو بشربة ماء\n" +
                    "• تعجيل الفطر عند غروب الشمس\n" +
                    "• ادعُ قبل الإفطار (دعاء الصائم لا يُرد)\n" +
                    "• أكثر من الاستغفار وقراءة القرآن\n" +
                    "• تجنب الغيبة والنميمة والكلام السيء",
                    fontSize = 12.sp,
                    color = Color(0xFF6D4C00),
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}

@Composable
fun ImsakiyaRow(name: String, time: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(description, fontSize = 11.sp, color = Color.Gray)
            }
            Text(time, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D4A30))
        }
    }
}

/**
 * التراويح - عداد الركعات
 */
@Composable
fun TarawihView() {
    var rakats by remember { mutableStateOf(0) }
    val target = 20  // 20 ركعة (الأكثر شيوعاً)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🌙 صلاة التراويح", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("$rakats / $target ركعة", fontSize = 12.sp, color = Color.Gray)

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(180.dp)
                .background(Color(0xFF1B5E20), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "$rakats",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text("ركعة", fontSize = 14.sp, color = Color(0xFFC9A84C))
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FilledTonalButton(
                onClick = { if (rakats > 0) rakats-- }
            ) {
                Icon(Icons.Default.Remove, null)
                Spacer(Modifier.width(4.dp))
                Text("إنقاص")
            }
            Button(
                onClick = { if (rakats < target) rakats++ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
            ) {
                Text("زيادة")
                Spacer(Modifier.width(4.dp))
                Icon(Icons.Default.Add, null)
            }
        }

        Spacer(Modifier.height(12.dp))

        TextButton(onClick = { rakats = 0 }) {
            Text("إعادة تعيين")
        }

        Spacer(Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = { rakats / target.toFloat() },
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFC9A84C)
        )

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("📜 التراويح:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6D4C00))
                Text(
                    "• 20 ركعة مع الوتر (الأكثر عند الجمهور)\n" +
                    "• أو 8 ركعات مع الوتر (المالكية)\n" +
                    "• كل 4 ركعات بتسليم = وحدة\n" +
                    "• يستحب ختم القرآن في رمضان",
                    fontSize = 12.sp,
                    color = Color(0xFF6D4C00),
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}

/**
 * قيام الليل
 */
@Composable
fun QiyamView() {
    var rakats by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🌙 قيام الليل", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("صلاة الليل بعد العشاء أو قبل الفجر", fontSize = 12.sp, color = Color.Gray)

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("كيفية صلاة الليل:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1B5E20))
                Spacer(Modifier.height(8.dp))
                Text(
                    "1. صلاة العشاء (فريضة)\n" +
                    "2. الوتر (ركعة أو أكثر)\n" +
                    "3. التهجد (ركعتين ركعتين)\n" +
                    "4. أفضلها في الثلث الأخير من الليل\n" +
                    "5. ينتهي بطلوع الفجر",
                    fontSize = 13.sp,
                    color = Color(0xFF1B5E20)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Counter
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("عداد الركعات", fontSize = 12.sp, color = Color(0xFFC9A84C))
                Text("$rakats", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { if (rakats > 0) rakats -= 2 },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("-2")
                    }
                    Button(
                        onClick = { rakats += 2 },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC9A84C))
                    ) {
                        Text("+2 ركعتين", color = Color(0xFF1B5E20))
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("🤲 دعاء قيام الليل:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                Text(
                    "اللهم إنك عفوٌّ تحب العفو فاعفُ عنّي",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6D4C00),
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    "كان النبي ﷺ يكثر من الدعاء والاستغفار في الثلث الأخير",
                    fontSize = 11.sp,
                    color = Color(0xFF6D4C00),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * الاعتكاف
 */
@Composable
fun ItikafView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("🕌 الاعتكاف", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("اللبث في المسجد لعبادة الله", fontSize = 12.sp, color = Color.Gray)

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("ما هو الاعتكاف؟", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFC9A84C))
                Spacer(Modifier.height(8.dp))
                Text(
                    "هو البقاء في المسجد بنيّة التعبد لله، مع الامتناع عن المباحات. " +
                    "سنة مؤكدة في العشر الأواخر من رمضان، يُرجى فيها ليلة القدر.",
                    fontSize = 13.sp,
                    color = Color.White
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("📋 أحكام الاعتكاف:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1B5E20))
                Spacer(Modifier.height(8.dp))
                Text(
                    "• يكون في مسجد تقام فيه الجماعة\n" +
                    "• لا يخرج إلا لحاجة (كقضاء الحاجة)\n" +
                    "• يستحب الإكثار من القرآن والذكر\n" +
                    "• مباح للمعتكف الأكل والشرب في المسجد\n" +
                    "• يُسن الاعتكاف في العشر الأواخر من رمضان\n" +
                    "• لو اعتكف يوماً أو يومين أو أكثر أجزأ",
                    fontSize = 12.sp,
                    color = Color(0xFF1B5E20)
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("⚠️ ما يبطل الاعتكاف:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFFE65100))
                Text(
                    "• الجماع (يُبطله)\n" +
                    "• الخروج من المسجد لغير حاجة\n" +
                    "• الحيض والنفاس (للنساء)\n" +
                    "• الجنون والإغماء",
                    fontSize = 12.sp,
                    color = Color(0xFF6D4C00),
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}

/**
 * ختم القرآن في رمضان
 */
@Composable
fun KhatmaTrackerView() {
    var currentJuz by remember { mutableStateOf(1) }
    val targetJuz = 30

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("📖 ختمة رمضان", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("قراءة القرآن كاملاً خلال رمضان", fontSize = 12.sp, color = Color.Gray)

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("الجزء الحالي", fontSize = 12.sp, color = Color(0xFFC9A84C))
                Text("الجزء $currentJuz من $targetJuz", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { currentJuz / targetJuz.toFloat() },
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFC9A84C),
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = { if (currentJuz > 1) currentJuz-- },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.ChevronRight, null)
                Text("  السابق")
            }
            Button(
                onClick = { if (currentJuz < targetJuz) currentJuz++ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                modifier = Modifier.weight(1f)
            ) {
                Text("التالي  ")
                Icon(Icons.Default.ChevronLeft, null)
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("💡 جدول مقترح للختم:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF6D4C00))
                Spacer(Modifier.height(8.dp))
                Text(
                    "• جزء واحد كل يوم (الأفضل)\n" +
                    "• أو نصف جزء = جزءين يومياً = 15 يوماً\n" +
                    "• أو ربع جزء = 4 يومياً = ختمتين في رمضان\n" +
                    "• 20 صفحة = جزء تقريباً\n" +
                    "• أفضل وقت بعد الفجر أو قبل المغرب",
                    fontSize = 12.sp,
                    color = Color(0xFF6D4C00)
                )
            }
        }
    }
}
