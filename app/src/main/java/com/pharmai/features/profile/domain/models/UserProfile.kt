package com.pharmai.features.profile.domain.models

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String? = null,
    val profileImageUrl: String? = null,
    val dateOfBirth: String? = null,
    val bloodGroup: String? = null,
    val allergies: List<String> = emptyList(),
    val biometricEnabled: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val expiryAlertDays: Int = 30
)