package com.pharmai.features.reminders.domain.usecase

import com.pharmai.features.reminders.domain.models.Reminder
import com.pharmai.features.reminders.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor(private val repository: ReminderRepository) {
    operator fun invoke(): Flow<List<Reminder>> = repository.observeActive()
}

class CreateReminderUseCase @Inject constructor(private val repository: ReminderRepository) {
    suspend operator fun invoke(reminder: Reminder): Long = repository.create(reminder)
}

class ToggleReminderUseCase @Inject constructor(private val repository: ReminderRepository) {
    suspend operator fun invoke(id: Long, isActive: Boolean) = repository.toggle(id, isActive)
}

class DeleteReminderUseCase @Inject constructor(private val repository: ReminderRepository) {
    suspend operator fun invoke(id: Long) = repository.delete(id)
}