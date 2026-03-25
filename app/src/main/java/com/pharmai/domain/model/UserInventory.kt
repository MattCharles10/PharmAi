package com.pharmai.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class UserInventory(
    val id: Int,
    val userId: String,
    val drugId: String,
    val name: String,
    val dosage: String,
    val quantity: Int,
    val unit: String, // "tablets", "ml", "capsules", etc.
    val expiryDate: Date? = null,
    val purchaseDate: Date = Date(),
    val batchNumber: String? = null,
    val manufacturer: String? = null,
    val instructions: String? = null,
    val imageUrl: String? = null,
    val notes: String? = null,
    val isRefillRequired: Boolean = false,
    val refillThreshold: Int = 5,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable {
    val isExpired: Boolean
        get() = expiryDate?.before(Date()) ?: false

    val isExpiringSoon: Boolean
        get() = expiryDate?.let { date ->
            val daysUntilExpiry = (date.time - Date().time) / (1000 * 60 * 60 * 24)
            daysUntilExpiry in 0..30
        } ?: false

    val needsRefill: Boolean
        get() = isRefillRequired && quantity <= refillThreshold
}