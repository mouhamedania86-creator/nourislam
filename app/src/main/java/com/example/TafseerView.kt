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
 * بيانات التفسير
 */
data class TafseerEntry(
    val surahId: Int,
    val surahName: String,
    val ayahNumber: Int,
    val ayahText: String,
    val tafseer: String,
    val source: String = "التفسير الميسر"
)

/**
 * تفسير السور الأساسية
 * تفسير مختصر من التفسير الميسر (وزارة الشؤون الإسلامية السعودية)
 */
object TafseerData {
    val entries = listOf(
        // === سورة الفاتحة ===
        TafseerEntry(
            surahId = 1, surahName = "الفاتحة", ayahNumber = 1,
            ayahText = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
            tafseer = "أبدأ مستعينًا باسم الله العظيم القادر على كل شيء، الرحمن الذي وسعت رحمته جميع الخلق، الرحيم الذي خص بها المؤمنين يوم القيامة.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 1, surahName = "الفاتحة", ayahNumber = 2,
            ayahText = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
            tafseer = "جميع المحامد والثناء بصفات الكمال لله وحده، المالك المربي لجميع المخلوقات بنعمه العظيمة، الظاهرة والباطنة.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 1, surahName = "الفاتحة", ayahNumber = 3,
            ayahText = "الرَّحْمَٰنِ الرَّحِيمِ",
            tafseer = "الرحمن الذي وسعت رحمته في الدنيا جميع الخلائق مؤمنهم وكافرهم، الرحيم الذي يرحم المؤمنين يوم القيامة.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 1, surahName = "الفاتحة", ayahNumber = 4,
            ayahText = "مَالِكِ يَوْمِ الدِّينِ",
            tafseer = "هو سبحانه المالك المتصرف وحده يوم القيامة، يوم الحساب والجزاء، يفصل فيه بين الخلائق بأحكامه العادلة.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 1, surahName = "الفاتحة", ayahNumber = 5,
            ayahText = "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ",
            tafseer = "نخصك وحدك بالعبادة، ونستعين بك وحدك في جميع أمورنا، فلا نعبد إلا إياك، ولا نستعين إلا بك.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 1, surahName = "الفاتحة", ayahNumber = 6,
            ayahText = "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ",
            tafseer = "دُلنا وأرشدنا ووفقنا للطريقة المستقيمة، وهي طريق الإسلام الذي ارتضيته لعبادك، الموصلة إلى رضوانك وجنتك.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 1, surahName = "الفاتحة", ayahNumber = 7,
            ayahText = "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
            tafseer = "طريق الذين أنعمت عليهم من النبيين والصدقين والشهداء والصالحين، لا طريق من علم الحق فحاد عنه ولا من جهله فضل.",
            source = "التفسير الميسر"
        ),

        // === سورة الإخلاص ===
        TafseerEntry(
            surahId = 112, surahName = "الإخلاص", ayahNumber = 1,
            ayahText = "قُلْ هُوَ اللَّهُ أَحَدٌ",
            tafseer = "قل: هو الله المتفرد بالإلهية، الواحد المعبود بحق، الذي لا شريك له ولا نظير ولا مثيل.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 112, surahName = "الإخلاص", ayahNumber = 2,
            ayahText = "اللَّهُ الصَّمَدُ",
            tafseer = "هو الله الذي تصمد إليه الخلائق في حاجاتها، الذي يُقصد إليه في الشدائد، الكامل في صفاته الذي لا يحتاج إلى أحد ويحتاج إليه كل أحد.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 112, surahName = "الإخلاص", ayahNumber = 3,
            ayahText = "لَمْ يَلِدْ وَلَمْ يُولَدْ",
            tafseer = "لم يخرج منه ولد ولا والد، لم ينشأ من شيء ولم ينشأ منه شيء، فهو الأول الذي ليس قبله شيء والآخر الذي ليس بعده شيء.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 112, surahName = "الإخلاص", ayahNumber = 4,
            ayahText = "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
            tafseer = "ليس لله مماثل ولا مكافئ في ذاته ولا في صفاته ولا في أفعاله، فهو المنفرد بالملك المطلق والكمال المطلق.",
            source = "التفسير الميسر"
        ),

        // === سورة الفلق ===
        TafseerEntry(
            surahId = 113, surahName = "الفلق", ayahNumber = 1,
            ayahText = "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ",
            tafseer = "قل: أعتصم وأستجير وأستنجد برب الصبح وفالق الإصباح من الظلام، الذي خلق كل شيء من العدم.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 113, surahName = "الفلق", ayahNumber = 2,
            ayahText = "مِن شَرِّ مَا خَلَقَ",
            tafseer = "من شر جميع المخلوقات وأذاها، ولا سيما التي قد تتأذى بها العباد.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 113, surahName = "الفلق", ayahNumber = 3,
            ayahText = "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ",
            tafseer = "ومن شر الليل إذا أظلم ودخل، وما فيه من المؤذيات، فإن كثيراً من الشر يحدث بالليل.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 113, surahName = "الفلق", ayahNumber = 4,
            ayahText = "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ",
            tafseer = "ومن شر السواحر اللاتي ينفثن في العقد لقصد السحر، فإن النفث في العقد من أعمال السحرة.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 113, surahName = "الفلق", ayahNumber = 5,
            ayahText = "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ",
            tafseer = "ومن شر الحاسد إذا أظهر حسده وعمل بمقتضاه، فإن الحاسد قد يضر بالحسد بالعين أو بالقلب أو باللسان أو باليد.",
            source = "التفسير الميسر"
        ),

        // === سورة الناس ===
        TafseerEntry(
            surahId = 114, surahName = "الناس", ayahNumber = 1,
            ayahText = "قُلْ أَعُوذُ بِرَبِّ النَّاسِ",
            tafseer = "قل: أعتصم وأستجير وأستنجد برب الناس ومالكهم وسيدهم ومدبر أمورهم.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 114, surahName = "الناس", ayahNumber = 2,
            ayahText = "مَلِكِ النَّاسِ",
            tafseer = "المالك المتصرف في الناس بكامل السلطان، يفعل فيهم ما يشاء بحكمته وعلمه.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 114, surahName = "الناس", ayahNumber = 3,
            ayahText = "إِلَٰهِ النَّاسِ",
            tafseer = "المعبود بحق الذي لا يستحق العبادة سواه، الكامل في صفاته وأفعاله.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 114, surahName = "الناس", ayahNumber = 4,
            ayahText = "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ",
            tafseer = "من شر الشيطان الذي يوسوس في صدور الناس ثم يخنس عند ذكر الله، وهو شديد الاختباء والتسلل.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 114, surahName = "الناس", ayahNumber = 5,
            ayahText = "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ",
            tafseer = "الذي يُلقي وسوسته في قلوب الناس، فيُضلهم ويغويهم بالكلام الخفي.",
            source = "التفسير الميسر"
        ),
        TafseerEntry(
            surahId = 114, surahName = "الناس", ayahNumber = 6,
            ayahText = "مِنَ الْجِنَّةِ وَالنَّاسِ",
            tafseer = "الموسوسون نوعان: من جنس الجن، ومن جنس الإنس، فاحفظ نفسك بالاستعاذة من شر الفريقين.",
            source = "التفسير الميسر"
        )
    )

