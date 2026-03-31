package com.pharmai.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "scanned_medicines")
data class ScannedMedicineEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val medicineName: String,
    val brandName: String? = null,
    val imageUrl: String? = null,
    val barcode: String? = null,
    val confidenceScore: Float,
    val scanType: String, // PILL_RECOGNITION, PACKAGE_RECOGNITION, BARCODE_SCAN, PRESCRIPTION_SCAN
    val detectedFeatures: String? = null, // Stored as JSON
    val matchedDrugId: String? = null,
    val notes: String? = null,
    val scannedAt: Date = Date(),
    val isAddedToInventory: Boolean = false
)