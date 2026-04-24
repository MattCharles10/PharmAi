package com.pharmai.features.scanner.domain.usecase

import android.graphics.Bitmap
import com.pharmai.features.scanner.domain.models.ScanResult
import com.pharmai.features.scanner.domain.repository.ScannerRepository
import javax.inject.Inject

class ScanMedicineUseCase @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke(bitmap: Bitmap): ScanResult = repository.scanMedicine(bitmap)
}

class RecognizeMedicineUseCase @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke(bitmap: Bitmap): ScanResult = repository.scanMedicine(bitmap)
}

class ScanBarcodeUseCase @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke(bitmap: Bitmap): String? = repository.scanBarcode(bitmap)
}

class SaveScanUseCase @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke(scan: ScanResult) = repository.saveScan(scan)
}

class GetScanHistoryUseCase @Inject constructor(
    private val repository: ScannerRepository
) {
    operator fun invoke() = repository.getScanHistory()
}

class DeleteScanUseCase @Inject constructor(
    private val repository: ScannerRepository
) {
    suspend operator fun invoke(id: String) = repository.deleteScan(id)
}