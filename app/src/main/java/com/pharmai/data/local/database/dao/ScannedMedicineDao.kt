
package com.pharmai.data.local.database.dao

import androidx.room.*
import com.pharmai.data.model.database.ScannedMedicineEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ScannedMedicineDao {
    @Query("SELECT * FROM scanned_medicines WHERE userId = :userId ORDER BY scannedAt DESC")
    fun getScanHistory(userId: String): Flow<List<ScannedMedicineEntity>>

    @Query("SELECT * FROM scanned_medicines WHERE userId = :userId AND id = :scanId")
    suspend fun getScanById(userId: String, scanId: String): ScannedMedicineEntity?

    @Query("SELECT * FROM scanned_medicines WHERE userId = :userId AND scannedAt >= :startDate")
    fun getScansAfterDate(userId: String, startDate: Date): Flow<List<ScannedMedicineEntity>>

    @Insert
    suspend fun insertScan(scan: ScannedMedicineEntity)

    @Update
    suspend fun updateScan(scan: ScannedMedicineEntity)

    @Delete
    suspend fun deleteScan(scan: ScannedMedicineEntity)

    @Query("DELETE FROM scanned_medicines WHERE userId = :userId AND scannedAt < :cutoffDate")
    suspend fun deleteOldScans(userId: String, cutoffDate: Date)

    @Query("UPDATE scanned_medicines SET isAddedToInventory = 1 WHERE id = :scanId")
    suspend fun markAsAddedToInventory(scanId: String)
}