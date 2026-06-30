package com.example

import android.app.Application
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "═══════════════════════════════════════")
        Log.d(TAG, "  MyApplication.onCreate() — نور الإسلام")
        Log.d(TAG, "═══════════════════════════════════════")

        // 1. Initialize notification channels
        IslamicNotificationManager.initNotificationChannels(this)

        // 2. Schedule daily prayer time refresh (every 24h via WorkManager)
        scheduleDailyPrayerUpdate()

        // 3. Ensure alarms are scheduled
        ensureAlarmsScheduled()

        // 4. Schedule live countdown worker (every 1 minute)
        CountdownWorker.schedule(this)

        Log.d(TAG, "✅ MyApplication initialized successfully")
    }

    private fun scheduleDailyPrayerUpdate() {
        try {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(false)
                .build()

            // Update prayer times every 24 hours
            val dailyUpdate = PeriodicWorkRequestBuilder<DailyPrayerUpdateWorker>(
                24, TimeUnit.HOURS,
                // Flex interval: 6 hours of variance
                6, TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .addTag("daily_prayer_update")
                .build()

            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "DailyPrayerUpdate",
                ExistingPeriodicWorkPolicy.KEEP,
                dailyUpdate
            )
            Log.d(TAG, "✅ Daily prayer update scheduled (every 24h)")

            // Also run once on startup to refresh prayer times
            val oneTimeUpdate = PeriodicWorkRequestBuilder<DailyPrayerUpdateWorker>(
                15, TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .addTag("quick_prayer_update")
                .build()

            // Schedule a one-time work to refresh after 1 minute
            val immediateUpdate = androidx.work.OneTimeWorkRequestBuilder<DailyPrayerUpdateWorker>()
                .setInitialDelay(1, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance(this).enqueue(immediateUpdate)

        } catch (e: Exception) {
            Log.e(TAG, "Failed to schedule daily prayer update", e)
        }
    }

    private fun ensureAlarmsScheduled() {
        try {
            // Schedule periodic dua notifications
            IslamicAlarmReceiver.schedulePeriodicDua(this)

            // Try to schedule prayer alarms from cached data
            Thread {
                try {
                    val db = AppDatabase.getDatabase(this)
                    val cached = db.prayerCacheDao().getLatestSync()
                    if (cached != null) {
                        IslamicAlarmReceiver.scheduleAllDailyAlarms(this, cached)
                        IslamicAlarmReceiver.scheduleAllPreAdhanAlarms(this, cached)
                        Log.d(TAG, "✅ Prayer alarms scheduled from cache")
                    } else {
                        Log.w(TAG, "⚠️ No cached prayer times — alarms will be set when data arrives")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error scheduling alarms from cache", e)
                }
            }.start()
        } catch (e: Exception) {
            Log.e(TAG, "ensureAlarmsScheduled error", e)
        }
    }

    companion object {
        private const val TAG = "MyApplication"
    }
}
