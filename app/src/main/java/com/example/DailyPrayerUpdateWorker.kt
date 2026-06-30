package com.example

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * WorkManager worker that runs daily to:
 * 1. Fetch updated prayer times from API
 * 2. Reschedule all prayer alarms
 * 3. Schedule pre-adhan notifications
 *
 * This ensures that even if user doesn't open the app for days,
 * the prayer times stay fresh and alarms are always up to date.
 */
class DailyPrayerUpdateWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "═══════════════════════════════════════")
            Log.d(TAG, "  DailyPrayerUpdateWorker started")
            Log.d(TAG, "═══════════════════════════════════════")

            val context = applicationContext

            // 1. Get current settings (city, country, calculation method)
            val database = AppDatabase.getDatabase(context)
            val settings = database.settingsDao().getSettingsDirect()
                ?: SettingsEntity().also {
                    Log.w(TAG, "No settings found, using defaults")
                }

            // 2. Fetch updated prayer times from AlAdhan API
            Log.d(TAG, "📡 Fetching prayer times for ${settings.city}, ${settings.country}")
            val repository = IslamicRepository(
                settingsDao = database.settingsDao(),
                prayerCacheDao = database.prayerCacheDao(),
                tasbihDao = database.tasbihDao()
            )

            val timings = repository.getPrayerTimes(
                city = settings.city,
                country = settings.country,
                method = settings.calculationMethod
            )

            if (timings == null) {
                Log.e(TAG, "❌ Failed to fetch prayer times")
                return@withContext Result.retry()
            }

            Log.d(TAG, "✅ Prayer times fetched:")
            Log.d(TAG, "   Fajr: ${timings.Fajr}")
            Log.d(TAG, "   Dhuhr: ${timings.Dhuhr}")
            Log.d(TAG, "   Asr: ${timings.Asr}")
            Log.d(TAG, "   Maghrib: ${timings.Maghrib}")
            Log.d(TAG, "   Isha: ${timings.Isha}")

            // 2.5. Fetch 7 days of prayer times (for weekly caching)
            Log.d(TAG, "📅 Fetching 7 days of prayer times...")
            val weeklyPrayers = repository.getWeeklyPrayerTimes(
                city = settings.city,
                country = settings.country,
                method = settings.calculationMethod,
                weeklyDao = database.weeklyPrayerDao()
            )
            Log.d(TAG, "✅ Cached ${weeklyPrayers.size} days")

            // 3. Reschedule all alarms for today (prayer + pre-adhan)
            IslamicAlarmReceiver.scheduleAllDailyAlarms(context, timings)
            IslamicAlarmReceiver.scheduleAllPreAdhanAlarms(context, timings)

            // 3.5 Schedule alarms for upcoming days (next 6 days)
            IslamicAlarmReceiver.scheduleWeeklyAlarms(context, weeklyPrayers, database.weeklyPrayerDao())

            // 4. Reschedule periodic dua
            IslamicAlarmReceiver.schedulePeriodicDua(context)

            // 5. Show daily hadith notification
            IslamicNotificationManager.showDailyHadithNotification(context)

            // 6. If Friday, show Jumu'ah notification
            val calendar = java.util.Calendar.getInstance()
            if (calendar.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.FRIDAY) {
                IslamicNotificationManager.showJumuahNotification(context)
            }

            // 7. If Ramadan, show Ramadan notification
            if (timings.hijriDate.contains("رمضان")) {
                IslamicNotificationManager.showRamadanNotification(context)
            }

            Log.d(TAG, "✅ DailyPrayerUpdateWorker completed successfully")

            // 8. تحديث الـ Widget على الشاشة الرئيسية
            PrayerTimesWidget.updateAllWidgets(context)
            Log.d(TAG, "✅ Widget updated")

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "❌ DailyPrayerUpdateWorker failed", e)
            // Retry on failure (will try again in 15 min)
            Result.retry()
        }
    }

    companion object {
        private const val TAG = "DailyPrayerUpdateWorker"
    }
}
