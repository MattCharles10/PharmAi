package com.pharmai.features.prescriptions.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "prescriptions")
data class PrescriptionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val doctorName: String?,
    val hospitalName: String?,
    val prescriptionDate: Date,
    val expiryDate: Date?,
    val drugName: String,
    val dosage: String,
    val frequency: String,
    val duration: String?,
    val instructions: String?,
    val imageUrls: String,
    val isActive: Boolean
)