package com.example

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

/**
 * مساعد المشاركة
 * يدعم مشاركة: آيات قرآنية، أحاديث، أدعية، أذكار
 */
object ShareHelper {

    /**
     * مشاركة آية
     */
    fun shareAyah(
        context: Context,
        surahName: String,
        ayahNumber: Int,
        text: String,
        translation: String? = null
    ) {
        val message = buildString {
            appendLine("🕌 القرآن الكريم")
            appendLine()
            appendLine("﴿ $text ﴾")
            appendLine()
            appendLine("📖 سورة $surahName - آية $ayahNumber")
            if (!translation.isNullOrBlank()) {
                appendLine()
                appendLine("🔹 الترجمة:")
                appendLine(translation)
            }
            appendLine()
            append("📲 تطبيق نور الإسلام")
        }
        shareText(context, message, "آية من القرآن الكريم")
    }

    /**
     * مشاركة حديث
     */
    fun shareHadith(context: Context, hadith: Hadith) {
        val message = buildString {
            appendLine("📜 حديث شريف")
            appendLine()
            appendLine(hadith.text)
            appendLine()
            appendLine("📖 الراوي: ${hadith.narrator}")
            appendLine("📚 المصدر: ${hadith.book} (${hadith.grade})")
            appendLine("🔢 رقم: ${hadith.reference}")
            appendLine()
            appendLine("💡 ${hadith.explanation}")
            appendLine()
            append("📲 تطبيق نور الإسلام")
        }
        shareText(context, message, "حديث شريف")
    }

    /**
     * مشاركة دعاء
     */
    fun shareDua(context: Context, duaText: String, source: String = "") {
        val message = buildString {
            appendLine("🤲 دعاء")
            appendLine()
            appendLine(duaText)
            if (source.isNotBlank()) {
                appendLine()
                appendLine("📖 $source")
            }
            appendLine()
            append("📲 تطبيق نور الإسلام")
        }
        shareText(context, message, "دعاء")
    }

    /**
     * مشاركة اسم من أسماء الله
     */
    fun shareNameOfAllah(context: Context, name: NameOfAllah) {
        val message = buildString {
            appendLine("✨ اسم من أسماء الله الحسنى")
            appendLine()
            appendLine("${name.nameAr} (${name.number}/99)")
            appendLine()
            appendLine("English: ${name.nameEn}")
            appendLine()
            appendLine("المعنى: ${name.meaningAr}")
            appendLine()
            appendLine("Meaning: ${name.meaningEn}")
            appendLine()
            append("📲 تطبيق نور الإسلام")
        }
        shareText(context, message, "اسم من أسماء الله")
    }

    /**
     * مشاركة آية + تفسير
     */
    fun shareAyahWithTafseer(
        context: Context,
        surahName: String,
        ayahNumber: Int,
        text: String,
        tafseer: String
    ) {
        val message = buildString {
            appendLine("🕌 القرآن مع التفسير")
            appendLine()
            appendLine("﴿ $text ﴾")
            appendLine()
            appendLine("📖 سورة $surahName - آية $ayahNumber")
            appendLine()
            appendLine("📚 التفسير:")
            appendLine(tafseer)
            appendLine()
            append("📲 تطبيق نور الإسلام")
        }
        shareText(context, message, "آية مع تفسير")
    }

    /**
     * مشاركة نص عام
     */
    private fun shareText(context: Context, text: String, subject: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, text)
            }
            val chooser = Intent.createChooser(intent, "مشاركة عبر")
            chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(chooser)
        } catch (e: Exception) {
            Toast.makeText(context, "خطأ في المشاركة: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * نسخ نص للحافظة
     */
    fun copyToClipboard(context: Context, text: String, label: String = "نص") {
        try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText(label, text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "✅ تم النسخ", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "خطأ في النسخ", Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * تخزين المفضلات (Bookmarks)
 * يخزن في SharedPreferences (يمكن ترقيته لـ Room لاحقاً)
 */
object BookmarkStorage {
    private const val PREF_NAME = "noor_islam_bookmarks"
    private const val KEY_BOOKMARKS = "bookmark_ids"

    /**
     * حفظ/إزالة bookmark
     */
    fun save(context: Context, id: Int, add: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val current = getAll(context).toMutableSet()
        if (add) current.add(id) else current.remove(id)
        prefs.edit().putStringSet(KEY_BOOKMARKS, current.map { it.toString() }.toSet()).apply()
    }

    /**
     * كل الـ bookmarks
     */
    fun getAll(context: Context): Set<Int> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val strSet = prefs.getStringSet(KEY_BOOKMARKS, emptySet()) ?: emptySet()
        return strSet.mapNotNull { it.toIntOrNull() }.toSet()
    }

    /**
     * حذف الكل
     */
    fun clearAll(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_BOOKMARKS).apply()
    }

    /**
     * حفظ bookmark مع نوع
     */
    fun saveWithType(context: Context, type: String, id: Int, add: Boolean) {
        val key = "bookmark_${type}_${id}"
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (add) prefs.edit().putBoolean(key, true).apply()
        else prefs.edit().remove(key).apply()
    }

    /**
     * هل هذا محفوظ؟
     */
    fun isBookmarked(context: Context, type: String, id: Int): Boolean {
        val key = "bookmark_${type}_${id}"
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(key, false)
    }
}
