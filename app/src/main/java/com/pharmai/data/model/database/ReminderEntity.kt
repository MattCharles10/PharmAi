package com.pharmai.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reminders")
data class ReminderEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val medicineName: String,
    val medicineId: String? = null,
    val dosage: String,
    val time: Date,
    val daysOfWeek: String, // Stored as JSON
    val startDate: Date = Date(),
    val endDate: Date? = null,
    val isEnabled: Boolean = true,
    val isTaken: Boolean = false,
    val takenAt: Date? = null,
    val mealTiming: String? = null,
    val instructions: String? = null,
    val notes: String? = null,
    val priority: String,                    // HIGH, MEDIUM, LOW
    val recurrenceType: String,             // ONCE, DAILY, WEEKLY, MONTHLY, CUSTOM
    val customInterval: Int? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()

)