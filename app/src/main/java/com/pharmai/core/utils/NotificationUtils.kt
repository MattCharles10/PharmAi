package com.pharmai.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.pharmai.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val REMINDER_CHANNEL_ID = "reminder_channel"
        const val REMINDER_CHANNEL_NAME = "Medicine Reminders"
        const val EXPIRY_CHANNEL_ID = "expiry_channel"
        const val EXPIRY_CHANNEL_NAME = "Expiry Alerts"
        const val NOTIFICATION_ID_REMINDER = 1001
        const val NOTIFICATION_ID_EXPIRY = 1002
    }

    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)

            val reminderChannel = NotificationChannel(
                REMINDER_CHANNEL_ID,
                REMINDER_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for medicine reminders"
                enableLights(true)
                enableVibration(true)
            }

            val expiryChannel = NotificationChannel(
                EXPIRY_CHANNEL_ID,
                EXPIRY_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for medicine expiry alerts"
            }

            notificationManager.createNotificationChannels(listOf(reminderChannel, expiryChannel))
        }
    }

    fun showReminderNotification(
        title: String,
        message: String,
        notificationId: Int = NOTIFICATION_ID_REMINDER
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId, notification)
    }

    fun showExpiryNotification(medicineName: String, daysLeft: Int) {
        val title = "Medicine Expiry Alert"
        val message = when {
            daysLeft < 0 -> "$medicineName has expired!"
            daysLeft == 0 -> "$medicineName expires today!"
            daysLeft == 1 -> "$medicineName expires tomorrow!"
            else -> "$medicineName expires in $daysLeft days"
        }
        showReminderNotification(title, message, NOTIFICATION_ID_EXPIRY)
    }

    fun cancelNotification(notificationId: Int) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.cancel(notificationId)
    }
}