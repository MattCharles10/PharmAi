
package com.pharmai.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ScannedMedicine(
    val id: String,
    val userId: String,
    val medicineName: String,
    val brandName: String? = null,
    val imageUrl: String? = null,
    val barcode: String? = null,
    val confidenceScore: Float,
    val scanType: ScanType,
    val detectedFeatures: DetectedFeatures? = null,
    val matchedDrugId: String? = null,
    val notes: String? = null,
    val scannedAt: Date = Date(),
    val isAddedToInventory: Boolean = false
) : Parcelable

@Parcelize
data class DetectedFeatures(
    val shape: PillShape? = null,
    val color: PillColor? = null,
    val imprint: String? = null,
    val size: String? = null,
    val packaging: String? = null,
    val manufacturerLogo: String? = null,
    val textDetected: String? = null
) : Parcelable

@Parcelize
enum class ScanType : Parcelable {
    PILL_RECOGNITION, PACKAGE_RECOGNITION, BARCODE_SCAN, PRESCRIPTION_SCAN
}

@Parcelize
enum class PillShape : Parcelable {
    ROUND, OVAL, CAPSULE_SHAPED, TRIANGULAR, SQUARE, PENTAGON, OCTAGON, UNKNOWN
}

@Parcelize
enum class PillColor : Parcelable {
    WHITE, YELLOW, BLUE, RED, GREEN, ORANGE, BROWN, PINK, PURPLE, GRAY, MULTI_COLOR, UNKNOWN
}