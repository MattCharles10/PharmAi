package com.pharmai.ml

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate
import org.tensorflow.lite.support.common.FileUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TFLiteEngine(private val context: Context) {

    private var interpreter: Interpreter? = null
    private var gpuDelegate: GpuDelegate? = null

    fun loadModel(modelPath: String, useGpu: Boolean = true): Boolean {
        return try {
            val modelFile = FileUtil.loadMappedFile(context, modelPath)
            val options = Interpreter.Options()

            if (useGpu && CompatibilityList().isDelegateSupportedOnThisDevice) {
                gpuDelegate = GpuDelegate()
                options.addDelegate(gpuDelegate)
            } else {
                options.numThreads = 4
            }

            interpreter = Interpreter(modelFile, options)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun runInference(input: ByteBuffer): FloatArray {
        val outputSize = getOutputSize()
        val output = Array(1) { FloatArray(outputSize) }
        interpreter?.run(input, output)
        return output[0]
    }

    fun getInputSize(): Int {
        return interpreter?.getInputTensor(0)?.shape()?.getOrNull(1) ?: 224
    }

    fun getOutputSize(): Int {
        return interpreter?.getOutputTensor(0)?.shape()?.getOrNull(1) ?: 1000
    }

    fun close() {
        interpreter?.close()
        gpuDelegate?.close()
        interpreter = null
        gpuDelegate = null
    }

    companion object {
        fun bitmapToByteBuffer(bitmap: Bitmap, inputSize: Int): ByteBuffer {
            val buffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3)
            buffer.order(ByteOrder.nativeOrder())

            val pixels = IntArray(inputSize * inputSize)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
            scaledBitmap.getPixels(pixels, 0, inputSize, 0, 0, inputSize, inputSize)

            for (pixel in pixels) {
                buffer.putFloat(((pixel shr 16) and 0xFF) / 255.0f)
                buffer.putFloat(((pixel shr 8) and 0xFF) / 255.0f)
                buffer.putFloat((pixel and 0xFF) / 255.0f)
            }
            return buffer
        }
    }
}