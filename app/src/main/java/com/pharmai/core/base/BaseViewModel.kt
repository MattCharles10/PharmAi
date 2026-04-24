package com.pharmai.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    protected fun updateState(update: (State) -> State) {
        _state.update(update)
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    protected fun <T> execute(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                val result = block()
                withContext(Dispatchers.Main) { onSuccess(result) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onError(e.message ?: "An error occurred") }
            }
        }
    }

    abstract fun handleEvent(event: Event)
}

interface UiState
interface UiEvent
interface UiEffect