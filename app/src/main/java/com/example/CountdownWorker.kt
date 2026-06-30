package com.example

import android.content.Context
import android.util.Log
import androidx.work.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * CountdownWorker - يحدث إشعار العداد التنازلي كل دقيقة
 * يحسب الوقت الباقي للصلاة القادمة ويحدّث الإشعار
 */
class CountdownWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "⏰ Countdown tick — calculating next prayer time")

            val db = AppDatabase.getDatabase(applicationContext)
            val cached = db.prayerCacheDao().getLatestSync()
            if (cached == null) {
                Log.w(TAG, "⚠️ No cached prayer times — skipping countdown")
                return@withContext Result.success()
            }

            val now = System.currentTimeMillis()
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val nowTime = sdf.format(java.util.Date(now))

            val prayers = listOf(
    Pair("الفجر", cached.Fajr),
    Pair("الظهر", cached.Dhuhr),
    Pair("العصر", cached.Asr),
    Pair("المغرب", cached.Maghrib),
    Pair("العشاء", cached.Isha)
)

            // إيجاد الصلاة القادمة (الأقرب من الآن)
            var nextPrayerName: String? = null
            var nextPrayerMillis: Long = 0
            val cal = Calendar.getInstance()

            for ((name, timeStr) in prayers) {
                val parts = timeStr.trim().split(":")
                if (parts.size != 2) continue
                val hour = parts[0].toIntOrNull() ?: continue
                val minute = parts[1].toIntOrNull() ?: continue

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)

                if (cal.timeInMillis > now + 60_000) {
                    if (nextPrayerMillis == 0L || cal.timeInMillis < nextPrayerMillis) {
                        nextPrayerMillis = cal.timeInMillis
                        nextPrayerName = name
                    }
                }
            }

            // إذا لا توجد صلاة اليوم، نأخذ فجر الغد
            if (nextPrayerName == null) {
                val parts = cached.Fajr.trim().split(":")
                if (parts.size == 2) {
                    cal.add(Calendar.DAY_OF_YEAR, 1)
                    cal.set(Calendar.HOUR_OF_DAY, parts[0].toIntOrNull() ?: 5)
                    cal.set(Calendar.MINUTE, parts[1].toIntOrNull() ?: 0)
                    cal.set(Calendar.SECOND, 0)
                    cal.set(Calendar.MILLISECOND, 0)
                    nextPrayerMillis = cal.timeInMillis
                    nextPrayerName = "الفجر"
                }
            }

            if (nextPrayerName != null) {
                val remainingMillis = nextPrayerMillis - now
                val hoursLeft = (remainingMillis / 3_600_000).toInt()
                val minutesLeft = ((remainingMillis % 3_600_000) / 60_000).toInt()

                if (remainingMillis > 0) {
                    IslamicNotificationManager.showCountdownNotification(
                        context = applicationContext,
                        prayerName = nextPrayerName,
                        hoursLeft = hoursLeft,
                        minutesLeft = minutesLeft
                    )
                    Log.d(TAG, "✅ Countdown: $nextPrayerName in ${hoursLeft}h ${minutesLeft}m")
                } else {
                    // حان وقت الصلاة، الغ العداد
                    IslamicNotificationManager.cancelCountdown(applicationContext)
                    Log.d(TAG, "⏰ Prayer time arrived — countdown cancelled")
                }
            }

            return@withContext Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "CountdownWorker error", e)
            return@withContext Result.retry()
        }
    }

    companion object {
        private const val TAG = "CountdownWorker"
        private const val UNIQUE_WORK_NAME = "noor_al_islam_countdown_worker"

        /**
         * جدولة العداد التنازلي - يعمل كل دقيقة بشكل دائم
         */
        fun schedule(context: Context) {
            try {
                val request = PeriodicWorkRequestBuilder<CountdownWorker>(
                    1, TimeUnit.MINUTES
                )
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                            .build()
                    )
                    .build()

                WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                    UNIQUE_WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    request
                )
                Log.d(TAG, "✅ Countdown worker scheduled (every 1 min)")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to schedule countdown worker", e)
            }
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(UNIQUE_WORK_NAME)
        }
    }
}
