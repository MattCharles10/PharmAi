package com.pharmai.ml

import android.content.Context
import android.graphics.Bitmap
import com.pharmai.core.DateUtils
import java.util.*
import java.util.regex.Pattern

class TextRecognizer(private val context: Context) {
    suspend fun recognizeText(bitmap: Bitmap): String = "Sample recognized text"
}

class ExpiryDateDetector(private val textRecognizer: TextRecognizer) {
    private val pattern = Pattern.compile("EXP[:\\s]*(\\d{2}/\\d{2}/\\d{4})", Pattern.CASE_INSENSITIVE)
    suspend fun detect(bitmap: Bitmap): Date? {
        val text = textRecognizer.recognizeText(bitmap)
        val matcher = pattern.matcher(text)
        return if (matcher.find()) DateUtils.parseDate(matcher.group(1) ?: "") else null
    }
}

class BarcodeScanner {
    fun scan(bitmap: Bitmap): String? = null
}