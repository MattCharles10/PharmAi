package com.pharmai.features.scanner.ml

import android.content.Context
import android.graphics.Bitmap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextRecognizer @Inject constructor(
    private val context: Context
) {
    private val engine = TFLiteEngine(context)

    suspend fun recognizeText(bitmap: Bitmap): String {
        // TODO: Implement OCR using the TFLite model
        // For now, return a placeholder result
        return "Sample recognized text"
    }

    suspend fun extractExpiryDate(text: String): String? {
        val expiryPatterns = listOf(
            Regex("""EXP[:\\s]*(\\d{2}/\\d{2}/\\d{4})""", RegexOption.IGNORE_CASE),
            Regex("""EXPIRY[:\\s]*(\\d{2}/\\d{2}/\\d{4})""", RegexOption.IGNORE_CASE),
            Regex("""(\\d{2}/\\d{4})"""),
            Regex("""(\\d{2}-\\d{2}-\\d{4})""")
        )

        for (pattern in expiryPatterns) {
            val match = pattern.find(text)
            if (match != null) {
                return match.groupValues.getOrNull(1)
            }
        }
        return null
    }

    suspend fun extractBatchNumber(text: String): String? {
        val batchPattern = Regex("""BATCH[:\\s]*([A-Z0-9]+)""", RegexOption.IGNORE_CASE)
        return batchPattern.find(text)?.groupValues?.getOrNull(1)
    }

    fun close() = engine.close()
}