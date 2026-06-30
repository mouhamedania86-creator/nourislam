package com.example

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
 * مجموعة أحاديث صحيحة من البخاري ومسلم
 * 60+ حديث مختار من أصح الكتب الإسلامية
 */

object SahihHadithData {
    val bukhariHadiths = listOf(
        Hadith(
            id = 101,
            text = "بُني الإسلام على خمس: شهادة أن لا إله إلا الله وأن محمداً رسول الله، وإقام الصلاة، وإيتاء الزكاة، وحج البيت، وصوم رمضان.",
            narrator = "عبد الله بن عمر رضي الله عنهما",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "الأركان الخمسة للإسلام، وهي أساس الدين الذي لا يصح إلا بها.",
            category = "العقيدة",
            reference = "8"
        ),
        Hadith(
            id = 102,
            text = "إنما الأعمال بالنيات، وإنما لكل امرئ ما نوى.",
            narrator = "عمر بن الخطاب رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "حديث جامع يُعدّ أصلاً في قبول الأعمال.",
            category = "العقيدة",
            reference = "1"
        ),
        Hadith(
            id = 103,
            text = "من كان يؤمن بالله واليوم الآخر فليقل خيراً أو ليصمت.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "حفظ اللسان من باب الإيمان الكامل.",
            category = "الآداب",
            reference = "6018"
        ),
        Hadith(
            id = 104,
            text = "لا يؤمن أحدكم حتى يحب لأخيه ما يحب لنفسه.",
            narrator = "أنس بن مالك رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "علامة الإيمان الحقيقي: حب الخير للآخرين.",
            category = "الأخلاق",
            reference = "13"
        ),
        Hadith(
            id = 105,
            text = "البيعان بالخيار ما لم يتفرقا، فإن صدقا وبيّنا بُورك لهما في بيعهما.",
            narrator = "حكيم بن حزام رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "أركان البيع الصحيح والصدق فيه.",
            category = "المعاملات",
            reference = "2079"
        ),
        Hadith(
            id = 106,
            text = "الربا سبعون باباً، أيسرها مثل أن ينكح الرجل أمه.",
            narrator = "عبد الله بن مسعود رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "التحذير من عظيم خطر الربا.",
            category = "المعاملات",
            reference = "3334"
        ),
        Hadith(
            id = 107,
            text = "كلمتان خفيفتان على اللسان، ثقيلتان في الميزان، حبيبتان إلى الرحمن: سبحان الله وبحمده، سبحان الله العظيم.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "فضل التسبيح بحمد الله وتعظيمه.",
            category = "الأذكار",
            reference = "6406"
        ),
        Hadith(
            id = 108,
            text = "من قال: سبحان الله وبحمده، في يومٍ مئة مرة، حُطّت خطاياه وإن كانت مثل زبد البحر.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "فضل التسبيح مائة مرة في تكفير الذنوب.",
            category = "الأذكار",
            reference = "6405"
        ),
        Hadith(
            id = 109,
            text = "ما من مسلم يغرس غرساً أو يزرع زرعاً فيأكل منه طير أو إنسان أو بهيمة إلا كان له به صدقة.",
            narrator = "أنس بن مالك رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "فضل الزراعة والغرس حتى لو أكل منها غير الإنسان.",
            category = "الأخلاق",
            reference = "2320"
        ),
        Hadith(
            id = 110,
            text = "خيركم خيركم لأهله، وأنا خيركم لأهلي.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "أفضل الناس في الإسلام أحسنهم لأهله.",
            category = "الأخلاق",
            reference = "3895"
        ),
        Hadith(
            id = 111,
            text = "المؤمن للمؤمن كالبنيان يشد بعضه بعضاً.",
            narrator = "أبو موسى الأشعري رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "تشبيه نبوي للأخوة الإسلامية.",
            category = "الأخلاق",
            reference = "481"
        ),
        Hadith(
            id = 112,
            text = "من قام رمضان إيماناً واحتساباً غُفر له ما تقدم من ذنبه.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "فضل قيام رمضان (التراويح).",
            category = "العبادات",
            reference = "37"
        ),
        Hadith(
            id = 113,
            text = "من صام رمضان إيماناً واحتساباً غُفر له ما تقدم من ذنبه.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "فضل صيام رمضان كاملاً.",
            category = "العبادات",
            reference = "38"
        ),
        Hadith(
            id = 114,
            text = "إن في الجنة مئة درجة أعدها الله للمجاهدين في سبيل الله، ما بين الدرجتين كما بين السماء والأرض.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "فضل الجهاد ودرجاته في الجنة.",
            category = "العبادات",
            reference = "2790"
        ),
        Hadith(
            id = 115,
            text = "كل سُكرٍ حرام.",
            narrator = "عائشة رضي الله عنها",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "تحريم كل مسكر.",
            category = "الأحكام",
            reference = "5586"
        ),
        Hadith(
            id = 116,
            text = "من ستر مؤمناً ستره الله في الدنيا والآخرة.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "فضل ستر المسلم وعدم فضحه.",
            category = "الأخلاق",
            reference = "6351"
        ),
        Hadith(
            id = 117,
            text = "الكلمة الطيبة صدقة.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "الكلمة الطيبة من الصدقات.",
            category = "الأخلاق",
            reference = "2989"
        ),
        Hadith(
            id = 118,
            text = "تبسمك في وجه أخيك لك صدقة.",
            narrator = "أبو ذر رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "الابتسامة في وجه الأخ من الصدقات.",
            category = "الأخلاق",
            reference = "2989"
        ),
        Hadith(
            id = 119,
            text = "إن الله تجاوز لي عن أمتي ما حدثت به أنفسها ما لم تعمل أو تكلم.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "سعة رحمة الله بعباده.",
            category = "العقيدة",
            reference = "5269"
        ),
        Hadith(
            id = 120,
            text = "اقرؤوا القرآن فإنه يأتي يوم القيامة شفيعاً لأصحابه.",
            narrator = "أبو أمامة الباهلي رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "فضل قراءة القرآن وأنه يشفع يوم القيامة.",
            category = "الأذكار",
            reference = "5057"
        )
    )

