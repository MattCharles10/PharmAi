package com.pharmai.data.local

import androidx.room.*
import com.pharmai.data.local.Entities.DrugEntity
import com.pharmai.data.local.Entities.FavoriteEntity
import com.pharmai.data.local.Entities.InventoryEntity
import com.pharmai.data.local.Entities.PrescriptionEntity
import com.pharmai.data.local.Entities.ScanEntity
import com.pharmai.data.local.Entities.ReminderEntity
import com.pharmai.data.local.Entities.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

object Daos {

    @Dao
    interface DrugDao {
        @Query("SELECT * FROM drugs WHERE id = :id")
        suspend fun getById(id: String): DrugEntity?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(drug: DrugEntity)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAll(drugs: List<DrugEntity>)

        @Query("SELECT * FROM drugs WHERE name LIKE '%' || :query || '%' OR genericName LIKE '%' || :query || '%'")
        suspend fun search(query: String): List<DrugEntity>

        @Query("SELECT * FROM drugs")
        suspend fun getAll(): List<DrugEntity>

        @Delete
        suspend fun delete(drug: DrugEntity)
    }

    @Dao
    interface InventoryDao {
        @Query("SELECT * FROM inventory ORDER BY expiryDate ASC")
        fun observeAll(): Flow<List<InventoryEntity>>

        @Query("SELECT * FROM inventory ORDER BY expiryDate ASC")
        suspend fun getAll(): List<InventoryEntity>

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

        @Query("SELECT * FROM inventory WHERE expiryDate BETWEEN :start AND :end")
        suspend fun getExpiring(start: Date, end: Date): List<InventoryEntity>

        @Query("SELECT * FROM inventory WHERE barcode = :barcode")
        suspend fun getByBarcode(barcode: String): InventoryEntity?
    }

    @Dao
    interface ReminderDao {
        @Query("SELECT * FROM reminders WHERE isActive = 1")
        fun observeActive(): Flow<List<ReminderEntity>>

        @Query("SELECT * FROM reminders")
        suspend fun getAll(): List<ReminderEntity>

        @Query("SELECT * FROM reminders WHERE id = :id")
        suspend fun getById(id: Long): ReminderEntity?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(reminder: ReminderEntity): Long

        @Update
        suspend fun update(reminder: ReminderEntity)

        @Query("DELETE FROM reminders WHERE id = :id")
        suspend fun delete(id: Long)

        @Query("UPDATE reminders SET isActive = :active WHERE id = :id")
        suspend fun toggle(id: Long, active: Boolean)
    }

    @Dao
    interface PrescriptionDao {
        @Query("SELECT * FROM prescriptions ORDER BY prescriptionDate DESC")
        fun observeAll(): Flow<List<PrescriptionEntity>>

        @Query("SELECT * FROM prescriptions")
        suspend fun getAll(): List<PrescriptionEntity>

        @Query("SELECT * FROM prescriptions WHERE id = :id")
        suspend fun getById(id: Long): PrescriptionEntity?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(prescription: PrescriptionEntity): Long

        @Update
        suspend fun update(prescription: PrescriptionEntity)

        @Query("DELETE FROM prescriptions WHERE id = :id")
        suspend fun delete(id: Long)
    }

    @Dao
    interface ScanDao {
        @Query("SELECT * FROM scans ORDER BY scanDate DESC")
        fun observeAll(): Flow<List<ScanEntity>>

        @Query("SELECT * FROM scans ORDER BY scanDate DESC")
        suspend fun getAll(): List<ScanEntity>

        @Query("SELECT * FROM scans WHERE id = :id")
        suspend fun getById(id: String): ScanEntity?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(scan: ScanEntity)

        @Query("DELETE FROM scans WHERE id = :id")
        suspend fun delete(id: String)
    }

    @Dao
    interface FavoriteDao {
        @Query("SELECT d.* FROM drugs d INNER JOIN favorites f ON d.id = f.drugId ORDER BY f.addedAt DESC")
        fun observeFavorites(): Flow<List<DrugEntity>>

        @Query("SELECT * FROM favorites")
        suspend fun getAll(): List<FavoriteEntity>

        @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE drugId = :drugId)")
        suspend fun isFavorite(drugId: String): Boolean

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun add(favorite: FavoriteEntity)

        @Query("DELETE FROM favorites WHERE drugId = :drugId")
        suspend fun remove(drugId: String)
    }

    @Dao
    interface SearchHistoryDao {
        @Insert
        suspend fun insert(history: SearchHistoryEntity)

        @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 10")
        suspend fun getRecent(): List<SearchHistoryEntity>

        @Query("DELETE FROM search_history")
        suspend fun clear()
    }
}