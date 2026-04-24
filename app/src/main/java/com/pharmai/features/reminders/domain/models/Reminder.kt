package com.pharmai.features.reminders.domain.models

import java.util.*

data class Reminder(
    val id: Long = 0,
    val medicineId: Long? = null,
    val medicineName: String,
    val dosage: String,
    val timeHour: Int,
    val timeMinute: Int,
    val isActive: Boolean = true,
    val daysOfWeek: List<Int> = emptyList(),
    val createdAt: Date = Date()
) {
    fun toTimeString() = String.format("%02d:%02d %s",
        if (timeHour > 12) timeHour - 12 else if (timeHour == 0) 12 else timeHour,
        timeMinute,
        if (timeHour >= 12) "PM" else "AM"
    )
}