    fun forSurah(surahId: Int): List<TafseerEntry> {
        return entries.filter { it.surahId == surahId }
    }

    fun search(query: String): List<TafseerEntry> {
        if (query.isBlank()) return entries
        return entries.filter {
            it.ayahText.contains(query, ignoreCase = true) ||
            it.tafseer.contains(query, ignoreCase = true) ||
            it.surahName.contains(query, ignoreCase = true)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TafseerView(onBack: () -> Unit) {
    val context = LocalContext.current
    var selectedSurah by remember { mutableStateOf(1) }
    var searchQuery by remember { mutableStateOf("") }

    val surahs = listOf(
        1 to "الفاتحة",
        112 to "الإخلاص",
        113 to "الفلق",
        114 to "الناس"
    )

    val displayedTafseer = remember(selectedSurah, searchQuery) {
        if (searchQuery.isNotBlank()) {
            TafseerData.search(searchQuery)
        } else {
            TafseerData.forSurah(selectedSurah)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📚 التفسير الميسر", fontWeight = FontWeight.Bold, color = Color.White) },
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
        ) {
            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("ابحث في التفسير...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true
            )

            // Surah selector chips
            if (searchQuery.isBlank()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    surahs.forEach { (id, name) ->
                        FilterChip(
                            selected = selectedSurah == id,
                            onClick = { selectedSurah = id },
                            label = { Text(name, fontSize = 12.sp) }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Tafseer list
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                displayedTafseer.forEach { entry ->
                    TafseerCard(
                        entry = entry,
                        onShare = { ShareHelper.shareAyahWithTafseer(
                            context = context,
                            surahName = entry.surahName,
                            ayahNumber = entry.ayahNumber,
                            text = entry.ayahText,
                            tafseer = entry.tafseer
                        ) },
                        onBookmark = {
                            BookmarkStorage.saveWithType(context, "tafseer", entry.surahId * 1000 + entry.ayahNumber, it)
                        }
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun TafseerCard(
    entry: TafseerEntry,
    onShare: () -> Unit,
    onBookmark: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var isBookmarked by remember { mutableStateOf(BookmarkStorage.isBookmarked(context, "tafseer", entry.surahId * 1000 + entry.ayahNumber)) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isBookmarked) Color(0xFFFFF8E1) else Color(0xFFFAFAFA)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "سورة ${entry.surahName} - آية ${entry.ayahNumber}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D4A30)
                )
                Text(
                    entry.source,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Spacer(Modifier.height(8.dp))

            // Ayah text
            Surface(
                color = Color(0xFFFAFAFA),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "﴿ ${entry.ayahText} ﴾",
                    fontSize = 18.sp,
                    lineHeight = 32.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            // Tafseer
            Surface(
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "📖 التفسير:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        entry.tafseer,
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        color = Color(0xFF1B5E20)
                    )
                }
            }

            // Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onShare() }) {
                    Icon(Icons.Default.Share, "مشاركة", tint = Color(0xFF0D4A30))
                }
                IconButton(onClick = {
                    isBookmarked = !isBookmarked
                    onBookmark(isBookmarked)
                }) {
                    Icon(
                        if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        "حفظ",
                        tint = if (isBookmarked) Color(0xFFC9A84C) else Color.Gray
                    )
                }
            }
        }
    }
}
