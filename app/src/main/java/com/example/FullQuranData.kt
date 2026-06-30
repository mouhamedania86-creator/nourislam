package com.example

/**
 * القرآن الكريم كاملاً - 114 سورة
 * بيانات السور والآيات
 *
 * النص محفوظ في assets/quran_full.txt (للتحميل من السيرفر)
 * هنا فقط البيانات الوصفية (metadata) للبحث السريع
 */

data class QuranSurahMeta(
    val number: Int,
    val nameAr: String,
    val nameEn: String,
    val transliteration: String,
    val verseCount: Int,
    val type: String, // مكية or مدنية
    val revelationOrder: Int,
    val startPage: Int // رقم الصفحة في المصحف الشريف
)

data class QuranVerse(
    val surahNumber: Int,
    val verseNumber: Int,
    val textAr: String,
    val juz: Int,
    val page: Int,
    val audioUrl: String? = null
)

/**
 * بيانات وصفية للـ 114 سورة
 */
object FullQuranMetadata {
    val surahs: List<QuranSurahMeta> = listOf(
        QuranSurahMeta(1, "الفاتحة", "The Opening", "Al-Fatihah", 7, "مكية", 5, 1),
        QuranSurahMeta(2, "البقرة", "The Cow", "Al-Baqarah", 286, "مدنية", 87, 2),
        QuranSurahMeta(3, "آل عمران", "The Family of Imraan", "Ali 'Imran", 200, "مدنية", 89, 50),
        QuranSurahMeta(4, "النساء", "The Women", "An-Nisa", 176, "مدنية", 92, 77),
        QuranSurahMeta(5, "المائدة", "The Table", "Al-Ma'idah", 120, "مدنية", 112, 106),
        QuranSurahMeta(6, "الأنعام", "The Cattle", "Al-An'am", 165, "مكية", 55, 128),
        QuranSurahMeta(7, "الأعراف", "The Heights", "Al-A'raf", 206, "مكية", 39, 151),
        QuranSurahMeta(8, "الأنفال", "The Spoils of War", "Al-Anfal", 75, "مدنية", 88, 177),
        QuranSurahMeta(9, "التوبة", "The Repentance", "At-Tawbah", 129, "مدنية", 113, 187),
        QuranSurahMeta(10, "يونس", "Jonas", "Yunus", 109, "مكية", 51, 208),
        QuranSurahMeta(11, "هود", "Hud", "Hud", 123, "مكية", 52, 221),
        QuranSurahMeta(12, "يوسف", "Joseph", "Yusuf", 111, "مكية", 53, 235),
        QuranSurahMeta(13, "الرعد", "The Thunder", "Ar-Ra'd", 43, "مدنية", 96, 249),
        QuranSurahMeta(14, "إبراهيم", "Abraham", "Ibrahim", 52, "مكية", 72, 255),
        QuranSurahMeta(15, "الحجر", "The Rocky Tract", "Al-Hijr", 99, "مكية", 54, 262),
        QuranSurahMeta(16, "النحل", "The Bee", "An-Nahl", 128, "مكية", 70, 267),
        QuranSurahMeta(17, "الإسراء", "The Night Journey", "Al-Isra", 111, "مكية", 50, 282),
        QuranSurahMeta(18, "الكهف", "The Cave", "Al-Kahf", 110, "مكية", 69, 293),
        QuranSurahMeta(19, "مريم", "Mary", "Maryam", 98, "مكية", 44, 305),
        QuranSurahMeta(20, "طه", "Ta-Ha", "Ta-Ha", 135, "مكية", 45, 312),
        QuranSurahMeta(21, "الأنبياء", "The Prophets", "Al-Anbiya", 112, "مكية", 73, 322),
        QuranSurahMeta(22, "الحج", "The Pilgrimage", "Al-Hajj", 78, "مدنية", 103, 332),
        QuranSurahMeta(23, "المؤمنون", "The Believers", "Al-Mu'minun", 118, "مكية", 74, 342),
        QuranSurahMeta(24, "النور", "The Light", "An-Nur", 64, "مدنية", 102, 350),
        QuranSurahMeta(25, "الفرقان", "The Criterion", "Al-Furqan", 77, "مكية", 42, 359),
        QuranSurahMeta(26, "الشعراء", "The Poets", "Ash-Shu'ara", 227, "مكية", 47, 367),
        QuranSurahMeta(27, "النمل", "The Ant", "An-Naml", 93, "مكية", 48, 376),
        QuranSurahMeta(28, "القصص", "The Stories", "Al-Qasas", 88, "مكية", 49, 385),
        QuranSurahMeta(29, "العنكبوت", "The Spider", "Al-'Ankabut", 69, "مكية", 85, 396),
        QuranSurahMeta(30, "الروم", "The Romans", "Ar-Rum", 60, "مكية", 84, 404),
        QuranSurahMeta(31, "لقمان", "Luqman", "Luqman", 34, "مكية", 57, 411),
        QuranSurahMeta(32, "السجدة", "The Prostration", "As-Sajdah", 30, "مكية", 75, 415),
        QuranSurahMeta(33, "الأحزاب", "The Clans", "Al-Ahzab", 73, "مدنية", 90, 418),
        QuranSurahMeta(34, "سبأ", "Sheba", "Saba", 54, "مكية", 58, 428),
        QuranSurahMeta(35, "فاطر", "Originator", "Fatir", 45, "مكية", 43, 434),
        QuranSurahMeta(36, "يس", "Ya-Sin", "Ya-Sin", 83, "مكية", 41, 440),
        QuranSurahMeta(37, "الصافات", "Those Who Set the Ranks", "As-Saffat", 182, "مكية", 56, 446),
        QuranSurahMeta(38, "ص", "The Letter Saad", "Sad", 88, "مكية", 38, 453),
        QuranSurahMeta(39, "الزمر", "The Groups", "Az-Zumar", 75, "مكية", 59, 458),
        QuranSurahMeta(40, "غافر", "The Forgiver", "Ghafir", 85, "مكية", 60, 467),
        QuranSurahMeta(41, "فصلت", "Explained in Detail", "Fussilat", 54, "مكية", 61, 477),
        QuranSurahMeta(42, "الشورى", "The Consultation", "Ash-Shura", 53, "مكية", 62, 482),
        QuranSurahMeta(43, "الزخرف", "The Ornaments of Gold", "Az-Zukhruf", 89, "مكية", 63, 489),
        QuranSurahMeta(44, "الدخان", "The Smoke", "Ad-Dukhan", 59, "مكية", 64, 496),
        QuranSurahMeta(45, "الجاثية", "The Crouching", "Al-Jathiyah", 37, "مكية", 65, 499),
        QuranSurahMeta(46, "الأحقاف", "The Wind-Curved Sandhills", "Al-Ahqaf", 35, "مكية", 66, 502),
        QuranSurahMeta(47, "محمد", "Muhammad", "Muhammad", 38, "مدنية", 95, 507),
        QuranSurahMeta(48, "الفتح", "The Victory", "Al-Fath", 29, "مدنية", 111, 511),
        QuranSurahMeta(49, "الحجرات", "The Rooms", "Al-Hujurat", 18, "مدنية", 106, 515),
        QuranSurahMeta(50, "ق", "The Letter Qaf", "Qaf", 45, "مكية", 34, 518),
        QuranSurahMeta(51, "الذاريات", "The Winnowing Winds", "Adh-Dhariyat", 60, "مكية", 67, 523),
        QuranSurahMeta(52, "الطور", "The Mount", "At-Tur", 49, "مكية", 76, 523),
        QuranSurahMeta(53, "النجم", "The Star", "An-Najm", 62, "مكية", 23, 526),
        QuranSurahMeta(54, "القمر", "The Moon", "Al-Qamar", 55, "مكية", 37, 528),
        QuranSurahMeta(55, "الرحمن", "The Most Gracious", "Ar-Rahman", 78, "مدنية", 97, 531),
        QuranSurahMeta(56, "الواقعة", "The Inevitable", "Al-Waqi'ah", 96, "مكية", 46, 534),
        QuranSurahMeta(57, "الحديد", "The Iron", "Al-Hadid", 29, "مدنية", 94, 537),
        QuranSurahMeta(58, "المجادلة", "The Pleading Woman", "Al-Mujadilah", 22, "مدنية", 105, 542),
        QuranSurahMeta(59, "الحشر", "The Gathering", "Al-Hashr", 24, "مدنية", 101, 545),
        QuranSurahMeta(60, "الممتحنة", "She That is to be Examined", "Al-Mumtahanah", 13, "مدنية", 91, 549),
        QuranSurahMeta(61, "الصف", "The Ranks", "As-Saff", 14, "مدنية", 109, 551),
        QuranSurahMeta(62, "الجمعة", "The Congregation", "Al-Jumu'ah", 11, "مدنية", 110, 553),
        QuranSurahMeta(63, "المنافقون", "The Hypocrites", "Al-Munafiqun", 11, "مدنية", 104, 554),
        QuranSurahMeta(64, "التغابن", "Mutual Disillusion", "At-Taghabun", 18, "مدنية", 108, 556),
        QuranSurahMeta(65, "الطلاق", "Divorce", "At-Talaq", 12, "مدنية", 99, 558),
        QuranSurahMeta(66, "التحريم", "The Prohibition", "At-Tahrim", 12, "مدنية", 107, 560),
        QuranSurahMeta(67, "الملك", "The Dominion", "Al-Mulk", 30, "مكية", 77, 562),
        QuranSurahMeta(68, "القلم", "The Pen", "Al-Qalam", 52, "مكية", 2, 564),
        QuranSurahMeta(69, "الحاقة", "The Reality", "Al-Haqqah", 52, "مكية", 78, 566),
        QuranSurahMeta(70, "المعارج", "The Ascending Stairways", "Al-Ma'arij", 44, "مكية", 79, 568),
        QuranSurahMeta(71, "نوح", "Noah", "Nuh", 28, "مكية", 71, 570),
        QuranSurahMeta(72, "الجن", "The Jinn", "Al-Jinn", 28, "مكية", 40, 572),
        QuranSurahMeta(73, "المزمل", "The Enshrouded One", "Al-Muzzammil", 20, "مكية", 3, 574),
        QuranSurahMeta(74, "المدثر", "The Cloaked One", "Al-Muddaththir", 56, "مكية", 4, 575),
        QuranSurahMeta(75, "القيامة", "The Resurrection", "Al-Qiyamah", 40, "مكية", 31, 577),
        QuranSurahMeta(76, "الإنسان", "Man", "Al-Insan", 31, "مدنية", 98, 578),
        QuranSurahMeta(77, "المرسلات", "The Emissaries", "Al-Mursalat", 50, "مكية", 33, 580),
        QuranSurahMeta(78, "النبأ", "The Tidings", "An-Naba", 40, "مكية", 80, 582),
        QuranSurahMeta(79, "النازعات", "Those Who Drag Forth", "An-Nazi'at", 46, "مكية", 81, 583),
        QuranSurahMeta(80, "عبس", "He Frowned", "Abasa", 42, "مكية", 24, 585),
        QuranSurahMeta(81, "التكوير", "The Overthrowing", "At-Takwir", 29, "مكية", 7, 586),
        QuranSurahMeta(82, "الانفطار", "The Cleaving", "Al-Infitar", 19, "مكية", 82, 587),
        QuranSurahMeta(83, "المطففين", "Defrauding", "Al-Mutaffifin", 36, "مكية", 86, 587),
        QuranSurahMeta(84, "الانشقاق", "The Splitting Open", "Al-Inshiqaq", 25, "مكية", 83, 589),
        QuranSurahMeta(85, "البروج", "The Constellations", "Al-Buruj", 22, "مكية", 27, 590),
        QuranSurahMeta(86, "الطارق", "The Morning Star", "At-Tariq", 17, "مكية", 36, 591),
        QuranSurahMeta(87, "الأعلى", "The Most High", "Al-A'la", 19, "مكية", 8, 591),
        QuranSurahMeta(88, "الغاشية", "The Overwhelming", "Al-Ghashiyah", 26, "مكية", 68, 592),
        QuranSurahMeta(89, "الفجر", "The Dawn", "Al-Fajr", 30, "مكية", 10, 593),
        QuranSurahMeta(90, "البلد", "The City", "Al-Balad", 20, "مكية", 35, 594),
        QuranSurahMeta(91, "الشمس", "The Sun", "Ash-Shams", 15, "مكية", 26, 595),
        QuranSurahMeta(92, "الليل", "The Night", "Al-Layl", 21, "مكية", 9, 595),
        QuranSurahMeta(93, "الضحى", "The Morning Hours", "Ad-Duha", 11, "مكية", 11, 596),
        QuranSurahMeta(94, "الشرح", "The Relief", "Ash-Sharh", 8, "مكية", 12, 596),
        QuranSurahMeta(95, "التين", "The Fig", "At-Tin", 8, "مكية", 28, 597),
        QuranSurahMeta(96, "العلق", "The Clot", "Al-'Alaq", 19, "مكية", 1, 597),
        QuranSurahMeta(97, "القدر", "The Power", "Al-Qadr", 5, "مكية", 25, 598),
        QuranSurahMeta(98, "البينة", "The Clear Proof", "Al-Bayyinah", 8, "مدنية", 100, 598),
        QuranSurahMeta(99, "الزلزلة", "The Earthquake", "Az-Zalzalah", 8, "مدنية", 93, 599),
        QuranSurahMeta(100, "العاديات", "The Coursers", "Al-'Adiyat", 11, "مكية", 14, 599),
        QuranSurahMeta(101, "القارعة", "The Striking", "Al-Qari'ah", 11, "مكية", 30, 600),
        QuranSurahMeta(102, "التكاثر", "The Piling Up", "At-Takathur", 8, "مكية", 16, 600),
        QuranSurahMeta(103, "العصر", "The Declining Day", "Al-'Asr", 3, "مكية", 13, 601),
        QuranSurahMeta(104, "الهمزة", "The Traducer", "Al-Humazah", 9, "مكية", 32, 601),
        QuranSurahMeta(105, "الفيل", "The Elephant", "Al-Fil", 5, "مكية", 19, 601),
        QuranSurahMeta(106, "قريش", "Quraysh", "Quraysh", 4, "مكية", 29, 602),
        QuranSurahMeta(107, "الماعون", "Almsgiving", "Al-Ma'un", 7, "مكية", 17, 602),
        QuranSurahMeta(108, "الكوثر", "The Abundance", "Al-Kawthar", 3, "مكية", 15, 602),
        QuranSurahMeta(109, "الكافرون", "The Disbelievers", "Al-Kafirun", 6, "مكية", 18, 603),
        QuranSurahMeta(110, "النصر", "The Help", "An-Nasr", 3, "مدنية", 114, 603),
        QuranSurahMeta(111, "المسد", "The Palm Fiber", "Al-Masad", 5, "مكية", 6, 603),
        QuranSurahMeta(112, "الإخلاص", "The Sincerity", "Al-Ikhlas", 4, "مكية", 22, 604),
        QuranSurahMeta(113, "الفلق", "The Daybreak", "Al-Falaq", 5, "مكية", 20, 604),
        QuranSurahMeta(114, "الناس", "Mankind", "An-Nas", 6, "مكية", 21, 604)
    )

