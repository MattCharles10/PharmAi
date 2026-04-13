package com.pharmai.core.common.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.resize(width: Int, height: Int): Bitmap {
    return Bitmap.createScaledBitmap(this, width, height, true)
}

fun Bitmap.centerCrop(targetWidth: Int, targetHeight: Int): Bitmap {
    val cropWidth: Int
    val cropHeight: Int

    if (width.toFloat() / height > targetWidth.toFloat() / targetHeight) {
        cropHeight = height
        cropWidth = (height * targetWidth.toFloat() / targetHeight).toInt()
    } else {
        cropWidth = width
        cropHeight = (width * targetHeight.toFloat() / targetWidth).toInt()
    }

    val startX = (width - cropWidth) / 2
    val startY = (height - cropHeight) / 2

    return Bitmap.createBitmap(this, startX, startY, cropWidth, cropHeight)
        .resize(targetWidth, targetHeight)
}

fun Bitmap.toByteArray(quality: Int = 90): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}

fun Bitmap.saveToFile(file: File, quality: Int = 90): Boolean {
    return try {
        FileOutputStream(file).use { out ->
            compress(Bitmap.CompressFormat.JPEG, quality, out)
        }
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun File.toBitmap(): Bitmap? {
    return try {
        val options = BitmapFactory.Options().apply {
            inPreferredConfig = Bitmap.Config.ARGB_8888
        }
        BitmapFactory.decodeFile(absolutePath, options)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Bitmap.getOrientation(filePath: String): Int {
    return try {
        val exif = ExifInterface(filePath)
        when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
    } catch (e: Exception) {
        0
    }
}