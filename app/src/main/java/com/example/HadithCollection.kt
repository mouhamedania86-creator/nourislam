package com.example

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * بيانات الحديث
 */
data class Hadith(
    val id: Int,
    val text: String,           // نص الحديث بالعربية
    val narrator: String,        // الراوي
    val book: String,            // المصدر (البخاري، مسلم...)
    val grade: String,           // الدرجة (صحيح، حسن، ضعيف)
    val explanation: String,     // شرح مختصر
    val category: String,        // التصنيف
    val reference: String        // رقم الحديث
)

/**
 * بيانات ثابتة للأحاديث
 * 40+ حديث من أصح الكتب مع التصنيف
 */
object HadithData {
    val categories = listOf(
        "العقيدة",
        "العبادات",
        "الأخلاق",
        "المعاملات",
        "الأذكار",
        "الآداب"
    )

    val hadiths = listOf(
        // === العقيدة ===
        Hadith(
            id = 1,
            text = "إنما الأعمال بالنيات، وإنما لكل امرئ ما نوى. فمن كانت هجرته إلى الله ورسوله فهجرته إلى الله ورسوله، ومن كانت هجرته لدنيا يصيبها أو امرأة ينكحها فهجرته إلى ما هاجر إليه.",
            narrator = "عمر بن الخطاب رضي الله عنه",
            book = "البخاري",
            grade = "صحيح",
            explanation = "حديث جامع يُعدّ أساساً في الإسلام، يبيّن أن قبول الأعمال وثوابها مرتبط بصدق النية وإخلاصها لله تعالى.",
            category = "العقيدة",
            reference = "1"
        ),
        Hadith(
            id = 2,
            text = "بُني الإسلام على خمس: شهادة أن لا إله إلا الله وأن محمداً رسول الله، وإقام الصلاة، وإيتاء الزكاة، وحج البيت، وصوم رمضان.",
            narrator = "ابن عمر رضي الله عنهما",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "يُبيّن أركان الإسلام الخمسة التي يقوم عليها بناء الدين.",
            category = "العقيدة",
            reference = "8"
        ),
        Hadith(
            id = 3,
            text = "الإسلام أن تشهد أن لا إله إلا الله وأن محمداً رسول الله وتقيم الصلاة وتؤتي الزكاة وتصوم رمضان وتحج البيت إن استطعت إليه سبيلاً.",
            narrator = "ابن عمر رضي الله عنهما",
            book = "مسلم",
            grade = "صحيح",
            explanation = "تعريف الإسلام بأركانه الخمسة.",
            category = "العقيدة",
            reference = "34"
        ),

        // === العبادات ===
        Hadith(
            id = 4,
            text = "الصلوات الخمس، والجمعة إلى الجمعة، ورمضان إلى رمضان: مكفرات ما بينهن إذا اجتُنبت الكبائر.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "فضل المحافظة على الصلوات الخمس والجمعة وصيام رمضان في تكفير الذنوب الصغائر.",
            category = "العبادات",
            reference = "233"
        ),
        Hadith(
            id = 5,
            text = "صوموا يوم عاشوراء، وخالفوا فيه اليهود، وصوموا قبله يوماً أو بعده يوماً.",
            narrator = "ابن عباس رضي الله عنهما",
            book = "أحمد",
            grade = "حسن",
            explanation = "فضل صيام يوم عاشوراء (10 محرم) مع يوم قبله أو بعده مخالفة لأهل الكتاب.",
            category = "العبادات",
            reference = "19660"
        ),
        Hadith(
            id = 6,
            text = "من قام رمضان إيماناً واحتساباً غُفر له ما تقدم من ذنبه.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "فضل قيام رمضان (صلاة التراويح) مع الإيمان والاحتساب.",
            category = "العبادات",
            reference = "37"
        ),
        Hadith(
            id = 7,
            text = "العمرة إلى العمرة كفارة لما بينهما، والحج المبرور ليس له جزاء إلا الجنة.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "فضل تكرار العمرة وثواب الحج المبرور.",
            category = "العبادات",
            reference = "1773"
        ),

        // === الأخلاق ===
        Hadith(
            id = 8,
            text = "لا يؤمن أحدكم حتى يحب لأخيه ما يحب لنفسه.",
            narrator = "أنس بن مالك رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "أساس الأخوة الإسلامية، أن تحب لأخيك ما تحبه لنفسك.",
            category = "الأخلاق",
            reference = "13"
        ),
        Hadith(
            id = 9,
            text = "المؤمن للمؤمن كالبنيان يشد بعضه بعضاً.",
            narrator = "أبو موسى الأشعري رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "تشبيه نبوي للأخوة الإسلامية بالبنيان المتماسك.",
            category = "الأخلاق",
            reference = "481"
        ),
        Hadith(
            id = 10,
            text = "لا تدخلون الجنة حتى تؤمنوا، ولا تؤمنوا حتى تحابوا. أولا أدلكم على شيء إذا فعلتموه تحاببتم؟ أفشوا السلام بينكم.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "فضل إفشاء السلام بين المسلمين كأسباب للمحبة.",
            category = "الأخلاق",
            reference = "54"
        ),
        Hadith(
            id = 11,
            text = "الكلمة الطيبة صدقة.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "فضل الكلمة الطيبة وأنها من الصدقات.",
            category = "الأخلاق",
            reference = "2989"
        ),
        Hadith(
            id = 12,
            text = "تبسمك في وجه أخيك لك صدقة، وأمرك بالمعروف ونهيك عن المنكر صدقة، وإرشادك الرجل في أرض الضلال لك صدقة، وبصرك للرجل الرديء البصر لك صدقة.",
            narrator = "أبو ذر رضي الله عنه",
            book = "الترمذي",
            grade = "حسن",
            explanation = "بيان أن الأخلاق الحسنة من الصدقات.",
            category = "الأخلاق",
            reference = "1956"
        ),
        Hadith(
            id = 13,
            text = "اتقِ الله حيثما كنت، وأتبع السيئة الحسنة تمحها، وخالق الناس بخلق حسن.",
            narrator = "أبو ذر رضي الله عنه",
            book = "الترمذي",
            grade = "حسن",
            explanation = "وصية جامعة لمراقبة الله في كل الأحوال وحسن الخلق.",
            category = "الأخلاق",
            reference = "1987"
        ),
        Hadith(
            id = 14,
            text = "خيركم خيركم لأهله، وأنا خيركم لأهلي.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "الترمذي",
            grade = "حسن",
            explanation = "أفضل الناس في الإسلام أحسنهم معاملة لأهله.",
            category = "الأخلاق",
            reference = "3895"
        ),

        // === المعاملات ===
        Hadith(
            id = 15,
            text = "البيعان بالخيار ما لم يتفرقا، فإن صدقا وبيّنا بُورك لهما في بيعهما، وإن كذبا وكتما مُحقت بركة بيعهما.",
            narrator = "حكيم بن حزام رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "أركان البيعة الصحيحة، والصدق والبيان في البيع.",
            category = "المعاملات",
            reference = "2079"
        ),
        Hadith(
            id = 16,
            text = "الربا سبعون باباً، أيسرها مثل أن ينكح الرجل أمه.",
            narrator = "ابن مسعود رضي الله عنه",
            book = "البخاري",
            grade = "صحيح",
            explanation = "التحذير الشديد من الربا وعواقبه الوخيمة.",
            category = "المعاملات",
            reference = "3334"
        ),
        Hadith(
            id = 17,
            text = "من غشّنا فليس منا.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "التحذير من الغش في المعاملات.",
            category = "المعاملات",
            reference = "101"
        ),

        // === الأذكار ===
        Hadith(
            id = 18,
            text = "من قال: سبحان الله وبحمده، في يومٍ مئة مرة، حُطّت خطاياه وإن كانت مثل زبد البحر.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "فضل التسبيح مائة مرة في اليوم في تكفير الذنوب.",
            category = "الأذكار",
            reference = "6405"
        ),
        Hadith(
            id = 19,
            text = "كلمتان خفيفتان على اللسان، ثقيلتان في الميزان، حبيبتان إلى الرحمن: سبحان الله وبحمده، سبحان الله العظيم.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "فضل التسبيح بحمد الله وتعظيمه.",
            category = "الأذكار",
            reference = "6406"
        ),
        Hadith(
            id = 20,
            text = "من قال: لا إله إلا الله وحده لا شريك له، له الملك وله الحمد وهو على كل شيء قدير، في يومٍ مئة مرة، كانت له عدل عشر رقاب.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "فضل قول التوحيد مائة مرة في اليوم، وثواب عتق الرقاب.",
            category = "الأذكار",
            reference = "6404"
        ),
        Hadith(
            id = 21,
            text = "أحب الأعمال إلى الله أدومها وإن قلّ.",
            narrator = "أم المؤمنين عائشة رضي الله عنها",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "أفضل العبادات المداومة عليها ولو كانت قليلة.",
            category = "الأذكار",
            reference = "6464"
        ),
        Hadith(
            id = 22,
            text = "اللهم إني أعوذ بك من الهم والحزن، وأعوذ بك من العجز والكسل، وأعوذ بك من الجبن والبخل، وأعوذ بك من غلبة الدين وقهر الرجال.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "البخاري",
            grade = "صحيح",
            explanation = "دعاء جامع للتحصن من الهموم والضعف والبخل.",
            category = "الأذكار",
            reference = "6368"
        ),
        Hadith(
            id = 23,
            text = "اللهم أعنّي على ذكرك وشكرك وحسن عبادتك.",
            narrator = "معاذ بن جبل رضي الله عنه",
            book = "أبو داود والنسائي",
            grade = "صحيح",
            explanation = "دعاء يُطلب فيه العون على الطاعة.",
            category = "الأذكار",
            reference = "1522"
        ),

        // === الآداب ===
        Hadith(
            id = 24,
            text = "السلام قبل الكلام.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "أبو داود والترمذي",
            grade = "حسن",
            explanation = "استحباب إلقاء السلام قبل بدء الكلام.",
            category = "الآداب",
            reference = "1984"
        ),
        Hadith(
            id = 25,
            text = "من كان يؤمن بالله واليوم الآخر فليقل خيراً أو ليصمت.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "وجوب حفظ اللسان من الكلام السيء.",
            category = "الآداب",
            reference = "6018"
        ),
        Hadith(
            id = 26,
            text = "لا تحقرن من المعروف شيئاً ولو أن تلقى أخاك بوجه طلق.",
            narrator = "أبو ذر رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "الحث على فعل المعروف ولو كان قليلاً.",
            category = "الآداب",
            reference = "2626"
        ),
        Hadith(
            id = 27,
            text = "أحب الأعمال إلى الله سرور تدخله على مسلم، أو تفرّج عنه كربة، أو تقضي عنه ديناً، أو تطرد عنه جوعاً.",
            narrator = "أبو ذر رضي الله عنه",
            book = "طبراني",
            grade = "حسن",
            explanation = "أحب الأعمال إدخال السرور على المسلم وقضاء حوائجه.",
            category = "الآداب",
            reference = "16334"
        ),
        Hadith(
            id = 28,
            text = "أعطوا الأجير أجره قبل أن يجفّ عرقه.",
            narrator = "عبد الله بن عمر رضي الله عنهما",
            book = "ابن ماجه",
            grade = "حسن",
            explanation = "وجوب إعطاء الأجير أجره فوراً بعد انتهاء العمل.",
            category = "الآداب",
            reference = "2443"
        ),
        Hadith(
            id = 29,
            text = "من ستر مؤمناً ستره الله في الدنيا والآخرة.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "فضل ستر المسلم وعدم فضحه.",
            category = "الآداب",
            reference = "2590"
        ),
        Hadith(
            id = 30,
            text = "المسلم أخو المسلم، لا يظلمه ولا يخذله ولا يحقره. التقوى ها هنا - ويشير إلى صدره ثلاث مرات - بحسب امرئ من الشر أن يحقر أخاه المسلم.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "حقوق المسلم على أخيه، من ظلم أو خذلان أو احتقار.",
            category = "الآداب",
            reference = "2564"
        ),

        // === أحاديث إضافية متنوعة ===
        Hadith(
            id = 31,
            text = "الدين النصيحة. قلنا: لمن؟ قال: لله، ولكتابه، ولرسوله، ولأئمة المسلمين، وعامتهم.",
            narrator = "تميم الداري رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "الدين كله مبني على النصيحة الخالصة لله وللمسلمين.",
            category = "الأخلاق",
            reference = "55"
        ),
        Hadith(
            id = 32,
            text = "من سلك طريقاً يلتمس فيه علماً، سهّل الله له به طريقاً إلى الجنة.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "فضل طلب العلم وأنه طريق إلى الجنة.",
            category = "الأذكار",
            reference = "2699"
        ),
        Hadith(
            id = 33,
            text = "من قال حين يصبح وحين يمسي: سبحان الله وبحمده مئة مرة، لم يأتِ أحد يوم القيامة بأفضل مما جاء به، إلا رجل قال مثل ما قال أو زاد عليه.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "فضل التسبيح مائة مرة صباحاً ومساءً.",
            category = "الأذكار",
            reference = "2691"
        ),
        Hadith(
            id = 34,
            text = "من صلى علي صلاة، صلى الله عليه بها عشراً.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "فضل الصلاة على النبي ﷺ وأن الله يصلي على العبد عشراً.",
            category = "الأذكار",
            reference = "384"
        ),
        Hadith(
            id = 35,
            text = "لا يؤمن العبد الإيمان كله حتى يقتل الحسد والبخل.",
            narrator = "أنس بن مالك رضي الله عنه",
            book = "أحمد",
            grade = "حسن",
            explanation = "الحسد والبخل من الصفات الذميمة التي تنقص الإيمان.",
            category = "الأخلاق",
            reference = "12330"
        ),
        Hadith(
            id = 36,
            text = "إن الله تجاوز لي عن أمتي ما حدثت به أنفسها ما لم تعمل أو تكلم.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "البخاري ومسلم",
            grade = "صحيح",
            explanation = "سعة رحمة الله بعباده، عدم المؤاخذة بالحديث النفسي ما لم يُترجم لفعل.",
            category = "العقيدة",
            reference = "5269"
        ),
        Hadith(
            id = 37,
            text = "من كان له مال فليُصلح ثيابه، ومن كانت له إبل فليحلبها، ومن كانت له أرض فليزرعها، ومن كانت له بئر فليسق منها، فإذا لم يكن له شيء إلا ولده أو والداه أو أخوه أو قريبه أو جاره أو أصحابه فليأكل ويطعم نفسه.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "أحمد",
            grade = "حسن",
            explanation = "تعليم الرسول ﷺ لأمته العمل والكسب والاستفادة من المال.",
            category = "المعاملات",
            reference = "8115"
        ),
        Hadith(
            id = 38,
            text = "من دعا إلى هدى كان له من الأجر مثل أجور من تبعه، لا ينقص ذلك من أجورهم شيئاً.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "فضل الدلالة على الخير، وأن الدال على الخير كفاعله.",
            category = "العبادات",
            reference = "2674"
        ),
        Hadith(
            id = 39,
            text = "اقرؤوا القرآن فإنه يأتي يوم القيامة شفيعاً لأصحابه.",
            narrator = "أبو أمامة الباهلي رضي الله عنه",
            book = "مسلم",
            grade = "صحيح",
            explanation = "فضل قراءة القرآن وأنه يشفع لقارئه يوم القيامة.",
            category = "الأذكار",
            reference = "804"
        ),
        Hadith(
            id = 40,
            text = "إن في الجنة مئة درجة أعدها الله للمجاهدين في سبيل الله، ما بين الدرجتين كما بين السماء والأرض.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "البخاري",
            grade = "صحيح",
            explanation = "فضل الجهاد في سبيل الله ودرجاته العظيمة في الجنة.",
            category = "العبادات",
            reference = "2790"
        )
    )

