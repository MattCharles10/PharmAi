package com.pharmai.features.search.presentation

import androidx.lifecycle.viewModelScope
import com.pharmai.core.base.BaseViewModel
import com.pharmai.core.base.UiEffect
import com.pharmai.core.base.UiEvent
import com.pharmai.core.base.UiState
import com.pharmai.features.search.domain.models.Drug
import com.pharmai.features.search.domain.usecase.SearchDrugsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDrugsUseCase: SearchDrugsUseCase
) : BaseViewModel<SearchState, SearchEvent, SearchEffect>(SearchState()) {

    override fun handleEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.Search -> search(event.query)
            is SearchEvent.ClearResults -> updateState { it.copy(results = emptyList()) }
        }
    }

    private fun search(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }
            try {
                val results = searchDrugsUseCase(query)
                updateState { it.copy(isLoading = false, results = results) }
            } catch (e: Exception) {
                updateState { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}

// FIXED: Implement UiState
data class SearchState(
    val query: String = "",
    val results: List<Drug> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

// FIXED: Implement UiEvent
sealed class SearchEvent : UiEvent {
    data class Search(val query: String) : SearchEvent()
    data object ClearResults : SearchEvent()
}

// FIXED: Implement UiEffect
sealed class SearchEffect : UiEffect