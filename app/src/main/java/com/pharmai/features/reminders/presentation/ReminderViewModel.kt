package com.pharmai.features.reminders.presentation

import androidx.lifecycle.viewModelScope
import com.pharmai.core.base.BaseViewModel
import com.pharmai.core.base.UiEffect
import com.pharmai.core.base.UiEvent
import com.pharmai.core.base.UiState
import com.pharmai.features.reminders.domain.models.Reminder
import com.pharmai.features.reminders.domain.usecase.DeleteReminderUseCase
import com.pharmai.features.reminders.domain.usecase.GetRemindersUseCase
import com.pharmai.features.reminders.domain.usecase.ToggleReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val getRemindersUseCase: GetRemindersUseCase,
    private val toggleReminderUseCase: ToggleReminderUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase
) : BaseViewModel<ReminderState, ReminderEvent, ReminderEffect>(ReminderState()) {

    // Use MutableStateFlow instead of stateIn
    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders = _reminders.asStateFlow()

    init {
        loadReminders()
    }

    private fun loadReminders() {
        viewModelScope.launch {
            getRemindersUseCase()
                .catch { e -> updateState { it.copy(error = e.message) } }
                .collect { reminderList ->
                    _reminders.value = reminderList
                }
        }
    }

    override fun handleEvent(event: ReminderEvent) {
        when (event) {
            is ReminderEvent.ToggleReminder -> toggleReminder(event.id, event.isActive)
            is ReminderEvent.DeleteReminder -> deleteReminder(event.id)
        }
    }

    private fun toggleReminder(id: Long, isActive: Boolean) {
        viewModelScope.launch {
            toggleReminderUseCase(id, isActive)
            loadReminders() // Refresh after toggle
        }
    }

    private fun deleteReminder(id: Long) {
        viewModelScope.launch {
            deleteReminderUseCase(id)
            loadReminders() // Refresh after delete
        }
    }
}

data class ReminderState(
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed class ReminderEvent : UiEvent {
    data class ToggleReminder(val id: Long, val isActive: Boolean) : ReminderEvent()
    data class DeleteReminder(val id: Long) : ReminderEvent()
}

sealed class ReminderEffect : UiEffect