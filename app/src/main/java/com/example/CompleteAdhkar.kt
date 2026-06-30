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
 * الأذكار الكاملة
 * - أذكار الصباح (15+ ذكر)
 * - أذكار المساء (15+ ذكر)
 * - أذكار النوم (15+ ذكر)
 * - أذكار الاستيقاظ (10+ ذكر)
 * - أذكار المسجد (5+ ذكر)
 * - أذكار الوضوء (5+ ذكر)
 * - أذكار الطعام (10+ ذكر)
 */

data class CompleteDhikr(
    val id: Int,
    val text: String,
    val count: Int,
    val benefit: String,
    val source: String,
    val category: String
)

/**
 * أذكار الصباح - 15 ذكر
 * من السنة النبوية الصحيحة
 */
object MorningAdhkar {
    val adhkar = listOf(
        CompleteDhikr(1, "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ، وَالْحَمْدُ لِلَّهِ، لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ.", 1, "من قالها كتب الله له ما بعدها من الخير", "صحيح مسلم", "الصباح"),
        CompleteDhikr(2, "اللَّهُمَّ بِكَ أَصْبَحْنَا، وَبِكَ أَمْسَيْنَا، وَبِكَ نَحْيَا، وَبِكَ نَمُوتُ، وَإِلَيْكَ النُّشُورُ.", 1, "أذكار الصباح النبوية", "الترمذي", "الصباح"),
        CompleteDhikr(3, "اللَّهُمَّ أَنْتَ رَبِّي لاَ إِلَهَ إِلاَّ أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ.", 1, "سيد الاستغفار - من قالها صباحاً ومات دخل الجنة", "صحيح البخاري", "الصباح"),
        CompleteDhikr(4, "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ.", 100, "حُطت خطاياه ولو كانت مثل زبد البحر", "صحيح مسلم", "الصباح"),
        CompleteDhikr(5, "لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ.", 100, "كانت له عدل عشر رقاب", "صحيح البخاري ومسلم", "الصباح"),
        CompleteDhikr(6, "أَسْتَغْفِرُ اللَّهَ وَأَتُوبُ إِلَيْهِ.", 100, "غُفر له ولو كانت ذنوبه مثل زبد البحر", "صحيح البخاري ومسلم", "الصباح"),
        CompleteDhikr(7, "اللَّهُمَّ صَلِّ وَسَلِّمْ عَلَى نَبِيِّنَا مُحَمَّدٍ.", 100, "من صلى علي صلاة صلى الله عليه بها عشراً", "صحيح الترمذي", "الصباح"),
        CompleteDhikr(8, "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي الدُّنْيَا وَالآخِرَةِ.", 1, "من أهم أدعية النبي ﷺ", "صحيح ابن ماجه", "الصباح"),
        CompleteDhikr(9, "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْهَمِّ وَالْحَزَنِ، وَأَعُوذُ بِكَ مِنَ الْعَجْزِ وَالْكَسَلِ، وَأَعُوذُ بِكَ مِنَ الْجُبْنِ وَالْبُخْلِ، وَأَعُوذُ بِكَ مِنْ غَلَبَةِ الدَّيْنِ وَقَهْرِ الرِّجَالِ.", 1, "دعاء جامع للتحصن", "صحيح البخاري", "الصباح"),
        CompleteDhikr(10, "حَسْبِيَ اللَّهُ لاَ إِلَهَ إِلاَّ هُوَ عَلَيْهِ تَوَكَّلْتُ وَهُوَ رَبُّ الْعَرْشِ الْعَظِيمِ.", 7, "كفاه الله ما أهمه", "أبو داود", "الصباح"),
        CompleteDhikr(11, "بِسْمِ اللَّهِ الَّذِي لاَ يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الأَرْضِ وَلاَ فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ.", 3, "لم يضره من الله شيء", "أبو داود والترمذي", "الصباح"),
        CompleteDhikr(12, "رَضِيتُ بِاللَّهِ رَبًّا، وَبِالإِسْلاَمِ دِينًا، وَبِمُحَمَّدٍ ﷺ نَبِيًّا.", 3, "كان حقاً على الله أن يرضيه يوم القيامة", "صحيح الترمذي", "الصباح"),
        CompleteDhikr(13, "يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ، أَصْلِحْ لِي شَأْنِي كُلَّهُ، وَلاَ تَكِلْنِي إِلَى نَفْسِي طَرْفَةَ عَيْنٍ.", 1, "من أعظم الأدعية للحفظ", "الحاكم", "الصباح"),
        CompleteDhikr(14, "اللَّهُمَّ مَا أَصْبَحَ بِي مِنْ نِعْمَةٍ أَوْ بِأَحَدٍ مِنْ خَلْقِكَ، فَمِنْكَ وَحْدَكَ لاَ شَرِيكَ لَكَ، فَلَكَ الْحَمْدُ وَلَكَ الشُّكْرُ.", 1, "من شكر الله أدى شكر كل من شكره", "أبو داود", "الصباح"),
        CompleteDhikr(15, "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ.", 3, "لم تضره فحمة تلك الليلة", "صحيح مسلم", "الصباح"),
        CompleteDhikr(16, "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ، عَدَدَ خَلْقِهِ، وَرِضَا نَفْسِهِ، وَزِنَةَ عَرْشِهِ، وَمِدَادَ كَلِمَاتِهِ.", 3, "أحب الكلام إلى الله", "صحيح مسلم", "الصباح"),
        CompleteDhikr(17, "اللَّهُمَّ عَافِنِي فِي بَدَنِي، اللَّهُمَّ عَافِنِي فِي سَمْعِي، اللَّهُمَّ عَافِنِي فِي بَصَرِي، لاَ إِلَهَ إِلاَّ أَنْتَ.", 3, "دعاء جامع للعافية", "أبو داود", "الصباح"),
        CompleteDhikr(18, "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْكُفْرِ، وَالْفَقْرِ، وَأَعُوذُ بِكَ مِنْ عَذَابِ الْقَبْرِ، لاَ إِلَهَ إِلاَّ أَنْتَ.", 3, "الاستعاذة من الثلاث", "أبو داود", "الصباح")
    )
}

