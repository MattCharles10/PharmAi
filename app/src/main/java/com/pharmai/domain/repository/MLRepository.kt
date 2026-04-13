package com.pharmai.domain.repository




import android.graphics.Bitmap
import com.pharmai.domain.model.MedicineMatch
import com.pharmai.domain.model.ScannedMedicine
import kotlinx.coroutines.flow.Flow

interface MLRepository {
    suspend fun recognizeMedicine(image: Bitmap): Result<List<MedicineMatch>>
    suspend fun detectExpiryDate(image: Bitmap): Result<String?>
    suspend fun verifyPrescription(image: Bitmap): Result<Map<String, String>>
    suspend fun saveScanHistory(scan: ScannedMedicine): Result<Unit>
    fun getScanHistory(userId: String): Flow<List<ScannedMedicine>>
    suspend fun updateModel(): Result<Unit>
}