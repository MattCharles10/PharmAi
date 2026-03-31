package com.pharmai.data.local.database.dao

import androidx.room.*
import com.pharmai.data.model.database.ReminderEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders WHERE userId = :userId ORDER BY time ASC")
    fun getReminders(userId: String): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE userId = :userId AND isEnabled = 1 ORDER BY time ASC")
    fun getEnabledReminders(userId: String): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE userId = :userId AND time BETWEEN :startTime AND :endTime")
    fun getRemindersForTimeRange(userId: String, startTime: Date, endTime: Date): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE userId = :userId AND id = :reminderId")
    suspend fun getReminderById(userId: String, reminderId: Int): ReminderEntity?

    @Insert
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Update
    suspend fun updateReminder(reminder: ReminderEntity)

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)

    @Query("UPDATE reminders SET isTaken = 1, takenAt = :takenAt WHERE id = :reminderId")
    suspend fun markAsTaken(reminderId: Int, takenAt: Date)

    @Query("UPDATE reminders SET isEnabled = :isEnabled WHERE id = :reminderId")
    suspend fun toggleReminder(reminderId: Int, isEnabled: Boolean)

    @Query("DELETE FROM reminders WHERE endDate < :currentDate")
    suspend fun deleteExpiredReminders(currentDate: Date)
}