package com.example

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.R
import java.util.Calendar

object IslamicNotificationManager {

    private const val GENERAL_CHANNEL_ID = "noor_al_islam_reminders"
    private const val ADHAN_CHANNEL_ID = "noor_al_islam_adhan"
    private const val PRE_ADHAN_CHANNEL_ID = "noor_al_islam_pre_adhan"
    private const val HADITH_CHANNEL_ID = "noor_al_islam_hadith"
    private const val JUMUAH_CHANNEL_ID = "noor_al_islam_jumuah"
    private const val RAMADAN_CHANNEL_ID = "noor_al_islam_ramadan"
    private const val COUNTDOWN_CHANNEL_ID = "noor_al_islam_countdown"

    private const val BASE_GENERAL = 2000
    private const val BASE_ADHAN = 3000
    private const val BASE_PRE_ADHAN = 4000
    private const val BASE_HADITH = 5000
    private const val JUMUAH_ID = 6001
    private const val RAMADAN_ID = 7001
    private const val COUNTDOWN_ID = 8001

    // استخدام أيقونة التطبيق بدل أيقونة النظام
    private fun appIcon() = R.mipmap.ic_launcher

    fun initNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val generalChannel = NotificationChannel(
                GENERAL_CHANNEL_ID,
                "نور الإسلام - أذكار وتذكيرات",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "إشعارات الأذكار والأدعية التذكيرية"
            }
            manager.createNotificationChannel(generalChannel)

