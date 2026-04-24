package com.pharmai.features.inventory.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory ORDER BY expiryDate ASC")
    fun observeAll(): Flow<List<InventoryEntity>>

    @Query("SELECT * FROM inventory WHERE id = :id")
    suspend fun getById(id: Long): InventoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: InventoryEntity): Long

    @Update
    suspend fun update(item: InventoryEntity)

    @Query("DELETE FROM inventory WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM inventory WHERE expiryDate <= :date")
    suspend fun getExpired(date: Date): List<InventoryEntity>

    @Query("SELECT * FROM inventory WHERE barcode = :barcode")
    suspend fun getByBarcode(barcode: String): InventoryEntity?
}