package com.pharmai.features.search.presentation

import androidx.lifecycle.viewModelScope
import com.pharmai.core.base.BaseViewModel
import com.pharmai.core.base.UiEffect
import com.pharmai.core.base.UiEvent
import com.pharmai.core.base.UiState
import com.pharmai.features.search.domain.models.Drug
import com.pharmai.features.search.domain.usecase.GetDrugDetailsUseCase
import com.pharmai.features.search.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrugDetailViewModel @Inject constructor(
    private val getDrugDetailsUseCase: GetDrugDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : BaseViewModel<DrugDetailState, DrugDetailEvent, DrugDetailEffect>(DrugDetailState()) {

    override fun handleEvent(event: DrugDetailEvent) {
        when (event) {
            is DrugDetailEvent.LoadDrug -> loadDrug(event.drugId)
            is DrugDetailEvent.ToggleFavorite -> toggleFavorite(event.drugId)
        }
    }

    private fun loadDrug(drugId: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }
            try {
                val drug = getDrugDetailsUseCase(drugId)
                updateState { it.copy(isLoading = false, drug = drug) }
            } catch (e: Exception) {
                updateState { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun toggleFavorite(drugId: String) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(drugId)
                updateState { state ->
                    state.drug?.let {
                        state.copy(drug = it.copy(isFavorite = !it.isFavorite))
                    } ?: state
                }
            } catch (e: Exception) {
                updateState { it.copy(error = e.message) }
            }
        }
    }
}

// FIXED: Implement UiState
data class DrugDetailState(
    val isLoading: Boolean = false,
    val drug: Drug? = null,
    val error: String? = null
) : UiState

// FIXED: Implement UiEvent
sealed class DrugDetailEvent : UiEvent {
    data class LoadDrug(val drugId: String) : DrugDetailEvent()
    data class ToggleFavorite(val drugId: String) : DrugDetailEvent()
}

// FIXED: Implement UiEffect
sealed class DrugDetailEffect : UiEffect