/**
 * أذكار المساء - 15 ذكر
 */
object EveningAdhkar {
    val adhkar = listOf(
        CompleteDhikr(101, "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ، وَالْحَمْدُ لِلَّهِ، لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ.", 1, "من قالها كتب الله له ما بعدها من الخير", "صحيح مسلم", "المساء"),
        CompleteDhikr(102, "اللَّهُمَّ بِكَ أَمْسَيْنَا، وَبِكَ أَصْبَحْنَا، وَبِكَ نَحْيَا، وَبِكَ نَمُوتُ، وَإِلَيْكَ الْمَصِيرُ.", 1, "أذكار المساء النبوية", "الترمذي", "المساء"),
        CompleteDhikr(103, "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ.", 100, "حُطت خطاياه ولو كانت مثل زبد البحر", "صحيح مسلم", "المساء"),
        CompleteDhikr(104, "لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ.", 100, "عدم الرقاب", "صحيح البخاري ومسلم", "المساء"),
        CompleteDhikr(105, "أَسْتَغْفِرُ اللَّهَ وَأَتُوبُ إِلَيْهِ.", 100, "غُفر له ولو كانت ذنوبه مثل زبد البحر", "صحيح البخاري ومسلم", "المساء"),
        CompleteDhikr(106, "اللَّهُمَّ صَلِّ وَسَلِّمْ عَلَى نَبِيِّنَا مُحَمَّدٍ.", 100, "جزاء الصلاة والسلام على النبي", "صحيح الترمذي", "المساء"),
        CompleteDhikr(107, "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي الدُّنْيَا وَالآخِرَةِ.", 1, "من أهم أدعية النبي ﷺ", "صحيح ابن ماجه", "المساء"),
        CompleteDhikr(108, "حَسْبِيَ اللَّهُ لاَ إِلَهَ إِلاَّ هُوَ عَلَيْهِ تَوَكَّلْتُ وَهُوَ رَبُّ الْعَرْشِ الْعَظِيمِ.", 7, "كفاه الله ما أهمه", "أبو داود", "المساء"),
        CompleteDhikr(109, "بِسْمِ اللَّهِ الَّذِي لاَ يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الأَرْضِ وَلاَ فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ.", 3, "لم يضره من الله شيء", "أبو داود والترمذي", "المساء"),
        CompleteDhikr(110, "رَضِيتُ بِاللَّهِ رَبًّا، وَبِالإِسْلاَمِ دِينًا، وَبِمُحَمَّدٍ ﷺ نَبِيًّا.", 3, "كان حقاً على الله أن يرضيه يوم القيامة", "صحيح الترمذي", "المساء"),
        CompleteDhikr(111, "يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ، أَصْلِحْ لِي شَأْنِي كُلَّهُ.", 1, "من أعظم الأدعية للحفظ", "الحاكم", "المساء"),
        CompleteDhikr(112, "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ.", 3, "لم تضره فحمة تلك الليلة", "صحيح مسلم", "المساء"),
        CompleteDhikr(113, "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ، عَدَدَ خَلْقِهِ، وَرِضَا نَفْسِهِ، وَزِنَةَ عَرْشِهِ، وَمِدَادَ كَلِمَاتِهِ.", 3, "أحب الكلام إلى الله", "صحيح مسلم", "المساء"),
        CompleteDhikr(114, "اللَّهُمَّ مَا أَمْسَى بِي مِنْ نِعْمَةٍ أَوْ بِأَحَدٍ مِنْ خَلْقِكَ، فَمِنْكَ وَحْدَكَ لاَ شَرِيكَ لَكَ، فَلَكَ الْحَمْدُ وَلَكَ الشُّكْرُ.", 1, "من شكر الله أدى شكر كل من شكره", "أبو داود", "المساء"),
        CompleteDhikr(115, "اللَّهُمَّ عَافِنِي فِي بَدَنِي، اللَّهُمَّ عَافِنِي فِي سَمْعِي، اللَّهُمَّ عَافِنِي فِي بَصَرِي، لاَ إِلَهَ إِلاَّ أَنْتَ.", 3, "دعاء جامع للعافية", "أبو داود", "المساء"),
        CompleteDhikr(116, "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْكُفْرِ، وَالْفَقْرِ، وَأَعُوذُ بِكَ مِنْ عَذَابِ الْقَبْرِ، لاَ إِلَهَ إِلاَّ أَنْتَ.", 3, "الاستعاذة من الثلاث", "أبو داود", "المساء"),
        CompleteDhikr(117, "أَسْأَلُ اللَّهَ الْعَظِيمَ، رَبَّ الْعَرْشِ الْعَظِيمِ، أَنْ يَشْفِيَكَ.", 7, "دعاء للشفاء للمريض", "الترمذي", "المساء")
    )
}

