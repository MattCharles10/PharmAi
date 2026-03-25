package com.pharmai.data.model.domain


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Reminder(
    val id: Int,
    val userId: String,
    val medicineName: String,
    val medicineId: String? = null,
    val dosage: String,
    val time: Date,
    val daysOfWeek: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7), // 1=Monday, 7=Sunday
    val startDate: Date = Date(),
    val endDate: Date? = null,
    val isEnabled: Boolean = true,
    val isTaken: Boolean = false,
    val takenAt: Date? = null,
    val mealTiming: String? = null, // "Before meal", "After meal", "With meal"
    val instructions: String? = null,
    val notes: String? = null,
    val priority: ReminderPriority = ReminderPriority.MEDIUM,
    val recurrenceType: RecurrenceType = RecurrenceType.DAILY,
    val customInterval: Int? = null, // For custom recurrence
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable

@Parcelize
enum class ReminderPriority : Parcelable {
    HIGH,
    MEDIUM,
    LOW
}

@Parcelize
enum class RecurrenceType : Parcelable {
    ONCE,
    DAILY,
    WEEKLY,
    MONTHLY,
    CUSTOM
}