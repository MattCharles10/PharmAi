package com.pharmai.data.repository


import com.pharmai.data.local.database.dao.ReminderDao
import com.pharmai.data.model.database.ReminderEntity
import com.pharmai.domain.model.RecurrenceType
import com.pharmai.domain.model.Reminder
import com.pharmai.domain.model.ReminderPriority
import com.pharmai.domain.repository.ReminderRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao,
    private val gson: Gson
) : ReminderRepository {

    override fun getReminders(userId: String): Flow<List<Reminder>> {
        return reminderDao.getReminders(userId).map { entities ->
            entities.map { it.toDomainModel(gson) }
        }
    }

    override fun getEnabledReminders(userId: String): Flow<List<Reminder>> {
        return reminderDao.getEnabledReminders(userId).map { entities ->
            entities.map { it.toDomainModel(gson) }
        }
    }

    override suspend fun createReminder(reminder: Reminder): Result<Long> {
        return try {
            val entity = reminder.toEntity(gson)
            val id = reminderDao.insertReminder(entity)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateReminder(reminder: Reminder): Result<Unit> {
        return try {
            val entity = reminder.toEntity(gson)
            reminderDao.updateReminder(entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteReminder(reminderId: Int): Result<Unit> {
        return try {
            val reminder = reminderDao.getReminderById("", reminderId)
            reminder?.let {
                reminderDao.deleteReminder(it)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleReminder(reminderId: Int, isEnabled: Boolean): Result<Unit> {
        return try {
            reminderDao.toggleReminder(reminderId, isEnabled)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markAsTaken(reminderId: Int): Result<Unit> {
        return try {
            reminderDao.markAsTaken(reminderId, Date())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

fun ReminderEntity.toDomainModel(gson: Gson): Reminder {
    val daysOfWeek = gson.fromJson(daysOfWeek, Array<Int>::class.java).toList()
    return Reminder(
        id = id,
        userId = userId,
        medicineName = medicineName,
        medicineId = medicineId,
        dosage = dosage,
        time = time,
        daysOfWeek = daysOfWeek,
        startDate = startDate,
        endDate = endDate,
        isEnabled = isEnabled,
        isTaken = isTaken,
        takenAt = takenAt,
        mealTiming = mealTiming,
        instructions = instructions,
        notes = notes,
        priority = ReminderPriority.valueOf(priority),
        recurrenceType = RecurrenceType.valueOf(recurrenceType),
        customInterval = customInterval,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Reminder.toEntity(gson: Gson): ReminderEntity {
    val daysOfWeekJson = gson.toJson(daysOfWeek)
    return ReminderEntity(
        id = id,
        userId = userId,
        medicineName = medicineName,
        medicineId = medicineId,
        dosage = dosage,
        time = time,
        daysOfWeek = daysOfWeekJson,
        startDate = startDate,
        endDate = endDate,
        isEnabled = isEnabled,
        isTaken = isTaken,
        takenAt = takenAt,
        mealTiming = mealTiming,
        instructions = instructions,
        notes = notes,
        priority = priority.name,
        recurrenceType = recurrenceType.name,
        customInterval = customInterval,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}