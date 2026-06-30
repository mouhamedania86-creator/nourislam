package com.example

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

/**
 * ويدجت مواقيت الصلاة
 * يعرض على الشاشة الرئيسية:
 * - الوقت الحالي
 * - اسم الصلاة القادمة
 * - الوقت المتبقي
 */
class PrayerTimesWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d(TAG, "Widget update for ${appWidgetIds.size} widgets")
        appWidgetIds.forEach { widgetId ->
            updateAppWidget(context, appWidgetManager, widgetId)
        }
    }

    override fun onEnabled(context: Context) {
        Log.d(TAG, "Widget enabled")
    }

    override fun onDisabled(context: Context) {
        Log.d(TAG, "Widget disabled")
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        widgetId: Int
    ) {
        try {
            val views = RemoteViews(context.packageName, R.layout.prayer_widget)

            // Get prayer data
            val db = AppDatabase.getDatabase(context)
            val prayerData = db.prayerCacheDao().getLatestSync()

            val city = prayerData?.city ?: "الجزائر"
            val nextPrayer = calculateNextPrayerFromCache(prayerData)
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

            // Set widget views
            views.setTextViewText(R.id.widget_city, "📍 $city")
            views.setTextViewText(R.id.widget_time, currentTime)
            views.setTextViewText(R.id.widget_next_prayer, nextPrayer.first)
            views.setTextViewText(R.id.widget_time_left, nextPrayer.second)

            // Click to open app
            val openAppIntent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context, 0, openAppIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)

            appWidgetManager.updateAppWidget(widgetId, views)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update widget", e)
        }
    }

    private fun calculateNextPrayerFromCache(prayer: PrayerCacheEntity?): Pair<String, String> {
        if (prayer == null) return "—" to "—"

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val now = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
        val nowTime = sdf.parse(now) ?: return "—" to "—"

        val prayers = listOf(
            "الفجر" to (prayer.Fajr.split(" ")[0]),
            "الظهر" to (prayer.Dhuhr.split(" ")[0]),
            "العصر" to (prayer.Asr.split(" ")[0]),
            "المغرب" to (prayer.Maghrib.split(" ")[0]),
            "العشاء" to (prayer.Isha.split(" ")[0])
        )

        for ((name, time) in prayers) {
            val prayerTime = sdf.parse(time) ?: continue
            if (prayerTime.after(nowTime)) {
                val diff = prayerTime.time - nowTime.time
                val hours = diff / (1000 * 60 * 60)
                val minutes = (diff / (1000 * 60)) % 60
                return name to "باقي ${hours}س ${minutes}د"
            }
        }

        return "الفجر" to "غداً"
    }

    companion object {
        private const val TAG = "PrayerTimesWidget"

        /**
         * تحديث كل الويدجتات (يُستدعى عند تحديث البيانات)
         */
        fun updateAllWidgets(context: Context) {
            try {
                val intent = Intent(context, PrayerTimesWidget::class.java)
                intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                val ids = AppWidgetManager.getInstance(context)
                    .getAppWidgetIds(ComponentName(context, PrayerTimesWidget::class.java))
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
                context.sendBroadcast(intent)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update widgets", e)
            }
        }
    }
}
