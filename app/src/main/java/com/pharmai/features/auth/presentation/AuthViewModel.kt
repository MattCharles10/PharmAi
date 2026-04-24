package com.pharmai.features.auth.presentation

import androidx.lifecycle.viewModelScope
import com.pharmai.core.base.BaseViewModel
import com.pharmai.core.base.UiEffect
import com.pharmai.core.base.UiEvent
import com.pharmai.core.base.UiState
import com.pharmai.features.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<AuthState, AuthEvent, AuthEffect>(AuthState()) {

    override fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> login(event.email, event.password)
            is AuthEvent.SignUp -> signUp(event.name, event.email, event.password)
            is AuthEvent.Logout -> logout()
            is AuthEvent.AuthenticateWithBiometrics -> authenticateWithBiometrics()
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }
            authRepository.login(email, password)
                .onSuccess {
                    updateState { it.copy(isLoading = false) }
                    sendEffect(AuthEffect.NavigateToHome)
                }
                .onFailure { error ->
                    updateState { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }
            authRepository.signUp(name, email, password)
                .onSuccess {
                    updateState { it.copy(isLoading = false) }
                    sendEffect(AuthEffect.NavigateToHome)
                }
                .onFailure { error ->
                    updateState { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    private fun authenticateWithBiometrics() {
        viewModelScope.launch {
            authRepository.authenticateWithBiometrics()
                .onSuccess {
                    sendEffect(AuthEffect.NavigateToHome)
                }
        }
    }
}

// State, Event, Effect - MUST implement the base interfaces
data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed class AuthEvent : UiEvent {
    data class Login(val email: String, val password: String) : AuthEvent()
    data class SignUp(val name: String, val email: String, val password: String) : AuthEvent()
    data object Logout : AuthEvent()
    data object AuthenticateWithBiometrics : AuthEvent()
}

sealed class AuthEffect : UiEffect {
    data object NavigateToHome : AuthEffect()
}