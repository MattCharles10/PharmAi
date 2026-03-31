package com.pharmai.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_inventory")
data class UserInventoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val drugId: String,
    val name: String,
    val dosage: String,
    val quantity: Int,
    val unit: String,
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
)