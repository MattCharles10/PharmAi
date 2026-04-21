package com.pharmai.domain.models

import java.util.*

// Drug Models
data class Drug(
    val id: String,
    val name: String,
    val genericName: String? = null,
    val manufacturer: String? = null,
    val strength: String? = null,
    val description: String? = null,
    val indications: List<String> = emptyList(),
    val sideEffects: List<String> = emptyList(),
    val imageUrl: String? = null,
    val isFavorite: Boolean = false
)

// Inventory Models
data class UserInventory(
    val id: Long = 0,
    val drugId: String,
    val drugName: String,
    val quantity: Int,
    val unit: String = "tablet",
    val expiryDate: Date,
    val batchNumber: String? = null,
    val barcode: String? = null,
    val notes: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) {
    val isExpired: Boolean
        get() = expiryDate.before(Date())

    val daysUntilExpiry: Int
        get() = ((expiryDate.time - Date().time) / (1000 * 60 * 60 * 24)).toInt()

    val status: ExpiryStatus
        get() = when {
            isExpired -> ExpiryStatus.EXPIRED
            daysUntilExpiry <= 30 -> ExpiryStatus.EXPIRING_SOON
            else -> ExpiryStatus.VALID
        }
}

enum class ExpiryStatus {
    VALID, EXPIRING_SOON, EXPIRED
}

// Reminder Models
data class Reminder(
    val id: Long = 0,
    val inventoryItemId: Long? = null,
    val medicineName: String,
    val dosage: String,
    val timeHour: Int,
    val timeMinute: Int,
    val isActive: Boolean = true,
    val daysOfWeek: List<Int> = emptyList(),
    val startDate: Date = Date(),
    val endDate: Date? = null
) {
    fun toTimeString(): String = String.format("%02d:%02d", timeHour, timeMinute)
}

// Prescription Models
data class Prescription(
    val id: Long = 0,
    val doctorName: String? = null,
    val prescriptionDate: Date,
    val drugName: String,
    val dosage: String,
    val frequency: String,
    val instructions: String? = null,
    val imageUrls: List<String> = emptyList(),
    val isActive: Boolean = true
)

// ML Models
data class ScannedMedicine(
    val id: String = UUID.randomUUID().toString(),
    val medicineName: String? = null,
    val genericName: String? = null,
    val manufacturer: String? = null,
    val confidence: Float = 0f,
    val scanType: ScanType,
    val imageUri: String? = null,
    val barcode: String? = null,
    val expiryDate: Date? = null,
    val scanDate: Date = Date(),
    val alternatives: List<MedicineMatch> = emptyList()
)

data class MedicineMatch(
    val drugId: String,
    val name: String,
    val confidence: Float,
    val matchType: MatchType
)

enum class ScanType {
    PILL, PACKAGE, BARCODE, PRESCRIPTION
}

enum class MatchType {
    EXACT, VISUAL_SIMILAR, ACTIVE_INGREDIENT
}

data class ClassificationResult(
    val medicineName: String?,
    val confidence: Float,
    val scanType: ScanType,
    val alternatives: List<MedicineMatch> = emptyList()
)

data class DrugAlternative(
    val name: String,
    val rxcui: String,
    val relationType: String
)