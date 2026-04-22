package com.pharmai.core.common.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.pharmai.MainActivity
import com.pharmai.core.Constants

class NotificationUtils(private val context: Context) {

    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)

            val reminderChannel = NotificationChannel(
                Constants.REMINDER_CHANNEL_ID,
                Constants.REMINDER_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for medicine reminders"
                enableLights(true)
                enableVibration(true)
            }

            val expiryChannel = NotificationChannel(
                Constants.EXPIRY_CHANNEL_ID,
                Constants.EXPIRY_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for medicine expiry alerts"
            }

            notificationManager?.createNotificationChannels(listOf(reminderChannel, expiryChannel))
        }
    }

    fun showReminderNotification(
        title: String,
        message: String,
        notificationId: Int = Constants.NOTIFICATION_ID_REMINDER
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

        val notification = NotificationCompat.Builder(context, Constants.REMINDER_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager?.notify(notificationId, notification)
    }
}