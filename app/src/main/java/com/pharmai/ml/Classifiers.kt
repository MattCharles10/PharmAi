package com.pharmai.ml

import android.content.Context
import android.graphics.Bitmap
import com.pharmai.core.Constants
import com.pharmai.domain.models.ClassificationResult
import com.pharmai.domain.models.ScanType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicineClassifier(private val context: Context) {
    private val engine = TFLiteEngine(context)
    private var labels: List<String> = emptyList()

    suspend fun initialize(): Boolean = withContext(Dispatchers.IO) { loadLabels() && engine.loadModel(Constants.PILL_MODEL) }

    suspend fun classify(bitmap: Bitmap): ClassificationResult = withContext(Dispatchers.IO) {
        if (!engine.loadModel(Constants.PILL_MODEL)) return@withContext ClassificationResult(null, 0f, ScanType.PILL)
        val input = TFLiteEngine.bitmapToByteBuffer(bitmap, engine.getInputSize())
        val output = engine.runInference(input)
        val topIndex = output.indices.maxByOrNull { output[it] } ?: -1
        ClassificationResult(labels.getOrNull(topIndex), output.getOrNull(topIndex) ?: 0f, ScanType.PILL)
    }

    private fun loadLabels(): Boolean = try {
        context.assets.open("labels/pill_labels.txt").bufferedReader().use { labels = it.readLines() }
        true
    } catch (e: Exception) { false }
}

class PackageClassifier(private val context: Context) {
    private val engine = TFLiteEngine(context)
    suspend fun classify(bitmap: Bitmap): ClassificationResult = withContext(Dispatchers.IO) {
        engine.loadModel(Constants.PACKAGE_MODEL)
        ClassificationResult("Package", 0.8f, ScanType.PACKAGE)
    }
}