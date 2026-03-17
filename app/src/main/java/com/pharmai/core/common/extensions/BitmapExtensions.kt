package com.pharmai.core.common.extensions

import android.graphics.Bitmap
import android.graphics.Matrix
import java.io.ByteArrayOutputStream

fun Bitmap.resize(maxSize: Int): Bitmap {
    val width = this.width
    val height = this.height

    val ratio = if (width > height) {
        maxSize.toFloat() / width
    } else {
        maxSize.toFloat() / height
    }

    if (ratio >= 1) return this

    val newWidth = (width * ratio).toInt()
    val newHeight = (height * ratio).toInt()

    return Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degrees)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.toByteArray(compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 80): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(compressFormat, quality, stream)
    return stream.toByteArray()
}

fun Bitmap.toGrayscale(): Bitmap {
    val width = this.width
    val height = this.height
    val grayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    for (x in 0 until width) {
        for (y in 0 until height) {
            val pixel = this.getPixel(x, y)
            val alpha = android.graphics.Color.alpha(pixel)
            val red = android.graphics.Color.red(pixel)
            val green = android.graphics.Color.green(pixel)
            val blue = android.graphics.Color.blue(pixel)

            val gray = (0.299 * red + 0.587 * green + 0.114 * blue).toInt()
            val grayPixel = android.graphics.Color.argb(alpha, gray, gray, gray)
            grayscale.setPixel(x, y, grayPixel)
        }
    }

    return grayscale
}