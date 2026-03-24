// app/src/main/java/com/pharmai/core/common/extensions/BitmapExtensions.kt
package com.pharmai.core.common.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun Bitmap.resize(maxWidth: Int, maxHeight: Int): Bitmap {
    val width = this.width
    val height = this.height

    val scaleWidth = maxWidth.toFloat() / width
    val scaleHeight = maxHeight.toFloat() / height
    val scale = minOf(scaleWidth, scaleHeight)

    val matrix = Matrix()
    matrix.postScale(scale, scale)

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.toByteArray(quality: Int = 90): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degrees)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.getOrientation(filePath: String): Int {
    val exif = ExifInterface(filePath)
    val orientation = exif.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        else -> 0
    }
}

fun File.saveBitmap(bitmap: Bitmap, quality: Int = 90): Boolean {
    return try {
        FileOutputStream(this).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
        }
        true
    } catch (e: Exception) {
        false
    }
}

fun Bitmap.toFile(file: File, quality: Int = 90): File {
    file.saveBitmap(this, quality)
    return file
}