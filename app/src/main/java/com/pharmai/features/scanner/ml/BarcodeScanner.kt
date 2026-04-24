package com.pharmai.features.scanner.ml

import android.graphics.Bitmap
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class BarcodeScanner @Inject constructor() {
    private val scanner = BarcodeScanning.getClient()

    suspend fun scan(bitmap: Bitmap): String? = suspendCancellableCoroutine { continuation ->
        val image = InputImage.fromBitmap(bitmap, 0)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                continuation.resume(barcodes.firstOrNull()?.rawValue)
            }
            .addOnFailureListener {
                continuation.resume(null)
            }
    }
}