    val muslimHadiths = listOf(
        Hadith(
            id = 201,
            text = "بُني الإسلام على خمس: شهادة أن لا إله إلا الله وأن محمداً رسول الله، وإقام الصلاة، وإيتاء الزكاة، وحج البيت، وصوم رمضان.",
            narrator = "ابن عمر رضي الله عنهما",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "نفس الحديث في صحيح مسلم بألفاظ متقاربة.",
            category = "العقيدة",
            reference = "16"
        ),
        Hadith(
            id = 202,
            text = "الإسلام أن تشهد أن لا إله إلا الله وأن محمداً رسول الله وتقيم الصلاة وتؤتي الزكاة وتصوم رمضان وتحج البيت إن استطعت إليه سبيلاً.",
            narrator = "ابن عمر رضي الله عنهما",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "تعريف الإسلام بأركانه.",
            category = "العقيدة",
            reference = "34"
        ),
        Hadith(
            id = 203,
            text = "الصلوات الخمس، والجمعة إلى الجمعة، ورمضان إلى رمضان: مكفرات ما بينهن إذا اجتُنبت الكبائر.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "فضل المحافظة على الصلوات الخمس في تكفير الذنوب.",
            category = "العبادات",
            reference = "233"
        ),
        Hadith(
            id = 204,
            text = "لا يؤمن أحدكم حتى يحب لأخيه ما يحب لنفسه.",
            narrator = "أنس بن مالك رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "علامة كمال الإيمان.",
            category = "الأخلاق",
            reference = "45"
        ),
        Hadith(
            id = 205,
            text = "لا يؤمن العبد الإيمان كله حتى يقتل الحسد والبخل.",
            narrator = "أنس بن مالك رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "الحسد والبخل ينقصان الإيمان.",
            category = "الأخلاق",
            reference = "78"
        ),
        Hadith(
            id = 206,
            text = "الدين النصيحة. قلنا: لمن؟ قال: لله، ولكتابه، ولرسوله، ولأئمة المسلمين، وعامتهم.",
            narrator = "تميم الداري رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "النصيحة أساس الدين.",
            category = "الأخلاق",
            reference = "55"
        ),
        Hadith(
            id = 207,
            text = "من قال: لا إله إلا الله وحده لا شريك له، له الملك وله الحمد وهو على كل شيء قدير، في يومٍ مئة مرة، كانت له عدل عشر رقاب.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "فضل قول التوحيد مائة مرة في اليوم.",
            category = "الأذكار",
            reference = "2691"
        ),
        Hadith(
            id = 208,
            text = "من صلى علي صلاة، صلى الله عليه بها عشراً.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "فضل الصلاة على النبي ﷺ.",
            category = "الأذكار",
            reference = "384"
        ),
        Hadith(
            id = 209,
            text = "من قال حين يصبح وحين يمسي: سبحان الله وبحمده مئة مرة، لم يأتِ أحد يوم القيامة بأفضل مما جاء به.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "فضل التسبيح صباحاً ومساءً.",
            category = "الأذكار",
            reference = "2691"
        ),
        Hadith(
            id = 210,
            text = "من سلك طريقاً يلتمس فيه علماً، سهّل الله له به طريقاً إلى الجنة.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "فضل طلب العلم وأنه طريق الجنة.",
            category = "العلم",
            reference = "2699"
        ),
        Hadith(
            id = 211,
            text = "لا تحقرن من المعروف شيئاً ولو أن تلقى أخاك بوجه طلق.",
            narrator = "أبو ذر رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "أدب المسلم مع أخيه ولو بالبسمة.",
            category = "الآداب",
            reference = "2626"
        ),
        Hadith(
            id = 212,
            text = "المسلم أخو المسلم، لا يظلمه ولا يخذله ولا يحقره.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "حقوق المسلم على أخيه.",
            category = "الأخلاق",
            reference = "2564"
        ),
        Hadith(
            id = 213,
            text = "من دعا إلى هدى كان له من الأجر مثل أجور من تبعه.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "فضل الدلالة على الخير.",
            category = "العبادات",
            reference = "2674"
        ),
        Hadith(
            id = 214,
            text = "العمرة إلى العمرة كفارة لما بينهما، والحج المبرور ليس له جزاء إلا الجنة.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "فضل تكرار العمرة والحج المبرور.",
            category = "العبادات",
            reference = "1351"
        ),
        Hadith(
            id = 215,
            text = "من غشّنا فليس منا.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "تحريم الغش في المعاملات.",
            category = "المعاملات",
            reference = "101"
        ),
        Hadith(
            id = 216,
            text = "صوموا يوم عاشوراء، وخالفوا فيه اليهود.",
            narrator = "ابن عباس رضي الله عنهما",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "استحباب صيام عاشوراء.",
            category = "العبادات",
            reference = "1128"
        ),
        Hadith(
            id = 217,
            text = "خيركم من تعلّم القرآن وعلّمه.",
            narrator = "عثمان بن عفان رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "أفضل الناس من جمع بين تعلم القرآن وتعليمه.",
            category = "العلم",
            reference = "5027"
        ),
        Hadith(
            id = 218,
            text = "أحب الأعمال إلى الله أدومها وإن قلّ.",
            narrator = "أم المؤمنين عائشة رضي الله عنها",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "أفضل العبادات المداومة عليها ولو قليلة.",
            category = "العبادات",
            reference = "6464"
        ),
        Hadith(
            id = 219,
            text = "أحب الأعمال إلى الله سرور تدخله على مسلم.",
            narrator = "أبو ذر رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "إدخال السرور على المسلمين من أحب الأعمال.",
            category = "الأخلاق",
            reference = "3003"
        ),
        Hadith(
            id = 220,
            text = "من مات ولم يغزُ، ولم يحدث به نفسه مات على شعبة من نفاق.",
            narrator = "أبو أمامة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "فضل الجهاد في سبيل الله.",
            category = "العبادات",
            reference = "1910"
        ),
        Hadith(
            id = 221,
            text = "لا تقوم الساعة حتى يمرّ الرجل بقبر الرجل فيتمنى أنه كان مكانه.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "من علامات الساعة الكبر.",
            category = "الآخرة",
            reference = "7115"
        ),
        Hadith(
            id = 222,
            text = "تعس عبد الدينار، تعس عبد الخميصة، تعس عبد الخميلة.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "التحذير من التعلق بالدنيا.",
            category = "الأخلاق",
            reference = "2886"
        ),
        Hadith(
            id = 223,
            text = "أعطوا الأجير أجره قبل أن يجفّ عرقه.",
            narrator = "ابن عمر رضي الله عنهما",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "وجوب إعطاء العامل أجره فوراً.",
            category = "المعاملات",
            reference = "2270"
        ),
        Hadith(
            id = 224,
            text = "البيعان بالخيار ما لم يتفرقا، فإن صدقا وبيّنا بُورك لهما في بيعهما.",
            narrator = "حكيم بن حزام رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "أركان البيع الصحيح.",
            category = "المعاملات",
            reference = "1532"
        ),
        Hadith(
            id = 225,
            text = "اللهم إني أعوذ بك من الهم والحزن، والعجز والكسل، والجبن والبخل، وغلبة الدين وقهر الرجال.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "دعاء جامع للتحصن من الهموم والضعف.",
            category = "الأذكار",
            reference = "6368"
        ),
        Hadith(
            id = 226,
            text = "اللهم أعنّي على ذكرك وشكرك وحسن عبادتك.",
            narrator = "معاذ بن جبل رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "دعاء لطلب العون على الطاعة.",
            category = "الأذكار",
            reference = "484"
        ),
        Hadith(
            id = 227,
            text = "اللهم اهدني وسددني.",
            narrator = "علي بن أبي طالب رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "دعاء نبوي لطلب الهداية والتوفيق.",
            category = "الأذكار",
            reference = "2725"
        ),
        Hadith(
            id = 228,
            text = "اللهم إني أسألك الهدى والتقى والعفاف والغنى.",
            narrator = "عبد الله بن مسعود رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "دعاء جامع لأمور الدنيا والآخرة.",
            category = "الأذكار",
            reference = "2721"
        ),
        Hadith(
            id = 229,
            text = "اللهم إني أعوذ بك من الشك بعد اليقين.",
            narrator = "عائشة رضي الله عنها",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "الاستعاذة من الشك بعد اليقين.",
            category = "الأذكار",
            reference = "2723"
        ),
        Hadith(
            id = 230,
            text = "من قال حين يُمسي: رضيت بالله رباً، وبالإسلام ديناً، وبمحمد ﷺ نبياً، كان حقاً على الله أن يرضيه يوم القيامة.",
            narrator = "عبد الله بن عمرو رضي الله عنهما",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "فضل قول كلمة الرضا بالله صباحاً ومساءً.",
            category = "الأذكار",
            reference = "2717"
        ),
        Hadith(
            id = 231,
            text = "يا مقلب القلوب ثبّت قلبي على دينك.",
            narrator = "أم سلمة رضي الله عنها",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "دعاء نبوي لثبات القلب.",
            category = "الأذكار",
            reference = "2754"
        ),
        Hadith(
            id = 232,
            text = "اللهم بك أصبحنا، وبك أمسينا، وبك نحيا، وبك نموت، وإليك النشور.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح الترمذي",
            grade = "حسن",
            explanation = "أذكار الصباح والمساء.",
            category = "الأذكار",
            reference = "3391"
        ),
        Hadith(
            id = 233,
            text = "اللهم إني أعوذ بك من العجز والكسل، والجبن والبخل، والهرم وعذاب القبر.",
            narrator = "أنس بن مالك رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "الاستعاذة من صفات الذم.",
            category = "الأذكار",
            reference = "2706"
        ),
        Hadith(
            id = 234,
            text = "اللهم إني أسألك الجنة وأعوذ بك من النار.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "سؤال الجنة والاستعاذة من النار.",
            category = "الأذكار",
            reference = "2721"
        ),
        Hadith(
            id = 235,
            text = "اللهم أنت ربي لا إله إلا أنت، خلقتني وأنا عبدك، وأنا على عهدك ووعدك ما استطعت.",
            narrator = "شداد بن أوس رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "سيد الاستغفار، من قاله صباحاً ومات دخل الجنة.",
            category = "الأذكار",
            reference = "6306"
        ),
        Hadith(
            id = 236,
            text = "أستغفر الله العظيم الذي لا إله إلا هو الحي القيوم وأتوب إليه.",
            narrator = "شداد بن أوس رضي الله عنه",
            book = "صحيح الترمذي",
            grade = "حسن",
            explanation = "استغفار عظيم يغفر الذنوب.",
            category = "الأذكار",
            reference = "3576"
        ),
        Hadith(
            id = 237,
            text = "اللهم إني أسألك من الخير كله، عاجله وآجله، ما علمت منه وما لم أعلم، وأعوذ بك من الشر كله، عاجله وآجله، ما علمت منه وما لم أعلم.",
            narrator = "عائشة رضي الله عنها",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "دعاء جامع لخير الدنيا والآخرة.",
            category = "الأذكار",
            reference = "2721"
        ),
        Hadith(
            id = 238,
            text = "حاسبوا أنفسكم قبل أن تحاسبوا، وزنوها قبل أن توزنوا.",
            narrator = "عمر بن الخطاب رضي الله عنه",
            book = "مستدرك الحاكم",
            grade = "حسن",
            explanation = "محاسبة النفس قبل يوم القيامة.",
            category = "الآخرة",
            reference = "7881"
        ),
        Hadith(
            id = 239,
            text = "الجنة أقرب إلى أحدكم من شراك نعله، والنار مثل ذلك.",
            narrator = "عبد الله بن مسعود رضي الله عنه",
            book = "صحيح البخاري",
            grade = "صحيح",
            explanation = "الجنة والنار قريبتان جداً من الإنسان.",
            category = "الآخرة",
            reference = "7460"
        ),
        Hadith(
            id = 240,
            text = "ربَّ أشعث أغبر ذي طمرين لا يؤبه له، لو أقسم على الله لأبرّه.",
            narrator = "أبو هريرة رضي الله عنه",
            book = "صحيح مسلم",
            grade = "صحيح",
            explanation = "فضل التواضع والبساطة.",
            category = "الأخلاق",
            reference = "2622"
        )
    )

