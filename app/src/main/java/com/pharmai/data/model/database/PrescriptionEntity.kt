package com.pharmai.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date



@Entity(tableName = "prescriptions")

data class PrescriptionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val doctorName: String,
    val doctorSpecialty: String? = null,
    val clinicName: String? = null,
    val prescriptionDate: Date,
    val expiryDate: Date? = null,
    val medications: String, // Stored as JSON
    val notes: String? = null,
    val imageUrl: String? = null,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()

)