            // Adhan: IMPORTANCE_HIGH + bypassDnd باش يشتغل fullScreenIntent حتى على الشاشات المقفلة
            val adhanChannel = NotificationChannel(
                ADHAN_CHANNEL_ID,
                "نور الإسلام - إشعارات الأذان",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "تنبيه عند دخول وقت الصلاة مع تشغيل صوت الأذان"
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 200, 500)
                setBypassDnd(true)
                setShowBadge(true)
            }
            manager.createNotificationChannel(adhanChannel)

            val preAdhanChannel = NotificationChannel(
                PRE_ADHAN_CHANNEL_ID,
                "نور الإسلام - تنبيه قبل الأذان",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "تنبيه قبل 10 دقائق من وقت الأذان"
            }
            manager.createNotificationChannel(preAdhanChannel)

            val hadithChannel = NotificationChannel(
                HADITH_CHANNEL_ID,
                "نور الإسلام - حديث اليوم",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "حديث شريف يومي"
            }
            manager.createNotificationChannel(hadithChannel)

            val jumuahChannel = NotificationChannel(
                JUMUAH_CHANNEL_ID,
                "نور الإسلام - الجمعة المباركة",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "تذكير بساعة الجمعة المباركة"
            }
            manager.createNotificationChannel(jumuahChannel)

            val ramadanChannel = NotificationChannel(
                RAMADAN_CHANNEL_ID,
                "نور الإسلام - رمضان",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "تذكير بمواعيد السحور والإفطار في رمضان"
            }
            manager.createNotificationChannel(ramadanChannel)

            val countdownChannel = NotificationChannel(
                COUNTDOWN_CHANNEL_ID,
                "نور الإسلام - العداد التنازلي",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "عداد تنازلي للصلاة القادمة يحدث كل دقيقة"
                setShowBadge(false)
            }
            manager.createNotificationChannel(countdownChannel)
        }
    }

    fun showPeriodicDuaNotification(context: Context) {
        try {
            val randomDua = IslamicStaticData.hourlyDuas.random()
            val randomHadith = IslamicStaticData.randomHadiths.random()

            val title = "🌙 تذكير إسلامي"
            val body = "$randomDua\n\n📖 حديث: $randomHadith"

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, GENERAL_CHANNEL_ID)
                .setSmallIcon(appIcon())
                .setContentTitle(title)
                .setContentText(randomDua)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val uniqueId = BASE_GENERAL + (System.currentTimeMillis() / 1000).toInt() % 1000
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(uniqueId, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * إشعار الأذان - مع FullScreenIntent باش يظهر على الشاشة المقفلة
     */
    fun showAdhanNotification(context: Context, prayerName: String) {
        try {
            val title = "🕌 حان الآن أذان صلاة $prayerName"
            val body = "حي على الصلاة — حي على الفلاح. أقم صلاتك قبل مماتك."

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 1, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            // FullScreenIntent = يفتح الشاشة على قفل الشاشة
            val fullScreenPending = PendingIntent.getActivity(
                context, 11,
                Intent(context, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, ADHAN_CHANNEL_ID)
                .setSmallIcon(appIcon())
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setFullScreenIntent(fullScreenPending, true) // ← FullScreenIntent
                .setAutoCancel(true)
                .setOngoing(false)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // يبان على قفل الشاشة
                .setVibrate(longArrayOf(0, 500, 200, 500, 200, 500))

            val prayerIndex = when (prayerName) {
                "الفجر" -> 1
                "الظهر" -> 2
                "العصر" -> 3
                "المغرب" -> 4
                "العشاء" -> 5
                else -> 0
            }
            val uniqueId = BASE_ADHAN + prayerIndex
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(uniqueId, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showPreAdhanNotification(context: Context, prayerName: String, minutesLeft: Int) {
        try {
            val title = "⏰ باقي $minutesLeft دقيقة على أذان $prayerName"
            val body = "استعد للصلاة. الوضوء والسلام عليك يا رسول الله."

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 2, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, PRE_ADHAN_CHANNEL_ID)
                .setSmallIcon(appIcon())
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val prayerIndex = when (prayerName) {
                "الفجر" -> 1
                "الظهر" -> 2
                "العصر" -> 3
                "المغرب" -> 4
                "العشاء" -> 5
                else -> 0
            }
            val uniqueId = BASE_PRE_ADHAN + prayerIndex
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(uniqueId, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showDailyHadithNotification(context: Context) {
        try {
            val hadith = IslamicStaticData.randomHadiths.random()
            val title = "📖 حديث اليوم"
            val body = hadith

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 3, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, HADITH_CHANNEL_ID)
                .setSmallIcon(appIcon())
                .setContentTitle(title)
                .setContentText(hadith)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(BASE_HADITH, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showJumuahNotification(context: Context) {
        try {
            val title = "🕌 الجمعة المباركة"
            val body = "ساعة الإجابة على الدعاء: آخر ساعة من يوم الجمعة. لا تنسَ الدعاء والصلاة على النبي ﷺ."

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 4, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, JUMUAH_CHANNEL_ID)
                .setSmallIcon(appIcon())
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(JUMUAH_ID, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showRamadanNotification(context: Context) {
        try {
            val title = "🌙 رمضان كريم"
            val body = "شهر الرحمة والمغفرة. لا تنسَ السحور، الإفطار، صلاة التراويح، وقيام الليل."

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 5, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, RAMADAN_CHANNEL_ID)
                .setSmallIcon(appIcon())
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(RAMADAN_ID, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * عداد تنازلي للصلاة القادمة
     * يحدث كل دقيقة - يرسل إلى COUNTDOWN_ID
     * @param prayerName اسم الصلاة القادمة
     * @param hoursLeft الساعات المتبقية
     * @param minutesLeft الدقائق المتبقية
     */
    fun showCountdownNotification(context: Context, prayerName: String, hoursLeft: Int, minutesLeft: Int) {
        try {
            val timeStr = when {
                hoursLeft > 0 -> "$hoursLeft ساعة و$minutesLeft دقيقة"
                minutesLeft > 0 -> "$minutesLeft دقيقة"
                else -> "الآن!"
            }
            val title = "🕌 باقي $timeStr على صلاة $prayerName"
            val body = "استعد للصلاة. الوضوء وتوجه للمسجد."

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 6, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, COUNTDOWN_CHANNEL_ID)
                .setSmallIcon(appIcon())
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setOnlyAlertOnce(true) // لا يصدر صوت كل دقيقة
                .setShowWhen(false)
                .setSilent(true)

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(COUNTDOWN_ID, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * إلغاء العداد التنازلي
     */
    fun cancelCountdown(context: Context) {
        try {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.cancel(COUNTDOWN_ID)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
