package com.pharmai.features.scanner.ml

import android.graphics.Bitmap
import com.pharmai.core.utils.DateUtils
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

class ExpiryDateDetector @Inject constructor(
    private val textRecognizer: TextRecognizer
) {
    private val pattern = Pattern.compile("EXP[:\\s]*(\\d{2}/\\d{2}/\\d{4})", Pattern.CASE_INSENSITIVE)

    suspend fun detect(bitmap: Bitmap): Date? {
        val text = textRecognizer.recognizeText(bitmap)
        val matcher = pattern.matcher(text)
        return if (matcher.find()) {
            DateUtils.parseDate(matcher.group(1) ?: "")
        } else {
            null
        }
    }
}