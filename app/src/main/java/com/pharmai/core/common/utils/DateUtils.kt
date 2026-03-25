
package com.pharmai.core.common.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val DATE_TIME_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val DISPLAY_DATE_FORMAT = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val DISPLAY_TIME_FORMAT = SimpleDateFormat("hh:mm a", Locale.getDefault())
    private val DISPLAY_DATE_TIME_FORMAT = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())

    fun formatDate(date: Date): String = DISPLAY_DATE_FORMAT.format(date)
    fun formatTime(date: Date): String = DISPLAY_TIME_FORMAT.format(date)
    fun formatDateTime(date: Date): String = DISPLAY_DATE_TIME_FORMAT.format(date)

    fun formatDateForApi(date: Date): String = DATE_FORMAT.format(date)
    fun formatDateTimeForApi(date: Date): String = DATE_TIME_FORMAT.format(date)

    fun parseDate(dateString: String): Date? = try {
        DATE_FORMAT.parse(dateString)
    } catch (e: Exception) {
        null
    }

    fun parseDateTime(dateTimeString: String): Date? = try {
        DATE_TIME_FORMAT.parse(dateTimeString)
    } catch (e: Exception) {
        null
    }

    fun isExpired(expiryDate: Date): Boolean = expiryDate.before(Date())

    fun daysUntilExpiry(expiryDate: Date): Int {
        val now = Date()
        val diffInMillis = expiryDate.time - now.time
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS).toInt()
    }

    fun isExpiringSoon(expiryDate: Date, daysThreshold: Int = 30): Boolean {
        val daysLeft = daysUntilExpiry(expiryDate)
        return daysLeft in 0..daysThreshold
    }

    fun getCurrentDate(): String = DATE_FORMAT.format(Date())
    fun getCurrentDateTime(): String = DATE_TIME_FORMAT.format(Date())

    fun getTodayStart(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    fun getTodayEnd(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    fun getTimeRemaining(targetDate: Date): String {
        val now = Date()
        val diffInMillis = targetDate.time - now.time

        return when {
            diffInMillis < 0 -> "Expired"
            diffInMillis < TimeUnit.HOURS.toMillis(1) -> {
                val minutes = TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MILLISECONDS)
                "In $minutes minutes"
            }
            diffInMillis < TimeUnit.DAYS.toMillis(1) -> {
                val hours = TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS)
                "In $hours hours"
            }
            else -> {
                val days = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS)
                "In $days days"
            }
        }
    }

    fun addDays(date: Date, days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return calendar.time
    }

    fun addMonths(date: Date, months: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MONTH, months)
        return calendar.time
    }
}