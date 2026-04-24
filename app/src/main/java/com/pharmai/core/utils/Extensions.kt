package com.pharmai.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context.dpToPx(dp: Float) = dp * resources.displayMetrics.density

fun Modifier.noRippleClickable(onClick: () -> Unit) = composed {
    clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) { onClick() }
}

fun Bitmap.resize(w: Int, h: Int) = Bitmap.createScaledBitmap(this, w, h, true)

fun Bitmap.centerCrop(targetW: Int, targetH: Int): Bitmap {
    val cropW = if (width.toFloat() / height > targetW.toFloat() / targetH)
        (height * targetW.toFloat() / targetH).toInt() else width
    val cropH = if (width.toFloat() / height > targetW.toFloat() / targetH)
        height else (width * targetH.toFloat() / targetW).toInt()
    return Bitmap.createBitmap(this, (width - cropW) / 2, (height - cropH) / 2, cropW, cropH).resize(targetW, targetH)
}

fun Bitmap.toByteArray(quality: Int = 90): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}

fun File.toBitmap(): Bitmap? = try { BitmapFactory.decodeFile(absolutePath) } catch (e: Exception) { null }