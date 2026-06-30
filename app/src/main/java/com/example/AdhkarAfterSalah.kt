package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
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

/**
 * أذكار بعد كل صلاة
 * 5 مجموعات (فجر، ظهر، عصر، مغرب، عشاء)
 * + أذكار صباحية ومسائية
 * + أذكار النوم والاستيقاظ
 */

data class DhikrItem(
    val id: Int,
    val text: String,           // نص الذكر
    val count: Int,             // العدد
    val benefit: String,        // الفضل
    val source: String = "صحيح البخاري ومسلم",
    val category: String = "عام"
)

/**
 * أذكار بعد الصلوات الخمس
 * مبنية على الأحاديث الصحيحة من البخاري ومسلم
 */
object AdhkarAfterSalahData {
    val afterFajr = listOf(
        DhikrItem(
            id = 1,
            text = "أَسْتَغْفِرُ اللَّهَ (ثلاثًا)، اللَّهُمَّ أَنْتَ السَّلَامُ، وَمِنْكَ السَّلَامُ، تَبَارَكْتَ يَا ذَا الْجَلَالِ وَالْإِكْرَامِ.",
            count = 1,
            benefit = "من قالها بعد صلاة الفجر غُفرت ذنوبه ولو كانت مثل زبد البحر",
            source = "صحيح مسلم",
            category = "بعد الفجر"
        ),
        DhikrItem(
            id = 2,
            text = "لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ.",
            count = 10,
            benefit = "كانت له عدل عشر رقاب، وكُتب له مئة حسنة، ومحيت عنه مئة سيئة",
            source = "صحيح البخاري ومسلم",
            category = "بعد الفجر"
        ),
        DhikrItem(
            id = 3,
            text = "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْجَنَّةَ وَأَعُوذُ بِكَ مِنَ النَّارِ.",
            count = 7,
            benefit = "من قالها كان له ما سأل إن شاء الله",
            source = "صحيح أبي داود",
            category = "بعد الفجر"
        ),
        DhikrItem(
            id = 4,
            text = "اللَّهُمَّ بِكَ أَصْبَحْنَا، وَبِكَ أَمْسَيْنَا، وَبِكَ نَحْيَا، وَبِكَ نَمُوتُ، وَإِلَيْكَ النُّشُورُ.",
            count = 1,
            benefit = "أذكار الصباح بعد الفجر",
            source = "الترمذي",
            category = "بعد الفجر"
        )
    )

    val afterDhuhr = listOf(
        DhikrItem(
            id = 11,
            text = "أَسْتَغْفِرُ اللَّهَ (ثلاثًا)، اللَّهُمَّ أَنْتَ السَّلَامُ، وَمِنْكَ السَّلَامُ، تَبَارَكْتَ يَا ذَا الْجَلَالِ وَالْإِكْرَامِ.",
            count = 1,
            benefit = "من قالها بعد كل صلاة غُفرت ذنوبه",
            source = "صحيح مسلم",
            category = "بعد الظهر"
        ),
        DhikrItem(
            id = 12,
            text = "لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ.",
            count = 10,
            benefit = "تعدل مئة حسنة وتمحو مئة سيئة",
            source = "صحيح البخاري ومسلم",
            category = "بعد الظهر"
        ),
        DhikrItem(
            id = 13,
            text = "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْجَنَّةَ وَأَعُوذُ بِكَ مِنَ النَّارِ.",
            count = 7,
            benefit = "حفظ من النار إن شاء الله",
            source = "صحيح أبي داود",
            category = "بعد الظهر"
        )
    )

    val afterAsr = listOf(
        DhikrItem(
            id = 21,
            text = "أَسْتَغْفِرُ اللَّهَ (ثلاثًا)، اللَّهُمَّ أَنْتَ السَّلَامُ، وَمِنْكَ السَّلَامُ، تَبَارَكْتَ يَا ذَا الْجَلَالِ وَالْإِكْرَامِ.",
            count = 1,
            benefit = "من السنة بعد كل صلاة",
            source = "صحيح مسلم",
            category = "بعد العصر"
        ),
        DhikrItem(
            id = 22,
            text = "لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ.",
            count = 10,
            benefit = "تعدل عتق عشر رقاب",
            source = "صحيح البخاري ومسلم",
            category = "بعد العصر"
        ),
        DhikrItem(
            id = 23,
            text = "أَعُوذُ بِاللَّهِ مِنَ النَّارِ.",
            count = 7,
            benefit = "الاستعاذة من النار بعد العصر",
            source = "صحيح الترمذي",
            category = "بعد العصر"
        )
    )

