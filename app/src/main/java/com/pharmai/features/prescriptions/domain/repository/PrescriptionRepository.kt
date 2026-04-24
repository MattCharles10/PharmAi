package com.pharmai.features.prescriptions.domain.repository

import com.pharmai.features.prescriptions.domain.models.Prescription
import kotlinx.coroutines.flow.Flow

interface PrescriptionRepository {
    fun observeAll(): Flow<List<Prescription>>
    suspend fun getById(id: Long): Prescription?
    suspend fun add(prescription: Prescription): Long
    suspend fun update(prescription: Prescription)
    suspend fun delete(id: Long)
}