package com.pharmai.features.scanner.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pharmai.features.scanner.domain.models.ScanResult
import com.pharmai.features.scanner.domain.models.ScanType
import java.util.*

@Entity(tableName = "scans")
data class ScanEntity(
    @PrimaryKey val id: String,
    val medicineName: String?,
    val genericName: String?,
    val manufacturer: String?,
    val strength: String?,
    val confidence: Float,
    val scanType: String,
    val imageUri: String?,
    val barcode: String?,
    val expiryDate: Date?,
    val batchNumber: String?,
    val scanDate: Date
) {
    fun toDomain() = ScanResult(
        id = id, medicineName = medicineName, genericName = genericName,
        manufacturer = manufacturer, strength = strength, confidence = confidence,
        scanType = ScanType.valueOf(scanType), imageUri = imageUri, barcode = barcode,
        expiryDate = expiryDate, batchNumber = batchNumber, scanDate = scanDate
    )

    companion object {
        fun fromDomain(scan: ScanResult) = ScanEntity(
            id = scan.id, medicineName = scan.medicineName, genericName = scan.genericName,
            manufacturer = scan.manufacturer, strength = scan.strength, confidence = scan.confidence,
            scanType = scan.scanType.name, imageUri = scan.imageUri, barcode = scan.barcode,
            expiryDate = scan.expiryDate, batchNumber = scan.batchNumber, scanDate = scan.scanDate
        )
    }
}