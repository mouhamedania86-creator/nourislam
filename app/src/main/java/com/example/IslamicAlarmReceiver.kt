package com.example

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.*

class IslamicAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_PERIODIC_DUA -> {
                IslamicNotificationManager.showPeriodicDuaNotification(context)
                schedulePeriodicDua(context)
            }
            ACTION_PRE_ADHAN -> {
                val prayerName = intent.getStringExtra("PRAYER_NAME") ?: "الصلاة"
                val minutesBefore = intent.getIntExtra("MINUTES_BEFORE", 10)
                IslamicNotificationManager.showPreAdhanNotification(context, prayerName, minutesBefore)
            }
            else -> {
                val prayerName = intent.getStringExtra("PRAYER_NAME") ?: "الصلاة"
                IslamicNotificationManager.showAdhanNotification(context, prayerName)
                try {
                    val serviceIntent = Intent(context, AdhanService::class.java).apply {
                        putExtra("PRAYER_NAME", prayerName)
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(serviceIntent)
                    } else {
                        context.startService(serviceIntent)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to start AdhanService", e)
                }
            }
        }
    }

    companion object {
        private const val TAG = "IslamicAlarmReceiver"
        const val ACTION_PERIODIC_DUA = "COM_EXAMPLE_PERIODIC_DUA"
        const val ACTION_PRE_ADHAN = "COM_EXAMPLE_PRE_ADHAN"
        private const val DUA_REQUEST_CODE = 999
        private const val DUA_INTERVAL_MS = 20 * 60 * 1000L
        private const val PRE_ADHAN_MINUTES = 10

        fun schedulePeriodicDua(context: Context) {
            try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, IslamicAlarmReceiver::class.java).apply {
                    action = ACTION_PERIODIC_DUA
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context, DUA_REQUEST_CODE, intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val triggerAt = System.currentTimeMillis() + DUA_INTERVAL_MS
                val info = AlarmManager.AlarmClockInfo(triggerAt, null)
                alarmManager.setAlarmClock(info)
            } catch (e: Exception) {
                try {
                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, IslamicAlarmReceiver::class.java).apply {
                        action = ACTION_PERIODIC_DUA
                    }
                    val pendingIntent = PendingIntent.getBroadcast(
                        context, DUA_REQUEST_CODE, intent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis() + DUA_INTERVAL_MS,
                        pendingIntent
                    )
                } catch (e2: Exception) {
                    Log.e(TAG, "Fallback also failed", e2)
                }
            }
        }

        fun schedulePrayerAlarm(context: Context, prayerName: String, timeStr: String) {
            try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val parts = timeStr.trim().split(":")
                if (parts.size != 2) return
                val hour = parts[0].toIntOrNull() ?: return
                val minute = parts[1].toIntOrNull() ?: return
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                if (calendar.timeInMillis <= System.currentTimeMillis()) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1)
                }
                val intent = Intent(context, IslamicAlarmReceiver::class.java).apply {
                    putExtra("PRAYER_NAME", prayerName)
                    action = "COM_EXAMPLE_ALARM_${prayerName.hashCode()}"
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context, prayerName.hashCode(), intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val info = AlarmManager.AlarmClockInfo(calendar.timeInMillis, null)
                alarmManager.setAlarmClock(info)
            } catch (e: Exception) {
                Log.e(TAG, "schedulePrayerAlarm error", e)
            }
        }

        fun schedulePreAdhanAlarm(context: Context, prayerName: String, timeStr: String) {
            try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val parts = timeStr.trim().split(":")
                if (parts.size != 2) return
                val hour = parts[0].toIntOrNull() ?: return
                val minute = parts[1].toIntOrNull() ?: return
                val totalMinutes = hour * 60 + minute - PRE_ADHAN_MINUTES
                val safeHour = totalMinutes / 60
                val safeMinute = totalMinutes % 60
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, safeHour)
                    set(Calendar.MINUTE, safeMinute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                if (calendar.timeInMillis <= System.currentTimeMillis()) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1)
                }
                val intent = Intent(context, IslamicAlarmReceiver::class.java).apply {
                    action = ACTION_PRE_ADHAN
                    putExtra("PRAYER_NAME", prayerName)
                    putExtra("MINUTES_BEFORE", PRE_ADHAN_MINUTES)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context, ("PRE_$prayerName").hashCode(), intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val info = AlarmManager.AlarmClockInfo(calendar.timeInMillis, null)
                alarmManager.setAlarmClock(info)
            } catch (e: Exception) {
                Log.e(TAG, "schedulePreAdhanAlarm error", e)
            }
        }

        fun scheduleAllDailyAlarms(context: Context, timings: PrayerCacheEntity) {
            schedulePrayerAlarm(context, "الفجر", timings.Fajr)
            schedulePrayerAlarm(context, "الظهر", timings.Dhuhr)
            schedulePrayerAlarm(context, "العصر", timings.Asr)
            schedulePrayerAlarm(context, "المغرب", timings.Maghrib)
            schedulePrayerAlarm(context, "العشاء", timings.Isha)
        }

        fun scheduleAllPreAdhanAlarms(context: Context, timings: PrayerCacheEntity) {
            schedulePreAdhanAlarm(context, "الفجر", timings.Fajr)
            schedulePreAdhanAlarm(context, "الظهر", timings.Dhuhr)
            schedulePreAdhanAlarm(context, "العصر", timings.Asr)
            schedulePreAdhanAlarm(context, "المغرب", timings.Maghrib)
            schedulePreAdhanAlarm(context, "العشاء", timings.Isha)
        }

        fun scheduleWeeklyAlarms(
            context: Context,
            weeklyPrayers: List<WeeklyPrayerEntity>,
            dao: WeeklyPrayerDao
        ) {
            weeklyPrayers.forEach { day ->
                listOf(
                    Pair("الفجر", day.fajr),
                    Pair("الظهر", day.dhuhr),
                    Pair("العصر", day.asr),
                    Pair("المغرب", day.maghrib),
                    Pair("العشاء", day.isha)
                ).forEach { (name, time) ->
                    scheduleWeeklyPrayerAlarm(context, name, time, day.dateKey, day.dayOffset)
                    scheduleWeeklyPreAdhanAlarm(context, name, time, day.dateKey, day.dayOffset)
                }
            }
        }

        private fun scheduleWeeklyPrayerAlarm(
            context: Context, prayerName: String, timeStr: String,
            dateKey: String, dayOffset: Int
        ) {
            try {
                val parts = timeStr.trim().split(":")
                if (parts.size != 2) return
                val hour = parts[0].toIntOrNull() ?: return
                val minute = parts[1].toIntOrNull() ?: return
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, dayOffset)
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                if (calendar.timeInMillis <= System.currentTimeMillis() + 60000) return
                val intent = Intent(context, IslamicAlarmReceiver::class.java).apply {
                    putExtra("PRAYER_NAME", prayerName)
                    action = "COM_EXAMPLE_WEEKLY_${dateKey}_${prayerName.hashCode()}"
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context, ("weekly_$dateKey$prayerName").hashCode(), intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val info = AlarmManager.AlarmClockInfo(calendar.timeInMillis, null)
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setAlarmClock(info)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to schedule weekly prayer alarm", e)
            }
        }

        private fun scheduleWeeklyPreAdhanAlarm(
            context: Context, prayerName: String, timeStr: String,
            dateKey: String, dayOffset: Int
        ) {
            try {
                val parts = timeStr.trim().split(":")
                if (parts.size != 2) return
                val hour = parts[0].toIntOrNull() ?: return
                val minute = parts[1].toIntOrNull() ?: return
                val totalMinutes = hour * 60 + minute - PRE_ADHAN_MINUTES
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, dayOffset)
                calendar.set(Calendar.HOUR_OF_DAY, totalMinutes / 60)
                calendar.set(Calendar.MINUTE, totalMinutes % 60)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                if (calendar.timeInMillis <= System.currentTimeMillis() + 60000) return
                val intent = Intent(context, IslamicAlarmReceiver::class.java).apply {
                    action = ACTION_PRE_ADHAN
                    putExtra("PRAYER_NAME", prayerName)
                    putExtra("MINUTES_BEFORE", PRE_ADHAN_MINUTES)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context, ("weekly_pre_${dateKey}$prayerName").hashCode(), intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val info = AlarmManager.AlarmClockInfo(calendar.timeInMillis, null)
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setAlarmClock(info)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to schedule weekly pre-adhan", e)
            }
        }
    }
}

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        if (action != Intent.ACTION_BOOT_COMPLETED && action != Intent.ACTION_MY_PACKAGE_REPLACED) return

        IslamicAlarmReceiver.schedulePeriodicDua(context)

        try {
            val db = AppDatabase.getDatabase(context)
            Thread {
                try {
                    val cached = db.prayerCacheDao().getLatestSync()
                    if (cached != null) {
                        IslamicAlarmReceiver.scheduleAllDailyAlarms(context, cached)
                        IslamicAlarmReceiver.scheduleAllPreAdhanAlarms(context, cached)
                    }
                } catch (e: Exception) {
                    Log.e("BootReceiver", "Error reading cached prayers", e)
                }
            }.start()
        } catch (e: Exception) {
            Log.e("BootReceiver", "DB error on boot", e)
        }

        IslamicNotificationManager.showPeriodicDuaNotification(context)
    }
}

