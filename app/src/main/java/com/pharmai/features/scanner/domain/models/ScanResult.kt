package com.pharmai.features.scanner.domain.models

import java.util.*

data class ScanResult(
    val id: String = UUID.randomUUID().toString(),
    val medicineName: String? = null,
    val genericName: String? = null,
    val manufacturer: String? = null,
    val strength: String? = null,
    val confidence: Float = 0f,
    val scanType: ScanType,
    val imageUri: String? = null,
    val barcode: String? = null,
    val expiryDate: Date? = null,
    val batchNumber: String? = null,
    val scanDate: Date = Date(),
    val alternatives: List<MedicineMatch> = emptyList()
)

data class MedicineMatch(
    val name: String,
    val confidence: Float,
    val ndc: String? = null
)

enum class ScanType { PILL, PACKAGE, BARCODE, TEXT }
enum class ScanState { IDLE, SCANNING, PROCESSING, SUCCESS, ERROR }