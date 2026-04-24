package com.pharmai.features.inventory.domain.models

import java.util.*

data class Medicine(
    val id: Long = 0,
    val drugId: String,
    val name: String,
    val genericName: String? = null,
    val quantity: Int,
    val unit: String = "tablet",
    val expiryDate: Date,
    val batchNumber: String? = null,
    val barcode: String? = null,
    val notes: String? = null
) {
    val isExpired: Boolean get() = expiryDate.before(Date())
    val daysUntilExpiry: Int get() = ((expiryDate.time - Date().time) / 86400000).toInt()
    val status: ExpiryStatus get() = when {
        isExpired -> ExpiryStatus.EXPIRED
        daysUntilExpiry <= 30 -> ExpiryStatus.EXPIRING_SOON
        else -> ExpiryStatus.VALID
    }
}

enum class ExpiryStatus { VALID, EXPIRING_SOON, EXPIRED }