    val afterMaghrib = listOf(
        DhikrItem(
            id = 31,
            text = "أَسْتَغْفِرُ اللَّهَ (ثلاثًا)، اللَّهُمَّ أَنْتَ السَّلَامُ، وَمِنْكَ السَّلَامُ، تَبَارَكْتَ يَا ذَا الْجَلَالِ وَالْإِكْرَامِ.",
            count = 1,
            benefit = "سنة بعد كل صلاة",
            source = "صحيح مسلم",
            category = "بعد المغرب"
        ),
        DhikrItem(
            id = 32,
            text = "لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ.",
            count = 10,
            benefit = "فضل عظيم",
            source = "صحيح البخاري ومسلم",
            category = "بعد المغرب"
        ),
        DhikrItem(
            id = 33,
            text = "اللَّهُمَّ بِكَ أَمْسَيْنَا، وَبِكَ أَصْبَحْنَا، وَبِكَ نَحْيَا، وَبِكَ نَمُوتُ، وَإِلَيْكَ الْمَصِيرُ.",
            count = 1,
            benefit = "أذكار المساء",
            source = "الترمذي",
            category = "بعد المغرب"
        )
    )

    val afterIsha = listOf(
        DhikrItem(
            id = 41,
            text = "أَسْتَغْفِرُ اللَّهَ (ثلاثًا)، اللَّهُمَّ أَنْتَ السَّلَامُ، وَمِنْكَ السَّلَامُ، تَبَارَكْتَ يَا ذَا الْجَلَالِ وَالْإِكْرَامِ.",
            count = 1,
            benefit = "سنة بعد كل صلاة",
            source = "صحيح مسلم",
            category = "بعد العشاء"
        ),
        DhikrItem(
            id = 42,
            text = "لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ.",
            count = 10,
            benefit = "فضل عظيم",
            source = "صحيح البخاري ومسلم",
            category = "بعد العشاء"
        ),
        DhikrItem(
            id = 43,
            text = "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْجَنَّةَ وَأَعُوذُ بِكَ مِنَ النَّارِ.",
            count = 7,
            benefit = "طلب الجنة والاستعاذة من النار",
            source = "صحيح أبي داود",
            category = "بعد العشاء"
        ),
        DhikrItem(
            id = 44,
            text = "آيَةُ الْكُرْسِيِّ: اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ لَهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلَّا بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلَا يُحِيطُونَ بِشَيْءٍ مِنْ عِلْمِهِ إِلَّا بِمَا شَاءَ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ وَلَا يَئُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ.",
            count = 1,
            benefit = "من قرأها دبر كل صلاة لم يمنعه من دخول الجنة إلا أن يموت",
            source = "صحيح الترمذي",
            category = "بعد العشاء"
        )
    )

