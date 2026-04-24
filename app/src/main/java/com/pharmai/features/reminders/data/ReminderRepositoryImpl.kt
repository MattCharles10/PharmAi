package com.pharmai.features.reminders.data

import com.pharmai.features.reminders.data.local.ReminderDao
import com.pharmai.features.reminders.data.local.ReminderEntity
import com.pharmai.features.reminders.domain.models.Reminder
import com.pharmai.features.reminders.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val dao: ReminderDao
) : ReminderRepository {

    override fun observeActive(): Flow<List<Reminder>> = dao.observeActive().map { it.map { e -> e.toDomain() } }
    override suspend fun getAll(): List<Reminder> = dao.getAll().map { it.toDomain() }
    override suspend fun getById(id: Long): Reminder? = dao.getById(id)?.toDomain()
    override suspend fun create(reminder: Reminder): Long = dao.insert(ReminderEntity.fromDomain(reminder))
    override suspend fun update(reminder: Reminder) = dao.update(ReminderEntity.fromDomain(reminder))
    override suspend fun delete(id: Long) = dao.delete(id)
    override suspend fun toggle(id: Long, isActive: Boolean) = dao.toggle(id, isActive)
}