package com.pharmai.core.utils

object Constants {
    const val BASE_URL = "https://api.fda.gov/"
    const val RXNAV_BASE_URL = "https://rxnav.nlm.nih.gov/"
    const val DATABASE_NAME = "pharmai_database"
    const val PREF_NAME = "pharmai_prefs"
    const val NETWORK_TIMEOUT = 30L
    const val PAGE_SIZE = 20

    const val PILL_MODEL = "pill_classifier.tflite"
    const val CONFIDENCE_THRESHOLD = 0.7f
    const val MODEL_INPUT_SIZE = 224

    const val REMINDER_CHANNEL_ID = "reminder_channel"
    const val REMINDER_CHANNEL_NAME = "Medicine Reminders"
    const val EXPIRY_CHANNEL_ID = "expiry_channel"

    const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
    const val KEY_DARK_MODE = "dark_mode"
    const val KEY_EXPIRY_ALERT_DAYS = "expiry_alert_days"
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}