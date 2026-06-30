package com.example

import androidx.room.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * نظام تخزين 7 أيام من مواقيت الصلاة
 * يخزن في Room database بجدول منفصل
 * يحدّث تلقائياً عبر WorkManager
 */

/**
 * كيان مواقيت 7 أيام
 * كل يوم له entry منفصل في الـ database
 */
@Entity(tableName = "prayer_cache_weekly", primaryKeys = ["dateKey", "city"])
data class WeeklyPrayerEntity(
    val dateKey: String,           // YYYY-MM-DD format
    val city: String,
    val dayOffset: Int,            // 0 = today, 1 = tomorrow, ..., 6 = week ahead
    val dayNameAr: String,         // السبت، الأحد...
    val hijriDate: String,         // التاريخ الهجري
    val fajr: String,              // HH:MM
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    fun isToday(): Boolean = dayOffset == 0
    fun isTomorrow(): Boolean = dayOffset == 1
}

@Dao
interface WeeklyPrayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prayers: List<WeeklyPrayerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(prayer: WeeklyPrayerEntity)

    @Query("SELECT * FROM prayer_cache_weekly WHERE city = :city AND dateKey = :date LIMIT 1")
    suspend fun getPrayerForDate(city: String, date: String): WeeklyPrayerEntity?

    @Query("SELECT * FROM prayer_cache_weekly WHERE city = :city ORDER BY dateKey ASC LIMIT 7")
    suspend fun getWeeklyPrayers(city: String): List<WeeklyPrayerEntity>

    @Query("SELECT * FROM prayer_cache_weekly WHERE dateKey = :dateKey LIMIT 1")
    fun getPrayerForDateSync(dateKey: String): WeeklyPrayerEntity?

    @Query("SELECT * FROM prayer_cache_weekly WHERE dayOffset = 0 AND city = :city LIMIT 1")
    fun getTodaySync(city: String): WeeklyPrayerEntity?

    @Query("SELECT * FROM prayer_cache_weekly WHERE city = :city ORDER BY dateKey ASC LIMIT 7")
    fun getWeeklyPrayersSync(city: String): List<WeeklyPrayerEntity>

    @Query("DELETE FROM prayer_cache_weekly WHERE dateKey < :dateKey")
    suspend fun deleteOldEntries(dateKey: String)

    @Query("DELETE FROM prayer_cache_weekly WHERE city = :city")
    suspend fun clearCity(city: String)

    @Query("DELETE FROM prayer_cache_weekly")
    suspend fun clearAll()
}

/**
 * Helper لتحويل WeeklyPrayerEntity لـ PrayerCacheEntity (للتوافق)
 */
fun WeeklyPrayerEntity.toPrayerCacheEntity(): PrayerCacheEntity {
    return PrayerCacheEntity(
        dateKey = this.dateKey,
        city = this.city,
        Fajr = this.fajr,
        Sunrise = this.sunrise,
        Dhuhr = this.dhuhr,
        Asr = this.asr,
        Maghrib = this.maghrib,
        Isha = this.isha,
        hijriDate = this.hijriDate,
        gregorianDate = this.dateKey,
        lastUpdated = this.lastUpdated
    )
}

/**
 * Helper للحصول على قائمة 7 أيام من اليوم
 */
fun generateDateRange(daysAhead: Int = 7): List<String> {
    val dates = mutableListOf<String>()
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()
    for (i in 0 until daysAhead) {
        dates.add(sdf.format(calendar.time))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    return dates
}

fun getDayNameAr(dateKey: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dayFormat = SimpleDateFormat("EEEE", Locale("ar"))
        val date = sdf.parse(dateKey) ?: return ""
        dayFormat.format(date)
    } catch (e: Exception) {
        ""
    }
}

/**
 * تحديث الـ AppDatabase ليشمل الجدول الجديد
 * (نضيف الـ entity و DAO)
 */
abstract class AppDatabaseExtended : RoomDatabase() {
    abstract fun weeklyPrayerDao(): WeeklyPrayerDao
}
