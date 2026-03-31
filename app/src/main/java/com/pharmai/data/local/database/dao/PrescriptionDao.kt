
package com.pharmai.data.local.database.dao

import androidx.room.*
import com.pharmai.data.model.database.PrescriptionEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface PrescriptionDao {
    @Query("SELECT * FROM prescriptions WHERE userId = :userId ORDER BY prescriptionDate DESC")
    fun getPrescriptions(userId: String): Flow<List<PrescriptionEntity>>

    @Query("SELECT * FROM prescriptions WHERE userId = :userId AND id = :prescriptionId")
    suspend fun getPrescriptionById(userId: String, prescriptionId: Int): PrescriptionEntity?

    @Query("SELECT * FROM prescriptions WHERE userId = :userId AND isActive = 1")
    fun getActivePrescriptions(userId: String): Flow<List<PrescriptionEntity>>

    @Query("SELECT * FROM prescriptions WHERE userId = :userId AND prescriptionDate >= :startDate")
    fun getPrescriptionsAfterDate(userId: String, startDate: Date): Flow<List<PrescriptionEntity>>

    @Insert
    suspend fun insertPrescription(prescription: PrescriptionEntity): Long

    @Update
    suspend fun updatePrescription(prescription: PrescriptionEntity)

    @Delete
    suspend fun deletePrescription(prescription: PrescriptionEntity)

    @Query("UPDATE prescriptions SET isActive = 0 WHERE id = :prescriptionId")
    suspend fun deactivatePrescription(prescriptionId: Int)
}