package com.pharmai.features.reminders.data

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pharmai.core.utils.NotificationUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val notificationUtils: NotificationUtils
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val medicineName = inputData.getString("medicine_name") ?: "Medicine"
        val dosage = inputData.getString("dosage") ?: ""

        // Use the correct method name from NotificationUtils
        notificationUtils.showReminderNotification(
            title = "Time to take $medicineName",
            message = "Take $dosage of $medicineName"
        )

        return Result.success()
    }
}