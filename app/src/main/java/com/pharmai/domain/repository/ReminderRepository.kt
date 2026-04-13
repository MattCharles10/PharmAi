package com.pharmai.domain.repository


import com.pharmai.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getReminders(userId: String): Flow<List<Reminder>>
    fun getEnabledReminders(userId: String): Flow<List<Reminder>>
    suspend fun createReminder(reminder: Reminder): Result<Long>
    suspend fun updateReminder(reminder: Reminder): Result<Unit>
    suspend fun deleteReminder(reminderId: Int): Result<Unit>
    suspend fun toggleReminder(reminderId: Int, isEnabled: Boolean): Result<Unit>
    suspend fun markAsTaken(reminderId: Int): Result<Unit>
}