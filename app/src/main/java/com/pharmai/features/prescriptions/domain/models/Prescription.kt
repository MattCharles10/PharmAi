package com.pharmai.features.prescriptions.domain.models

import java.util.*

data class Prescription(
    val id: Long = 0,
    val doctorName: String? = null,
    val hospitalName: String? = null,
    val prescriptionDate: Date,
    val expiryDate: Date? = null,
    val drugName: String,
    val dosage: String,
    val frequency: String,
    val duration: String? = null,
    val instructions: String? = null,
    val imageUrls: List<String> = emptyList(),
    val isActive: Boolean = true
)