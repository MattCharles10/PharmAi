package com.pharmai.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val phoneNumber: String? = null,
    val dateOfBirth: Date? = null,
    val gender: String? = null,                // MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
    val bloodType: String? = null,
    val allergies: String? = null,            // Stored as JSON
    val chronicConditions: String? = null,   // Stored as JSON
    val emergencyContactName: String? = null,
    val emergencyContactRelationship: String? = null,
    val emergencyContactPhone: String? = null,
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
)