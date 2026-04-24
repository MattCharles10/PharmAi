package com.pharmai.features.scanner.data

import android.graphics.Bitmap
import com.pharmai.features.scanner.data.local.ScanDao
import com.pharmai.features.scanner.data.local.ScanEntity
import com.pharmai.features.scanner.domain.models.ScanResult
import com.pharmai.features.scanner.domain.models.ScanType
import com.pharmai.features.scanner.domain.repository.ScannerRepository
import com.pharmai.features.scanner.ml.BarcodeScanner
import com.pharmai.features.scanner.ml.ExpiryDateDetector
import com.pharmai.features.scanner.ml.MedicineClassifier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScannerRepositoryImpl @Inject constructor(
    private val medicineClassifier: MedicineClassifier,
    private val barcodeScanner: BarcodeScanner,
    private val expiryDetector: ExpiryDateDetector,
    private val scanDao: ScanDao
) : ScannerRepository {

    override suspend fun scanMedicine(bitmap: Bitmap): ScanResult {
        val classificationResult = medicineClassifier.classify(bitmap)
        val barcode = barcodeScanner.scan(bitmap)
        val expiryDate = expiryDetector.detect(bitmap)

        return ScanResult(
            medicineName = classificationResult.medicineName,
            genericName = classificationResult.genericName,
            confidence = classificationResult.confidence,
            scanType = ScanType.PILL,
            barcode = barcode,
            expiryDate = expiryDate
        )
    }

    override suspend fun scanBarcode(bitmap: Bitmap): String? = barcodeScanner.scan(bitmap)
    override suspend fun detectExpiryDate(bitmap: Bitmap): java.util.Date? = expiryDetector.detect(bitmap)

    override suspend fun saveScan(scan: ScanResult) {
        scanDao.insert(ScanEntity.fromDomain(scan))
    }

    override fun getScanHistory(): Flow<List<ScanResult>> {
        return scanDao.getAll().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun deleteScan(id: String) = scanDao.delete(id)
}