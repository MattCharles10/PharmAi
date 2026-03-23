package com.pharmai.core.common.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.IOException

fun Bitmap.rotateToCorrectOrientation(imagePath: String): Bitmap {
    try {
        val ei = ExifInterface(imagePath)
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotate(270f)
            else -> this
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return this
    }
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degrees)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.resize(maxWidth: Int, maxHeight: Int): Bitmap {
    val width = this.width
    val height = this.height

    val scaleWidth = maxWidth.toFloat() / width
    val scaleHeight = maxHeight.toFloat() / height
    val scale = minOf(scaleWidth, scaleHeight)

    val newWidth = (width * scale).toInt()
    val newHeight = (height * scale).toInt()

    return Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
}

fun Bitmap.toByteArray(quality: Int = 90): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}

fun ByteArray.toBitmap(): Bitmap? {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}