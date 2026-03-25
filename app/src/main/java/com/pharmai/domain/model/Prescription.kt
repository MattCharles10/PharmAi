package com.pharmai.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Prescription(
    val id: Int,
    val userId: String,
    val doctorName: String,
    val doctorSpecialty: String? = null,
    val clinicName: String? = null,
    val prescriptionDate: Date,
    val expiryDate: Date? = null,
    val medications: List<PrescribedMedication> = emptyList(),
    val notes: String? = null,
    val imageUrl: String? = null,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) : Parcelable

@Parcelize
data class PrescribedMedication(
    val id: Int,
    val drugName: String,
    val dosage: String,
    val frequency: String, // e.g., "Twice daily", "Once daily"
    val duration: String? = null, // e.g., "7 days", "2 weeks"
    val instructions: String? = null,
    val quantity: Int? = null,
    val refills: Int = 0,
    val refillsRemaining: Int = 0
) : Parcelable