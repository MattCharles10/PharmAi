package com.pharmai.features.reminders.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pharmai.features.reminders.domain.models.Reminder
import java.util.*

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val medicineId: Long?,
    val medicineName: String,
    val dosage: String,
    val timeHour: Int,
    val timeMinute: Int,
    val isActive: Boolean,
    val daysOfWeek: String?,
    val createdAt: Date
) {
    fun toDomain() = Reminder(
        id = id, medicineId = medicineId, medicineName = medicineName, dosage = dosage,
        timeHour = timeHour, timeMinute = timeMinute, isActive = isActive,
        daysOfWeek = daysOfWeek?.split(",")?.map { it.toInt() } ?: emptyList(), createdAt = createdAt
    )

    companion object {
        fun fromDomain(reminder: Reminder) = ReminderEntity(
            id = reminder.id, medicineId = reminder.medicineId, medicineName = reminder.medicineName,
            dosage = reminder.dosage, timeHour = reminder.timeHour, timeMinute = reminder.timeMinute,
            isActive = reminder.isActive, daysOfWeek = reminder.daysOfWeek.joinToString(","), createdAt = reminder.createdAt
        )
    }
}