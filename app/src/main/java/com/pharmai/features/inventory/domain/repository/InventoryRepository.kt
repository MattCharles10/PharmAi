package com.pharmai.features.inventory.domain.repository

import com.pharmai.features.inventory.domain.models.Medicine
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    fun observeAll(): Flow<List<Medicine>>
    suspend fun getById(id: Long): Medicine?
    suspend fun add(medicine: Medicine): Long
    suspend fun update(medicine: Medicine)
    suspend fun delete(id: Long)
    suspend fun getExpiring(threshold: Int = 30): List<Medicine>
    suspend fun getExpired(): List<Medicine>
    suspend fun getByBarcode(barcode: String): Medicine?
}