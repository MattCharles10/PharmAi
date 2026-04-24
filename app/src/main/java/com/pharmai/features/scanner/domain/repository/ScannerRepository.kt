package com.pharmai.features.scanner.domain.repository

import android.graphics.Bitmap
import com.pharmai.features.scanner.domain.models.ScanResult
import kotlinx.coroutines.flow.Flow

interface ScannerRepository {
    suspend fun scanMedicine(bitmap: Bitmap): ScanResult
    suspend fun scanBarcode(bitmap: Bitmap): String?
    suspend fun detectExpiryDate(bitmap: Bitmap): java.util.Date?
    suspend fun saveScan(scan: ScanResult)
    fun getScanHistory(): Flow<List<ScanResult>>
    suspend fun deleteScan(id: String)
}