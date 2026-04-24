package com.pharmai.features.scanner.ml

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class ClassificationResult(
    val medicineName: String?,
    val genericName: String?,
    val confidence: Float
)

class MedicineClassifier @Inject constructor(
    private val context: Context
) {
    private val engine = TFLiteEngine(context)

    suspend fun classify(bitmap: Bitmap): ClassificationResult = withContext(Dispatchers.IO) {
        ClassificationResult(
            medicineName = "Sample Medicine",
            genericName = "Sample Generic",
            confidence = 0.95f
        )
    }

    fun close() = engine.close()
}