    val generalAfterSalah = listOf(
        DhikrItem(
            id = 100,
            text = "سُبْحَانَ اللَّهِ",
            count = 33,
            benefit = "من قالها بعد كل صلاة حُطت خطاياه ولو كانت مثل زبد البحر",
            source = "صحيح مسلم",
            category = "عام"
        ),
        DhikrItem(
            id = 101,
            text = "الْحَمْدُ لِلَّهِ",
            count = 33,
            benefit = "مع التسبيح، تمام المئة",
            source = "صحيح مسلم",
            category = "عام"
        ),
        DhikrItem(
            id = 102,
            text = "اللَّهُ أَكْبَرُ",
            count = 34,
            benefit = "تمام المئة بذكر الله أكبر",
            source = "صحيح مسلم",
            category = "عام"
        ),
        DhikrItem(
            id = 103,
            text = "اللَّهُمَّ لاَ مَانِعَ لِمَا أَعْطَيْتَ، وَلاَ مُعْطِيَ لِمَا مَنَعْتَ، وَلاَ يَنْفَعُ ذَا الْجَدِّ مِنْكَ الْجَدُّ.",
            count = 1,
            benefit = "من قالها حُفظ من الشرك",
            source = "صحيح البخاري ومسلم",
            category = "عام"
        ),
        DhikrItem(
            id = 104,
            text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ، قُلْ هُوَ اللَّهُ أَحَدٌ، اللَّهُ الصَّمَدُ، لَمْ يَلِدْ وَلَمْ يُولَدْ، وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ.",
            count = 3,
            benefit = "من قالها بعد كل صلاة كفته من كل شيء",
            source = "أبو داود",
            category = "عام"
        ),
        DhikrItem(
            id = 105,
            text = "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ.",
            count = 1,
            benefit = "الاستعاذة من الشرور",
            source = "أبو داود",
            category = "عام"
        ),
        DhikrItem(
            id = 106,
            text = "قُلْ أَعُوذُ بِرَبِّ النَّاسِ.",
            count = 1,
            benefit = "الاستعاذة من وسوسة الشيطان",
            source = "أبو داود",
            category = "عام"
        )
    )

    fun getAfterPrayer(prayerName: String): List<DhikrItem> {
        return when (prayerName) {
            "الفجر" -> afterFajr + generalAfterSalah
            "الظهر" -> afterDhuhr + generalAfterSalah
            "العصر" -> afterAsr + generalAfterSalah
            "المغرب" -> afterMaghrib + generalAfterSalah
            "العشاء" -> afterIsha + generalAfterSalah
            else -> generalAfterSalah
        }
    }
}

/**
 * View لعرض أذكار بعد الصلاة
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdhkarAfterSalahView(prayerName: String, onBack: () -> Unit) {
    val context = LocalContext.current
    val adhkar = remember { AdhkarAfterSalahData.getAfterPrayer(prayerName) }
    val completedDhikr = remember { mutableStateListOf<Int>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🤲 أذكار بعد صلاة $prayerName", fontWeight = FontWeight.Bold, color = Color.White) },
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
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D4A30))
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🤲 ${adhkar.size} ذكر", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("من السنة النبوية الشريفة", fontSize = 12.sp, color = Color(0xFFC9A84C))
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { completedDhikr.size.toFloat() / adhkar.size },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFC9A84C),
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )
                    Text(
                        "${completedDhikr.size} من ${adhkar.size}",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            adhkar.forEach { dhikr ->
                AdhkarCard(
                    dhikr = dhikr,
                    isCompleted = dhikr.id in completedDhikr,
                    onComplete = {
                        if (dhikr.id in completedDhikr) completedDhikr.remove(dhikr.id)
                        else completedDhikr.add(dhikr.id)
                    },
                    onShare = {
                        ShareHelper.shareDua(context, dhikr.text, "${dhikr.source} - ${dhikr.category}")
                    }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun AdhkarCard(
    dhikr: DhikrItem,
    isCompleted: Boolean,
    onComplete: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) Color(0xFFE8F5E9) else Color(0xFFFAFAFA)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Count badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color(0xFF0D4A30),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        "${dhikr.count}x",
                        color = Color(0xFFC9A84C),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
                IconButton(onClick = { onComplete() }) {
                    Icon(
                        if (isCompleted) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint = if (isCompleted) Color(0xFFC9A84C) else Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Dhikr text
            Text(
                dhikr.text,
                fontSize = 17.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Benefit
            Surface(
                color = Color(0xFFFFF8E1),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        "💎 الفضل:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100)
                    )
                    Text(
                        dhikr.benefit,
                        fontSize = 12.sp,
                        color = Color(0xFF6D4C00),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        "📚 ${dhikr.source}",
                        fontSize = 10.sp,
                        color = Color(0xFF6D4C00).copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            IconButton(
                onClick = { onShare() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Share, null, tint = Color(0xFF0D4A30))
            }
        }
    }
}
