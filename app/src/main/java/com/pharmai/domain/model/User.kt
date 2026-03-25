package com.pharmai.data.model.domain


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class User(
    val id: String,
    val email: String,
    val name: String,
    val phoneNumber: String? = null,
    val dateOfBirth: Date? = null,
    val gender: Gender? = null,
    val bloodType: BloodType? = null,
    val allergies: List<String> = emptyList(),
    val chronicConditions: List<String> = emptyList(),
    val emergencyContact: EmergencyContact? = null,
    val insuranceProvider: String? = null,
    val insuranceId: String? = null,
    val profileImageUrl: String? = null,
    val isBiometricEnabled: Boolean = false,
    val isDarkModeEnabled: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val emailNotifications: Boolean = true,
    val expiryAlertDays: Int = 30,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable

@Parcelize
data class EmergencyContact(
    val name: String,
    val relationship: String,
    val phoneNumber: String
) : Parcelable

@Parcelize
enum class Gender : Parcelable {
    MALE,
    FEMALE,
    OTHER,
    PREFER_NOT_TO_SAY
}

@Parcelize
enum class BloodType : Parcelable {
    A_POSITIVE,
    A_NEGATIVE,
    B_POSITIVE,
    B_NEGATIVE,
    O_POSITIVE,
    O_NEGATIVE,
    AB_POSITIVE,
    AB_NEGATIVE,
    UNKNOWN
}