package com.pharmai.data.local.database.dao


import androidx.room.*
import com.pharmai.data.model.database.UserInventoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface UserInventoryDao {
    @Query("SELECT * FROM user_inventory WHERE userId = :userId ORDER BY expiryDate ASC")
    fun getInventory(userId: String): Flow<List<UserInventoryEntity>>

    @Query("SELECT * FROM user_inventory WHERE userId = :userId AND expiryDate BETWEEN :startDate AND :endDate")
    fun getExpiringMedicines(userId: String, startDate: Date, endDate: Date): Flow<List<UserInventoryEntity>>

    @Query("SELECT * FROM user_inventory WHERE userId = :userId AND expiryDate < :currentDate")
    fun getExpiredMedicines(userId: String, currentDate: Date): Flow<List<UserInventoryEntity>>

    @Query("SELECT * FROM user_inventory WHERE userId = :userId AND id = :inventoryId")
    suspend fun getInventoryById(userId: String, inventoryId: Int): UserInventoryEntity?

    @Query("SELECT * FROM user_inventory WHERE userId = :userId AND drugId = :drugId")
    suspend fun getInventoryByDrugId(userId: String, drugId: String): UserInventoryEntity?

    @Insert
    suspend fun insertInventoryItem(item: UserInventoryEntity): Long

    @Update
    suspend fun updateInventoryItem(item: UserInventoryEntity)

    @Delete
    suspend fun deleteInventoryItem(item: UserInventoryEntity)

    @Query("DELETE FROM user_inventory WHERE userId = :userId AND id = :inventoryId")
    suspend fun deleteInventoryItemById(userId: String, inventoryId: Int)

    @Query("UPDATE user_inventory SET quantity = quantity + :quantity WHERE id = :inventoryId")
    suspend fun updateQuantity(inventoryId: Int, quantity: Int)
}