package com.pharmai.features.inventory.data

import com.pharmai.features.inventory.data.local.InventoryDao
import com.pharmai.features.inventory.data.local.InventoryEntity
import com.pharmai.features.inventory.domain.models.Medicine
import com.pharmai.features.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val dao: InventoryDao
) : InventoryRepository {

    override fun observeAll(): Flow<List<Medicine>> = dao.observeAll().map { it.map { e -> e.toDomain() } }
    override suspend fun getById(id: Long): Medicine? = dao.getById(id)?.toDomain()
    override suspend fun add(medicine: Medicine): Long = dao.insert(InventoryEntity.fromDomain(medicine))
    override suspend fun update(medicine: Medicine) = dao.update(InventoryEntity.fromDomain(medicine))
    override suspend fun delete(id: Long) = dao.delete(id)

    override suspend fun getExpiring(threshold: Int): List<Medicine> {
        val now = Date()
        val thresholdDate = Calendar.getInstance().apply { time = now; add(Calendar.DAY_OF_YEAR, threshold) }.time
        return dao.getExpired(thresholdDate).filter { !it.expiryDate.before(now) }.map { it.toDomain() }
    }

    override suspend fun getExpired(): List<Medicine> = dao.getExpired(Date()).map { it.toDomain() }
    override suspend fun getByBarcode(barcode: String): Medicine? = dao.getByBarcode(barcode)?.toDomain()
}