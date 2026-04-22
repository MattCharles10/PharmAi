package com.pharmai.ml

import android.content.Context
import android.graphics.Bitmap
import com.pharmai.core.Constants
import com.pharmai.domain.models.ClassificationResult
import com.pharmai.domain.models.ScanType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class MedicineClassifier(private val context: Context) {
    private val engine = TFLiteEngine(context)
    private var labels: List<String> = emptyList()

    suspend fun initialize(): Boolean = withContext(Dispatchers.IO) {
        loadLabels() && engine.loadModel(Constants.PILL_MODEL)
    }

    suspend fun classify(bitmap: Bitmap): ClassificationResult = withContext(Dispatchers.IO) {
        if (!engine.loadModel(Constants.PILL_MODEL)) {
            return@withContext ClassificationResult(null, 0f, ScanType.PILL)
        }

        val inputBuffer = TFLiteEngine.bitmapToByteBuffer(bitmap, engine.getInputSize())
        val output = engine.runInference(inputBuffer)

        val topIndex = output.indices.maxByOrNull { output[it] } ?: -1
        val topResult = labels.getOrNull(topIndex)

        ClassificationResult(
            medicineName = topResult,
            confidence = output.getOrNull(topIndex) ?: 0f,
            scanType = ScanType.PILL
        )
    }

    private fun loadLabels(): Boolean {
        return try {
            context.assets.open("${Constants.LABELS_PATH}pill_labels.txt").use { input ->
                labels = BufferedReader(InputStreamReader(input)).readLines()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun close() = engine.close()
}

class PackageClassifier(private val context: Context) {
    private val engine = TFLiteEngine(context)

    suspend fun classify(bitmap: Bitmap): ClassificationResult = withContext(Dispatchers.IO) {
        if (!engine.loadModel(Constants.PACKAGE_MODEL)) {
            return@withContext ClassificationResult(null, 0f, ScanType.PACKAGE)
        }

        val inputBuffer = TFLiteEngine.bitmapToByteBuffer(bitmap, engine.getInputSize())
        val output = engine.runInference(inputBuffer)

        ClassificationResult(
            medicineName = "Package detected",
            confidence = output.maxOrNull() ?: 0f,
            scanType = ScanType.PACKAGE
        )
    }

    fun close() = engine.close()
}

// TextRecognizer removed from here - it's in Detectors.kt