class AdhanService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onCreate() { super.onCreate(); createNotificationChannel() }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val prayerName = intent?.getStringExtra("PRAYER_NAME") ?: "الصلاة"
        if (intent?.action == ACTION_STOP_ADHAN) { stopSelf(); return START_NOT_STICKY }

        val stopIntent = Intent(this, AdhanService::class.java).apply { action = ACTION_STOP_ADHAN }
        val stopPending = PendingIntent.getService(this, 99, stopIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_silent_mode_off)
            .setContentTitle("🕌 أذان صلاة $prayerName")
            .setContentText("حي على الصلاة — حي على الفلاح")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, "إيقاف الأذان", stopPending)
            .setOngoing(true).build()
        startForeground(2027, notification)

        try {
            val afd = assets.openFd("adhan.mp3")
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                isLooping = false
                setOnCompletionListener { stopSelf() }
                prepare(); start()
            }
        } catch (e: Exception) {
            try {
                val uri = android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_ALARM)
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(this@AdhanService, uri)
                    setAudioAttributes(AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
                    isLooping = false
                    setOnCompletionListener { stopSelf() }
                    prepare(); start()
                }
            } catch (ex: Exception) { stopSelf() }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        try { mediaPlayer?.stop(); mediaPlayer?.release(); mediaPlayer = null } catch (e: Exception) { }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "خدمة الأذان", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val TAG = "AdhanService"
        private const val CHANNEL_ID = "noor_al_islam_service_adhan"
        const val ACTION_STOP_ADHAN = "STOP_ADHAN"
    }
}

