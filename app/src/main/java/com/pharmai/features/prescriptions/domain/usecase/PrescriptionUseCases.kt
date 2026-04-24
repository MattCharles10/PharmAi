package com.pharmai.features.prescriptions.domain.usecase

import com.pharmai.features.prescriptions.domain.models.Prescription
import com.pharmai.features.prescriptions.domain.repository.PrescriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPrescriptionsUseCase @Inject constructor(private val repository: PrescriptionRepository) {
    operator fun invoke(): Flow<List<Prescription>> = repository.observeAll()
}

class AddPrescriptionUseCase @Inject constructor(private val repository: PrescriptionRepository) {
    suspend operator fun invoke(prescription: Prescription): Long = repository.add(prescription)
}