package com.pharmai.core.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val displayFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    fun formatDate(date: Date) = displayFormat.format(date)
    fun formatTime(date: Date) = timeFormat.format(date)
    fun parseDate(str: String) = try { dateFormat.parse(str) } catch (e: Exception) { null }
    fun isExpired(date: Date) = date.before(Date())

    fun daysUntil(date: Date): Int {
        val diff = date.time - Date().time
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
    }

    fun isExpiringSoon(date: Date, threshold: Int = 30) = daysUntil(date) in 0..threshold

    fun addDays(date: Date, days: Int): Date {
        val cal = Calendar.getInstance().apply { time = date; add(Calendar.DAY_OF_YEAR, days) }
        return cal.time
    }

    fun getTodayStart(): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }
}