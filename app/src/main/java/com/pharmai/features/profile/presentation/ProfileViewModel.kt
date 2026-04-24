package com.pharmai.features.profile.presentation

import androidx.lifecycle.viewModelScope
import com.pharmai.core.base.BaseViewModel
import com.pharmai.core.base.UiEffect
import com.pharmai.core.base.UiEvent
import com.pharmai.core.base.UiState
import com.pharmai.features.profile.domain.models.UserProfile
import com.pharmai.features.profile.domain.usecase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) : BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>(ProfileState()) {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            getProfileUseCase()
                .catch { e -> updateState { it.copy(isLoading = false, error = e.message) } }
                .collect { userProfile ->
                    _profile.value = userProfile
                    updateState { it.copy(isLoading = false) }
                }
        }
    }

    override fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.UpdateProfile -> { }
            ProfileEvent.Logout -> sendEffect(ProfileEffect.NavigateToLogin)
            is ProfileEvent.Refresh -> loadProfile()
        }
    }
}

data class ProfileState(
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed class ProfileEvent : UiEvent {
    data class UpdateProfile(val profile: UserProfile) : ProfileEvent()
    data object Logout : ProfileEvent()
    data object Refresh : ProfileEvent()
}

sealed class ProfileEffect : UiEffect {
    data object NavigateToLogin : ProfileEffect()
}