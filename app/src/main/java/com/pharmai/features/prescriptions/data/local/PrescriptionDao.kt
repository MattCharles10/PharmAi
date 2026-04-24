package com.pharmai.features.prescriptions.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PrescriptionDao {
    @Query("SELECT * FROM prescriptions ORDER BY prescriptionDate DESC")
    fun observeAll(): Flow<List<PrescriptionEntity>>

    @Query("SELECT * FROM prescriptions WHERE id = :id")
    suspend fun getById(id: Long): PrescriptionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prescription: PrescriptionEntity): Long

    @Update
    suspend fun update(prescription: PrescriptionEntity)

    @Query("DELETE FROM prescriptions WHERE id = :id")
    suspend fun delete(id: Long)
}