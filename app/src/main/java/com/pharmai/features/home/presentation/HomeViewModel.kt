package com.pharmai.features.home.presentation

import androidx.lifecycle.viewModelScope
import com.pharmai.core.base.BaseViewModel
import com.pharmai.core.base.UiEffect
import com.pharmai.core.base.UiEvent
import com.pharmai.core.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeState, HomeEvent, HomeEffect>(HomeState()) {

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> loadHomeData()
        }
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            updateState {
                it.copy(
                    isLoading = false,
                    expiringMedicines = listOf(
                        ExpiringMedicine("Paracetamol", 5),
                        ExpiringMedicine("Ibuprofen", 12),
                        ExpiringMedicine("Amoxicillin", 3)
                    ),
                    todayReminders = listOf(
                        ReminderItem("Vitamin D", "1 tablet", "09:00 AM"),
                        ReminderItem("Metformin", "500mg", "08:00 PM"),
                        ReminderItem("Aspirin", "75mg", "10:00 AM")
                    )
                )
            }
        }
    }

    init { loadHomeData() }
}

data class HomeState(
    val isLoading: Boolean = false,
    val expiringMedicines: List<ExpiringMedicine> = emptyList(),
    val todayReminders: List<ReminderItem> = emptyList()
) : UiState

data class ExpiringMedicine(val name: String, val daysLeft: Int)
data class ReminderItem(val medicineName: String, val dosage: String, val time: String)

sealed class HomeEvent : UiEvent {
    data object Refresh : HomeEvent()
}

sealed class HomeEffect : UiEffect