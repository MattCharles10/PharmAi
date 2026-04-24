package com.pharmai.features.inventory.domain.usecase

import com.pharmai.features.inventory.domain.models.Medicine
import com.pharmai.features.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInventoryUseCase @Inject constructor(private val repository: InventoryRepository) {
    operator fun invoke(): Flow<List<Medicine>> = repository.observeAll()
}

class AddMedicineUseCase @Inject constructor(private val repository: InventoryRepository) {
    suspend operator fun invoke(medicine: Medicine): Long = repository.add(medicine)
}

class UpdateMedicineUseCase @Inject constructor(private val repository: InventoryRepository) {
    suspend operator fun invoke(medicine: Medicine) = repository.update(medicine)
}

class DeleteMedicineUseCase @Inject constructor(private val repository: InventoryRepository) {
    suspend operator fun invoke(id: Long) = repository.delete(id)
}

class GetExpiringMedicinesUseCase @Inject constructor(private val repository: InventoryRepository) {
    suspend operator fun invoke(threshold: Int = 30): List<Medicine> = repository.getExpiring(threshold)
}