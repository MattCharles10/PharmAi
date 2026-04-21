package com.pharmai.ml

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TFLiteEngine(private val context: Context) {
    private var interpreter: Interpreter? = null

    fun loadModel(modelPath: String): Boolean = try {
        interpreter = Interpreter(FileUtil.loadMappedFile(context, modelPath))
        true
    } catch (e: Exception) { false }

    fun runInference(input: ByteBuffer): FloatArray {
        val output = Array(1) { FloatArray(1000) }
        interpreter?.run(input, output)
        return output[0]
    }

    fun getInputSize() = 224
    fun close() { interpreter?.close(); interpreter = null }

    companion object {
        fun bitmapToByteBuffer(bitmap: Bitmap, inputSize: Int): ByteBuffer {
            val buffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3)
            buffer.order(ByteOrder.nativeOrder())
            val pixels = IntArray(inputSize * inputSize)
            Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true).getPixels(pixels, 0, inputSize, 0, 0, inputSize, inputSize)
            for (pixel in pixels) {
                buffer.putFloat(((pixel shr 16) and 0xFF) / 255.0f)
                buffer.putFloat(((pixel shr 8) and 0xFF) / 255.0f)
                buffer.putFloat((pixel and 0xFF) / 255.0f)
            }
            return buffer
        }
    }
}