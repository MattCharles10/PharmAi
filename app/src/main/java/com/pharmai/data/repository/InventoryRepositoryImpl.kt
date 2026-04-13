package com.pharmai.data.repository


import com.pharmai.data.local.database.dao.UserInventoryDao
import com.pharmai.data.model.database.UserInventoryEntity
import com.pharmai.domain.model.UserInventory
import com.pharmai.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryRepositoryImpl @Inject constructor(
    private val inventoryDao: UserInventoryDao
) : InventoryRepository {

    override fun getInventory(userId: String): Flow<List<UserInventory>> {
        return inventoryDao.getInventory(userId).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun addToInventory(item: UserInventory): Result<Long> {
        return try {
            val entity = item.toEntity()
            val id = inventoryDao.insertInventoryItem(entity)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateInventory(item: UserInventory): Result<Unit> {
        return try {
            val entity = item.toEntity()
            inventoryDao.updateInventoryItem(entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFromInventory(userId: String, inventoryId: Int): Result<Unit> {
        return try {
            inventoryDao.deleteInventoryItemById(userId, inventoryId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getExpiringMedicines(userId: String, daysThreshold: Int): Flow<List<UserInventory>> {
        val startDate = Date()
        val endDate = Date(startDate.time + daysThreshold * 24L * 60 * 60 * 1000)
        return inventoryDao.getExpiringMedicines(userId, startDate, endDate).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getExpiredMedicines(userId: String): Flow<List<UserInventory>> {
        return inventoryDao.getExpiredMedicines(userId, Date()).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun updateQuantity(inventoryId: Int, quantity: Int): Result<Unit> {
        return try {
            inventoryDao.updateQuantity(inventoryId, quantity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

fun UserInventoryEntity.toDomainModel(): UserInventory {
    return UserInventory(
        id = id,
        userId = userId,
        drugId = drugId,
        name = name,
        dosage = dosage,
        quantity = quantity,
        unit = unit,
        expiryDate = expiryDate,
        purchaseDate = purchaseDate,
        batchNumber = batchNumber,
        manufacturer = manufacturer,
        instructions = instructions,
        imageUrl = imageUrl,
        notes = notes,
        isRefillRequired = isRefillRequired,
        refillThreshold = refillThreshold,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun UserInventory.toEntity(): UserInventoryEntity {
    return UserInventoryEntity(
        id = id,
        userId = userId,
        drugId = drugId,
        name = name,
        dosage = dosage,
        quantity = quantity,
        unit = unit,
        expiryDate = expiryDate,
        purchaseDate = purchaseDate,
        batchNumber = batchNumber,
        manufacturer = manufacturer,
        instructions = instructions,
        imageUrl = imageUrl,
        notes = notes,
        isRefillRequired = isRefillRequired,
        refillThreshold = refillThreshold,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}