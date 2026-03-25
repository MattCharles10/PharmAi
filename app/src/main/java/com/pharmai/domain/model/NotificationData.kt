package com.pharmai.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class NotificationData(
    val id: Int,
    val userId: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val relatedId: String? = null,
    val isRead: Boolean = false,
    val createdAt: Date = Date()
) : Parcelable

@Parcelize
enum class NotificationType : Parcelable {
    REMINDER,
    EXPIRY_ALERT,
    REFILL_REMINDER,
    MEDICATION_INFO,
    GENERAL
}