/**
 * أذكار النوم - 15 ذكر
 */
object SleepAdhkar {
    val adhkar = listOf(
        CompleteDhikr(201, "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا.", 1, "من قالها عند النوم مات على الفطرة", "صحيح البخاري", "النوم"),
        CompleteDhikr(202, "اللَّهُمَّ قِنِي عَذَابَكَ يَوْمَ تَبْعَثُ عِبَادَكَ.", 3, "من قالها حفظه الله من عذاب القبر", "أبو داود والترمذي", "النوم"),
        CompleteDhikr(203, "سُبْحَانَ اللَّهِ.", 33, "من سبّح عند النوم غُفرت ذنوبه", "صحيح مسلم", "النوم"),
        CompleteDhikr(204, "الْحَمْدُ لِلَّهِ.", 33, "مع التسبيح للحفظ", "صحيح مسلم", "النوم"),
        CompleteDhikr(205, "اللَّهُ أَكْبَرُ.", 34, "تمام المئة بذكر الله أكبر", "صحيح مسلم", "النوم"),
        CompleteDhikr(206, "آيَةُ الْكُرْسِيِّ.", 1, "من قرأها لم يزل عليه من الله حافظ ولا يقربه شيطان حتى يصبح", "صحيح البخاري", "النوم"),
        CompleteDhikr(207, "قُلْ هُوَ اللَّهُ أَحَدٌ.", 3, "كفته من كل شيء", "أبو داود", "النوم"),
        CompleteDhikr(208, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ.", 3, "الاستعاذة من شر المخلوقات", "أبو داود", "النوم"),
        CompleteDhikr(209, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ.", 3, "الاستعاذة من وسوسة الشيطان", "أبو داود", "النوم"),
        CompleteDhikr(210, "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ.", 1, "من قالها عند النوم كتب الله له ما بعدها من الخير", "صحيح مسلم", "النوم"),
        CompleteDhikr(211, "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ، عَدَدَ خَلْقِهِ، وَرِضَا نَفْسِهِ، وَزِنَةَ عَرْشِهِ، وَمِدَادَ كَلِمَاتِهِ.", 3, "ثقيل في الميزان", "صحيح مسلم", "النوم"),
        CompleteDhikr(212, "اللَّهُمَّ إِنِّي أَسْلَمْتُ نَفْسِي إِلَيْكَ، وَفَوَّضْتُ أَمْرِي إِلَيْكَ، وَأَلْجَأْتُ ظَهْرِي إِلَيْكَ.", 1, "من قالها عند نومه عاش وكُفي", "صحيح البخاري ومسلم", "النوم"),
        CompleteDhikr(213, "اللَّهُمَّ احْفَظْنِي مِنْ بَيْنِ يَدَيَّ، وَمِنْ خَلْفِي، وَعَنْ يَمِينِي، وَعَنْ شِمَالِي، وَمِنْ فَوْقِي، وَأَعُوذُ بِعَظَمَتِكَ أَنْ أُغْتَالَ مِنْ تَحْتِي.", 1, "دعاء حفظ من كل جانب", "أبو داود", "النوم"),
        CompleteDhikr(214, "بِسْمِ اللَّهِ الَّذِي لاَ يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الأَرْضِ وَلاَ فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ.", 3, "لم يضره شيء", "أبو داود", "النوم"),
        CompleteDhikr(215, "اللَّهُمَّ رَبَّ السَّمَاوَاتِ السَّبْعِ وَرَبَّ الأَرْضِ وَرَبَّ الْعَرْشِ الْعَظِيمِ، رَبَّنَا وَرَبَّ كُلِّ شَيْءٍ، فَالِقَ الْحَبِّ وَالنَّوَى، وَمُنزِلَ التَّوْرَاةِ وَالإِنْجِيلِ وَالْفُرْقَانِ.", 1, "من قالها عند النوم غُفرت ذنوبه", "صحيح البخاري", "النوم")
    )
}

/**
 * أذكار الاستيقاظ - 10 ذكر
 */
object WakeUpAdhkar {
    val adhkar = listOf(
        CompleteDhikr(301, "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ.", 1, "من قالها عند الاستيقاظ كتب الله له ما بعدها من الحسنات", "صحيح البخاري ومسلم", "الاستيقاظ"),
        CompleteDhikr(302, "لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ.", 10, "من قالها عند الاستيقاظ كتب الله له ما بعدها", "صحيح البخاري ومسلم", "الاستيقاظ"),
        CompleteDhikr(303, "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ، سُبْحَانَ اللَّهِ الْعَظِيمِ.", 10, "من أعظم أذكار الاستيقاظ", "صحيح مسلم", "الاستيقاظ"),
        CompleteDhikr(304, "اللَّهُمَّ بِكَ أَصْبَحْنَا وَبِكَ أَمْسَيْنَا وَبِكَ نَحْيَا وَبِكَ نَمُوتُ وَإِلَيْكَ النُّشُورُ.", 1, "أذكار الصباح النبوية", "الترمذي", "الاستيقاظ"),
        CompleteDhikr(305, "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْكُفْرِ وَالْفَقْرِ وَعَذَابِ الْقَبْرِ.", 3, "الاستعاذة من الثلاث", "الترمذي", "الاستيقاظ"),
        CompleteDhikr(306, "حَسْبِيَ اللَّهُ لاَ إِلَهَ إِلاَّ هُوَ عَلَيْهِ تَوَكَّلْتُ.", 7, "كفاه الله ما أهمه", "أبو داود", "الاستيقاظ"),
        CompleteDhikr(307, "اللَّهُمَّ صَلِّ وَسَلِّمْ عَلَى نَبِيِّنَا مُحَمَّدٍ.", 1, "من صلى علي صلاة صلى الله عليه بها عشراً", "الترمذي", "الاستيقاظ"),
        CompleteDhikr(308, "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ، عَدَدَ خَلْقِهِ، وَرِضَا نَفْسِهِ، وَزِنَةَ عَرْشِهِ، وَمِدَادَ كَلِمَاتِهِ.", 3, "ثقيل في الميزان", "صحيح مسلم", "الاستيقاظ"),
        CompleteDhikr(309, "اللَّهُمَّ مَا أَصْبَحَ بِي مِنْ نِعْمَةٍ فَمِنْكَ وَحْدَكَ لاَ شَرِيكَ لَكَ، فَلَكَ الْحَمْدُ وَلَكَ الشُّكْرُ.", 1, "أداء شكر النعمة", "أبو داود", "الاستيقاظ"),
        CompleteDhikr(310, "بِسْمِ اللَّهِ الَّذِي لاَ يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الأَرْضِ وَلاَ فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ.", 3, "لم يضره شيء", "أبو داود", "الاستيقاظ")
    )
}

/**
 * أذكار المسجد
 */
object MosqueAdhkar {
    val adhkar = listOf(
        CompleteDhikr(401, "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ.", 1, "من قالها عند دخول المسجد فتح الله له أبواب الجنة", "صحيح مسلم", "المسجد"),
        CompleteDhikr(402, "أَعُوذُ بِاللَّهِ الْعَظِيمِ، وَبِوَجْهِهِ الْكَرِيمِ، وَسُلْطَانِهِ الْقَدِيمِ، مِنَ الشَّيْطَانِ الرَّجِيمِ.", 1, "من قالها عند دخول المسجد قال الشيطان: حُفظ مني سائر اليوم", "أبو داود", "المسجد"),
        CompleteDhikr(403, "بِسْمِ اللَّهِ، وَالصَّلَاةُ وَالسَّلَامُ عَلَى رَسُولِ اللَّهِ.", 1, "دعاء الدخول المشروع", "الترمذي", "المسجد"),
        CompleteDhikr(404, "رَبِّ اغْفِرْ لِي ذَنْبِي، وَافْتَحْ لِي أَبْوَابَ رَحْمَتِكَ.", 1, "دعاء عند الخروج من المسجد", "صحيح مسلم", "المسجد"),
        CompleteDhikr(405, "اللَّهُمَّ إِنِّي أَسْأَلُكَ مِنْ فَضْلِكَ وَرَحْمَتِكَ، فَإِنَّهُ لاَ يَمْلِكُهَا إِلاَّ أَنْتَ.", 1, "دعاء عظيم الفضل", "الترمذي", "المسجد")
    )
}

/**
 * أذكار الطعام
 */
object FoodAdhkar {
    val adhkar = listOf(
        CompleteDhikr(501, "بِسْمِ اللَّهِ.", 1, "التسمية في أول الطعام", "صحيح البخاري", "الطعام"),
        CompleteDhikr(502, "بِسْمِ اللَّهِ أَوَّلَهُ وَآخِرَهُ.", 1, "من نسي التسمية في أوله", "أبو داود والترمذي", "الطعام"),
        CompleteDhikr(503, "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنَا وَسَقَانَا وَجَعَلَنَا مُسْلِمِينَ.", 1, "من قالها بعد الأكل أو الشرب", "أبو داود", "الطعام"),
        CompleteDhikr(504, "اللَّهُمَّ بَارِكْ لَنَا فِيمَا رَزَقْتَنَا وَقِنَا عَذَابَ النَّارِ، بِسْمِ اللَّهِ.", 1, "دعاء قبل الأكل", "ابن السني", "الطعام"),
        CompleteDhikr(505, "اللَّهُمَّ أَطْعَمْ مَنْ أَطْعَمَنِي، وَاسْقِ مَنْ سَقَانِي.", 1, "من أطعمه الله طعاماً فليقل هذا", "صحيح مسلم", "الطعام"),
        CompleteDhikr(506, "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنِي هَذَا وَرَزَقَنِيهِ مِنْ غَيْرِ حَوْلٍ مِنِّي وَلاَ قُوَّةٍ.", 1, "غُفر له ما تقدم من ذنبه", "الترمذي", "الطعام"),
        CompleteDhikr(507, "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْجُوعِ، فَإِنَّهُ بِئْسَ الضَّجِيعُ.", 1, "الاستعاذة من الجوع", "أبو داود", "الطعام"),
        CompleteDhikr(508, "سُبْحَانَ اللَّهِ.", 33, "بعد الأكل شكراً لله", "صحيح مسلم", "الطعام"),
        CompleteDhikr(509, "الْحَمْدُ لِلَّهِ.", 33, "بعد الأكل شكراً لله", "صحيح مسلم", "الطعام"),
        CompleteDhikr(510, "اللَّهُ أَكْبَرُ.", 34, "بعد الأكل شكراً لله", "صحيح مسلم", "الطعام")
    )
}

/**
 * View موحد لعرض كل الأذكار
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteAdhkarView(onBack: () -> Unit) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("morning") }
    val bookmarks = remember { mutableStateListOf<Int>() }

    val categories = listOf(
        Triple("morning", "🌅 أذكار الصباح", MorningAdhkar.adhkar),
        Triple("evening", "🌙 أذكار المساء", EveningAdhkar.adhkar),
        Triple("sleep", "💤 أذكار النوم", SleepAdhkar.adhkar),
        Triple("wake", "⏰ أذكار الاستيقاظ", WakeUpAdhkar.adhkar),
        Triple("mosque", "🕌 أذكار المسجد", MosqueAdhkar.adhkar),
        Triple("food", "🍽️ أذكار الطعام", FoodAdhkar.adhkar)
    )

    val currentList = categories.find { it.first == selectedCategory }?.third ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📿 الأذكار الكاملة", fontWeight = FontWeight.Bold, color = Color.White) },
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
            // Categories chips
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(androidx.compose.foundation.rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { (id, name, _) ->
                        FilterChip(
                            selected = selectedCategory == id,
                            onClick = { selectedCategory = id },
                            label = { Text(name, fontSize = 11.sp) }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Stats
            Text(
                "${currentList.size} ذكر في هذه الفئة",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Dhikr cards
            currentList.forEach { dhikr ->
                CompleteAdhkarCard(
                    dhikr = dhikr,
                    isBookmarked = dhikr.id in bookmarks,
                    onBookmark = {
                        if (dhikr.id in bookmarks) bookmarks.remove(dhikr.id)
                        else bookmarks.add(dhikr.id)
                        BookmarkStorage.save(context, dhikr.id, !it)
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
fun CompleteAdhkarCard(
    dhikr: CompleteDhikr,
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
                IconButton(onClick = { onBookmark(!isBookmarked) }) {
                    Icon(
                        if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint = if (isBookmarked) Color(0xFFC9A84C) else Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                dhikr.text,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Surface(
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        "💎 ${dhikr.benefit}",
                        fontSize = 12.sp,
                        color = Color(0xFF1B5E20)
                    )
                    Text(
                        "📚 ${dhikr.source}",
                        fontSize = 10.sp,
                        color = Color(0xFF1B5E20).copy(alpha = 0.7f),
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
