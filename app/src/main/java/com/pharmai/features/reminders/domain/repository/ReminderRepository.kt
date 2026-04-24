package com.pharmai.features.reminders.domain.repository

import com.pharmai.features.reminders.domain.models.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun observeActive(): Flow<List<Reminder>>
    suspend fun getAll(): List<Reminder>
    suspend fun getById(id: Long): Reminder?
    suspend fun create(reminder: Reminder): Long
    suspend fun update(reminder: Reminder)
    suspend fun delete(id: Long)
    suspend fun toggle(id: Long, isActive: Boolean)
}