    fun getSurah(number: Int): QuranSurahMeta? = surahs.find { it.number == number }

    fun searchSurahs(query: String): List<QuranSurahMeta> {
        if (query.isBlank()) return surahs
        val q = query.lowercase()
        return surahs.filter {
            it.nameAr.contains(query, ignoreCase = true) ||
            it.nameEn.contains(query, ignoreCase = true) ||
            it.transliteration.contains(query, ignoreCase = true)
        }
    }
}

/**
 * القراء المتوفرون
 */
data class QuranReciter(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val baseUrl: String,
    val bitrate: String,
    val style: String
)

object QuranReciters {
    val reciters = listOf(
        QuranReciter(
            "afs", "مشاري راشد العفاسي", "Mishary Al-Afasy",
            "https://backup.qurango.net/afs", "128kbps", "ترتيل"
        ),
        QuranReciter(
            "sudais", "عبد الرحمن السديس", "Abdulrahman Al-Sudais",
            "https://backup.qurango.net/sudais", "128kbps", "ترتيل"
        ),
        QuranReciter(
            "maher", "ماهر المعيقلي", "Maher Al-Muaiqly",
            "https://backup.qurango.net/maher", "128kbps", "ترتيل"
        ),
        QuranReciter(
            "minshawi", "محمد صديق المنشاوي", "Mohamed Al-Minshawi",
            "https://backup.qurango.net/minshawi", "128kbps", "مجود"
        ),
        QuranReciter(
            "husary", "محمود خليل الحصري", "Mahmoud Khalil Al-Husary",
            "https://backup.qurango.net/husary", "128kbps", "ترتيل"
        ),
        QuranReciter(
            "basit", "عبد الباسط عبد الصمد", "Abdul Basit Abdul Samad",
            "https://backup.qurango.net/basit", "192kbps", "مجود"
        ),
        QuranReciter(
            "abdulbasit_murattal", "عبد الباسط (مرتل)", "Abdul Basit (Murattal)",
            "https://backup.qurango.net/abdulbasit_murattal", "128kbps", "مرتل"
        ),
        QuranReciter(
            "yasser", "ياسر الدوسري", "Yasser Al-Dosari",
            "https://backup.qurango.net/yasser", "128kbps", "ترتيل"
        )
    )

    fun getReciter(id: String): QuranReciter? = reciters.find { it.id == id }

    /**
     * توليد URL للـ MP3 لآية معينة
     */
    fun getAudioUrl(reciterId: String, surahNumber: Int, verseNumber: Int? = null): String {
        val reciter = getReciter(reciterId) ?: return ""
        val surahPart = surahNumber.toString().padStart(3, '0')
        val versePart = verseNumber?.toString()?.padStart(3, '0') ?: ""
        return if (versePart.isEmpty()) {
            "${reciter.baseUrl}/$surahPart.mp3"
        } else {
            "${reciter.baseUrl}/${surahPart}${versePart}.mp3"
        }
    }

    /**
     * URL سورة كاملة
     */
    fun getSurahAudioUrl(reciterId: String, surahNumber: Int): String {
        val reciter = getReciter(reciterId) ?: return ""
        val surahPart = surahNumber.toString().padStart(3, '0')
        return "${reciter.baseUrl}/$surahPart.mp3"
    }
}