    /**
     * فلترة الأحاديث حسب التصنيف
     */
    fun filterByCategory(category: String): List<Hadith> {
        return hadiths.filter { it.category == category }
    }

    /**
     * بحث في الأحاديث
     */
    fun search(query: String): List<Hadith> {
        if (query.isBlank()) return hadiths
        val lower = query.lowercase()
        return hadiths.filter {
            it.text.contains(query, ignoreCase = true) ||
            it.narrator.contains(query, ignoreCase = true) ||
            it.explanation.contains(query, ignoreCase = true) ||
            it.book.contains(query, ignoreCase = true)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HadithCollectionView(onBack: () -> Unit) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    val bookmarks = remember { mutableStateListOf<Int>() }

    val displayedHadiths = remember(selectedCategory, searchQuery) {
        val filtered = if (searchQuery.isNotBlank()) {
            HadithData.search(searchQuery)
        } else if (selectedCategory != null) {
            HadithData.filterByCategory(selectedCategory!!)
        } else {
            HadithData.hadiths
        }
        filtered
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📜 مجموعة الأحاديث النبوية", fontWeight = FontWeight.Bold, color = Color.White) },
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
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("ابحث في الأحاديث...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true
            )

            // Category chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = { Text("الكل") }
                )
                HadithData.categories.take(4).forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 12.sp) }
                    )
                }
            }

            // Results count
            Text(
                "${displayedHadiths.size} حديث",
                modifier = Modifier.padding(16.dp),
                fontSize = 12.sp,
                color = Color.Gray
            )

            // Hadith list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(displayedHadiths) { hadith ->
                    HadithCard(
                        hadith = hadith,
                        isBookmarked = hadith.id in bookmarks,
                        onBookmark = {
                            if (hadith.id in bookmarks) bookmarks.remove(hadith.id)
                            else bookmarks.add(hadith.id)
                            BookmarkStorage.save(context, hadith.id, !it)
                        },
                        onShare = { ShareHelper.shareHadith(context, hadith) }
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun HadithCard(
    hadith: Hadith,
    isBookmarked: Boolean,
    onBookmark: (Boolean) -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isBookmarked) Color(0xFFFFF8E1) else Color(0xFFFAFAFA)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFF0D4A30),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            hadith.category,
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(Modifier.width(6.dp))
                    Text(hadith.book, fontSize = 11.sp, color = Color.Gray)
                }
                Text(
                    "#${hadith.reference}",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Spacer(Modifier.height(8.dp))

            // Hadith text
            Text(
                hadith.text,
                fontSize = 15.sp,
                lineHeight = 26.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            // Narrator
            Text(
                "📖 ${hadith.narrator} • ${hadith.grade}",
                fontSize = 11.sp,
                color = Color(0xFF6D4C00),
                fontWeight = FontWeight.Bold
            )

            // Explanation
            Spacer(Modifier.height(8.dp))
            Surface(
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "💡 ${hadith.explanation}",
                    fontSize = 12.sp,
                    color = Color(0xFF1B5E20),
                    modifier = Modifier.padding(8.dp)
                )
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
                IconButton(onClick = { onBookmark(isBookmarked) }) {
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
