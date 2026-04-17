package com.pharmai.core


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.pharmai.MainActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

object DateUtils {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val displayFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    fun formatDate(date: Date) = displayFormat.format(date)
    fun formatTime(date: Date) = timeFormat.format(date)
    fun parseDate(str: String) = try { dateFormat.parse(str) } catch (e: Exception) { null }
    fun isExpired(date: Date) = date.before(Date())

    fun daysUntil(date: Date): Int {
        val diff = date.time - Date().time
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
    }

    fun isExpiringSoon(date: Date, threshold: Int = 30) = daysUntil(date) in 0..threshold
    fun addDays(date: Date, days: Int): Date {
        val cal = Calendar.getInstance().apply { time = date; add(Calendar.DAY_OF_YEAR, days) }
        return cal.time
    }
    fun getTodayStart(): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }
}

class NotificationUtils(private val context: Context) {
    fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannels(listOf(
                NotificationChannel(Constants.REMINDER_CHANNEL_ID, Constants.REMINDER_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH),
                NotificationChannel(Constants.EXPIRY_CHANNEL_ID, Constants.EXPIRY_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            ))
        }
    }

    fun showReminder(title: String, message: String, id: Int = 1001) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pending = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context, Constants.REMINDER_CHANNEL_ID)
            .setContentTitle(title).setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info).setContentIntent(pending).setAutoCancel(true).build()
        context.getSystemService(NotificationManager::class.java)?.notify(id, notification)
    }
}

class BiometricUtils(private val context: Context) {
    fun isAvailable(): Boolean {
        val manager = BiometricManager.from(context)
        return manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
    }

    suspend fun authenticate(activity: FragmentActivity): Boolean = suspendCancellableCoroutine { cont ->
        val executor = ContextCompat.getMainExecutor(context)
        val prompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) { cont.resume(true) }
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) { cont.resume(false) }
        })
        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authenticate").setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG).build()
        prompt.authenticate(info)
        cont.invokeOnCancellation { prompt.cancelAuthentication() }
    }
}