class RuqyahService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onCreate() { super.onCreate(); createNotificationChannel() }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_STOP_RUQYAH -> { stopSelf(); return START_NOT_STICKY }
            ACTION_PAUSE_RUQYAH -> { mediaPlayer?.pause(); updateNotification(false); return START_NOT_STICKY }
            ACTION_RESUME_RUQYAH -> { mediaPlayer?.start(); updateNotification(true); return START_NOT_STICKY }
        }
        startForeground(3001, buildNotification(true))
        try {
            val afd = assets.openFd("ruqyah.mp3")
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close(); isLooping = true; prepare(); start()
            }
        } catch (e: Exception) { stopSelf() }
        return START_STICKY
    }

    private fun buildNotification(isPlaying: Boolean): android.app.Notification {
        val stopIntent = PendingIntent.getService(this, 0,
            Intent(this, RuqyahService::class.java).apply { action = ACTION_STOP_RUQYAH },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val toggleIntent = PendingIntent.getService(this, 1,
            Intent(this, RuqyahService::class.java).apply {
                action = if (isPlaying) ACTION_PAUSE_RUQYAH else ACTION_RESUME_RUQYAH },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle("🕯️ الرقية الشرعية")
            .setContentText(if (isPlaying) "جارٍ التشغيل — offline ✅" else "متوقف مؤقتاً")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .addAction(if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play,
                if (isPlaying) "إيقاف مؤقت" else "استئناف", toggleIntent)
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, "إيقاف", stopIntent)
            .setOngoing(true).build()
    }

    private fun updateNotification(isPlaying: Boolean) {
        getSystemService(NotificationManager::class.java)?.notify(3001, buildNotification(isPlaying))
    }

    override fun onDestroy() {
        super.onDestroy()
        try { mediaPlayer?.stop(); mediaPlayer?.release(); mediaPlayer = null } catch (e: Exception) { }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "الرقية الشرعية", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val TAG = "RuqyahService"
        private const val CHANNEL_ID = "noor_al_islam_ruqyah"
        const val ACTION_STOP_RUQYAH = "STOP_RUQYAH"
        const val ACTION_PAUSE_RUQYAH = "PAUSE_RUQYAH"
        const val ACTION_RESUME_RUQYAH = "RESUME_RUQYAH"
    }
}
