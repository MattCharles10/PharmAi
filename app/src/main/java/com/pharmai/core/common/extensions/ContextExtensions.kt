package com.pharmai.core.common.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showToast(@StringRes messageRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, messageRes, duration).show()
}

fun Context.getBiometricExecutor(): Executor {
    return ContextCompat.getMainExecutor(this)
}

fun Context.isBiometricAvailable(): Boolean {
    val biometricManager = BiometricManager.from(this)
    return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
}

fun FragmentActivity.showBiometricPrompt(
    title: String,
    subtitle: String,
    description: String,
    onSuccess: () -> Unit,
    onError: (Int, String) -> Unit
) {
    val executor = ContextCompat.getMainExecutor(this)
    val callback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            onSuccess()
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            onError(errorCode, errString.toString())
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            onError(-1, "Authentication failed")
        }
    }

    val biometricPrompt = BiometricPrompt(this, executor, callback)

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(title)
        .setSubtitle(subtitle)
        .setDescription(description)
        .setNegativeButtonText("Cancel")
        .build()

    biometricPrompt.authenticate(promptInfo)
}