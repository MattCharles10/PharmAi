package com.pharmai.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

object Entities {

    @Entity(tableName = "drugs")
    data class DrugEntity(
        @PrimaryKey val id: String,
        val name: String,
        val genericName: String? = null,
        val manufacturer: String? = null,
        val strength: String? = null,
        val description: String? = null,
        val indications: String = "[]",
        val sideEffects: String = "[]",
        val imageUrl: String? = null,
        val lastUpdated: Long = System.currentTimeMillis()
    )

    @Entity(tableName = "inventory")
    data class InventoryEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val drugId: String,
        val drugName: String,
        val quantity: Int,
        val unit: String,
        val expiryDate: Date,
        val batchNumber: String? = null,
        val barcode: String? = null,
        val notes: String? = null,
        val createdAt: Date = Date(),
        val updatedAt: Date = Date()
    )

    @Entity(tableName = "reminders")
    data class ReminderEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val inventoryItemId: Long? = null,
        val medicineName: String,
        val dosage: String,
        val timeHour: Int,
        val timeMinute: Int,
        val isActive: Boolean = true,
        val daysOfWeek: String? = null,
        val startDate: Date = Date(),
        val endDate: Date? = null
    )

    @Entity(tableName = "prescriptions")
    data class PrescriptionEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val doctorName: String? = null,
        val prescriptionDate: Date,
        val drugName: String,
        val dosage: String,
        val frequency: String,
        val instructions: String? = null,
        val imageUrls: String = "[]",
        val isActive: Boolean = true
    )

    @Entity(tableName = "scans")
    data class ScanEntity(
        @PrimaryKey val id: String,
        val medicineName: String? = null,
        val genericName: String? = null,
        val manufacturer: String? = null,
        val confidence: Float,
        val scanType: String,
        val imageUri: String? = null,
        val barcode: String? = null,
        val expiryDate: Date? = null,
        val alternatives: String = "[]",
        val scanDate: Date
    )

    @Entity(tableName = "favorites")
    data class FavoriteEntity(
        @PrimaryKey val drugId: String,
        val addedAt: Date = Date()
    )

    @Entity(tableName = "search_history")
    data class SearchHistoryEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val query: String,
        val timestamp: Date = Date()
    )
}