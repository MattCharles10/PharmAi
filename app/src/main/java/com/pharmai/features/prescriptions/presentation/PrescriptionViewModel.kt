package com.pharmai.features.prescriptions.presentation

import androidx.lifecycle.viewModelScope
import com.pharmai.core.base.BaseViewModel
import com.pharmai.core.base.UiEffect
import com.pharmai.core.base.UiEvent
import com.pharmai.core.base.UiState
import com.pharmai.features.prescriptions.domain.models.Prescription
import com.pharmai.features.prescriptions.domain.usecase.GetPrescriptionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrescriptionViewModel @Inject constructor(
    private val getPrescriptionsUseCase: GetPrescriptionsUseCase
) : BaseViewModel<PrescriptionState, PrescriptionEvent, PrescriptionEffect>(PrescriptionState()) {

    private val _prescriptions = MutableStateFlow<List<Prescription>>(emptyList())
    val prescriptions = _prescriptions.asStateFlow()

    init {
        loadPrescriptions()
    }

    private fun loadPrescriptions() {
        viewModelScope.launch {
            getPrescriptionsUseCase()
                .catch { e -> updateState { it.copy(error = e.message) } }
                .collect { prescriptionList ->
                    _prescriptions.value = prescriptionList
                }
        }
    }

    override fun handleEvent(event: PrescriptionEvent) {
        when (event) {
            is PrescriptionEvent.Refresh -> loadPrescriptions()
        }
    }
}

data class PrescriptionState(
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed class PrescriptionEvent : UiEvent {
    data object Refresh : PrescriptionEvent()
}

sealed class PrescriptionEffect : UiEffect