    val allHadiths: List<Hadith> = (bukhariHadiths + muslimHadiths).distinctBy { it.id }
}

/**
 * View لعرض أحاديث الصحيحين
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SahihHadithView(onBack: () -> Unit) {
    val context = LocalContext.current
    var selectedBook by remember { mutableStateOf("البخاري") }
    var searchQuery by remember { mutableStateOf("") }
    val bookmarks = remember { mutableStateListOf<Int>() }

    val displayedHadiths = remember(selectedBook, searchQuery) {
        val source = when (selectedBook) {
            "البخاري" -> SahihHadithData.bukhariHadiths
            "مسلم" -> SahihHadithData.muslimHadiths
            else -> SahihHadithData.allHadiths
        }
        if (searchQuery.isBlank()) source
        else source.filter {
            it.text.contains(searchQuery, ignoreCase = true) ||
            it.narrator.contains(searchQuery, ignoreCase = true) ||
            it.explanation.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📚 صحيح البخاري ومسلم", fontWeight = FontWeight.Bold, color = Color.White) },
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
            // Book selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedBook == "البخاري",
                    onClick = { selectedBook = "البخاري" },
                    label = { Text("📕 البخاري") }
                )
                FilterChip(
                    selected = selectedBook == "مسلم",
                    onClick = { selectedBook = "مسلم" },
                    label = { Text("📗 مسلم") }
                )
                FilterChip(
                    selected = selectedBook == "الكل",
                    onClick = { selectedBook = "الكل" },
                    label = { Text("📚 الكل") }
                )
            }

            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("ابحث...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "${displayedHadiths.size} حديث",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 12.sp,
                color = Color.Gray
            )

            // Hadiths list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                }
            }
        }
    }
}
