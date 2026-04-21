package com.pharmai.domain.repository

import com.pharmai.domain.models.*
import kotlinx.coroutines.flow.Flow

interface DrugRepository {
    suspend fun searchDrugs(query: String): List<Drug>
    suspend fun getDrugById(id: String): Drug?
    suspend fun toggleFavorite(drugId: String)
    fun observeFavorites(): Flow<List<Drug>>
}

interface InventoryRepository {
    fun observeAll(): Flow<List<UserInventory>>
    suspend fun getAll(): List<UserInventory>
    suspend fun getById(id: Long): UserInventory?
    suspend fun add(item: UserInventory): Long
    suspend fun update(item: UserInventory)
    suspend fun delete(id: Long)
    suspend fun getExpiring(threshold: Int = 30): List<UserInventory>
    suspend fun getExpired(): List<UserInventory>
    suspend fun getByBarcode(barcode: String): UserInventory?
}

interface ReminderRepository {
    fun observeActive(): Flow<List<Reminder>>
    suspend fun getAll(): List<Reminder>
    suspend fun getById(id: Long): Reminder?
    suspend fun create(reminder: Reminder): Long
    suspend fun update(reminder: Reminder)
    suspend fun delete(id: Long)
    suspend fun toggle(id: Long, isActive: Boolean)
}

interface PrescriptionRepository {
    fun observeAll(): Flow<List<Prescription>>
    suspend fun add(prescription: Prescription): Long
    suspend fun update(prescription: Prescription)
    suspend fun delete(id: Long)
}

interface MLRepository {
    fun observeScans(): Flow<List<ScannedMedicine>>
    suspend fun saveScan(scan: ScannedMedicine)
    suspend fun getScanById(id: String): ScannedMedicine?
    suspend fun deleteScan(id: String)
}