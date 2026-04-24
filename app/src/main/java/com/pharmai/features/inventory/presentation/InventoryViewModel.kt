package com.pharmai.features.inventory.presentation

import androidx.lifecycle.viewModelScope
import com.pharmai.core.base.BaseViewModel
import com.pharmai.core.base.UiEffect
import com.pharmai.core.base.UiEvent
import com.pharmai.core.base.UiState
import com.pharmai.features.inventory.domain.models.Medicine
import com.pharmai.features.inventory.domain.usecase.DeleteMedicineUseCase
import com.pharmai.features.inventory.domain.usecase.GetInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val getInventoryUseCase: GetInventoryUseCase,
    private val deleteMedicineUseCase: DeleteMedicineUseCase
) : BaseViewModel<InventoryState, InventoryEvent, InventoryEffect>(InventoryState()) {

    // Use MutableStateFlow instead of stateIn
    private val _medicines = MutableStateFlow<List<Medicine>>(emptyList())
    val medicines = _medicines.asStateFlow()

    init {
        loadMedicines()
    }

    private fun loadMedicines() {
        viewModelScope.launch {
            getInventoryUseCase()
                .catch { e -> updateState { it.copy(error = e.message) } }
                .collect { medicineList ->
                    _medicines.value = medicineList
                }
        }
    }

    override fun handleEvent(event: InventoryEvent) {
        when (event) {
            is InventoryEvent.DeleteMedicine -> deleteMedicine(event.id)
            is InventoryEvent.Refresh -> loadMedicines()
        }
    }

    private fun deleteMedicine(id: Long) {
        viewModelScope.launch {
            try {
                deleteMedicineUseCase(id)
                loadMedicines() // Refresh after delete
            } catch (e: Exception) {
                updateState { it.copy(error = e.message) }
            }
        }
    }
}

data class InventoryState(
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed class InventoryEvent : UiEvent {
    data class DeleteMedicine(val id: Long) : InventoryEvent()
    data object Refresh : InventoryEvent()
}

sealed class InventoryEffect : UiEffect