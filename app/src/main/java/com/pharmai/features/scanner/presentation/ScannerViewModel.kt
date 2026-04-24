package com.pharmai.features.scanner.presentation

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.pharmai.core.base.BaseViewModel
import com.pharmai.core.base.UiEffect
import com.pharmai.core.base.UiEvent
import com.pharmai.core.base.UiState
import com.pharmai.features.scanner.domain.models.ScanResult
import com.pharmai.features.scanner.domain.models.ScanState
import com.pharmai.features.scanner.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val scanMedicineUseCase: ScanMedicineUseCase,
    private val saveScanUseCase: SaveScanUseCase,
    private val getScanHistoryUseCase: GetScanHistoryUseCase,
    private val deleteScanUseCase: DeleteScanUseCase
) : BaseViewModel<ScannerState, ScannerEvent, ScannerEffect>(ScannerState()) {

    // FIXED: Use MutableStateFlow instead of stateIn
    private val _scanHistory = MutableStateFlow<List<ScanResult>>(emptyList())
    val scanHistory: StateFlow<List<ScanResult>> = _scanHistory.asStateFlow()

    init {
        loadScanHistory()
    }

    private fun loadScanHistory() {
        viewModelScope.launch {
            getScanHistoryUseCase()
                .catch { e -> updateState { it.copy(errorMessage = e.message) } }
                .collect { scanList ->
                    _scanHistory.value = scanList
                }
        }
    }

    override fun handleEvent(event: ScannerEvent) {
        when (event) {
            is ScannerEvent.CaptureImage -> scanMedicine(event.bitmap)
            is ScannerEvent.SaveScan -> saveScan(event.scan)
            is ScannerEvent.ClearResult -> updateState { it.copy(scanResult = null, scanState = ScanState.IDLE) }
            is ScannerEvent.DeleteScan -> deleteScan(event.scanId)
        }
    }

    private fun scanMedicine(bitmap: Bitmap) {
        updateState { it.copy(scanState = ScanState.PROCESSING) }
        execute(
            block = { scanMedicineUseCase(bitmap) },
            onSuccess = { result ->
                updateState { it.copy(scanResult = result, scanState = ScanState.SUCCESS) }
                saveScan(result)
                sendEffect(ScannerEffect.NavigateToResult(result.id))
            },
            onError = { error ->
                updateState { it.copy(scanState = ScanState.ERROR, errorMessage = error) }
            }
        )
    }

    private fun saveScan(scan: ScanResult) {
        viewModelScope.launch {
            try {
                saveScanUseCase(scan)
                loadScanHistory() // Refresh history
            } catch (e: Exception) {
                // Silent fail for save
            }
        }
    }

    private fun deleteScan(scanId: String) {
        viewModelScope.launch {
            try {
                deleteScanUseCase(scanId)
                loadScanHistory() // Refresh history
                sendEffect(ScannerEffect.ShowMessage("Scan deleted"))
            } catch (e: Exception) {
                sendEffect(ScannerEffect.ShowError(e.message ?: "Failed to delete"))
            }
        }
    }
}

// FIXED: Implement UiState
data class ScannerState(
    val scanState: ScanState = ScanState.IDLE,
    val scanResult: ScanResult? = null,
    val errorMessage: String? = null
) : UiState

// FIXED: Implement UiEvent
sealed class ScannerEvent : UiEvent {
    data class CaptureImage(val bitmap: Bitmap) : ScannerEvent()
    data class SaveScan(val scan: ScanResult) : ScannerEvent()
    data object ClearResult : ScannerEvent()
    data class DeleteScan(val scanId: String) : ScannerEvent()
}

// FIXED: Implement UiEffect
sealed class ScannerEffect : UiEffect {
    data class NavigateToResult(val scanId: String) : ScannerEffect()
    data class ShowError(val message: String) : ScannerEffect()
    data class ShowMessage(val message: String) : ScannerEffect()
}