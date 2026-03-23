package com.pharmai.core.common

object Constants {

    // API Endpoints
    const val BASE_URL = "https://api.fda.gov/"
    const val RXNAV_BASE_URL = "https://rxnav.nlm.nih.gov/"
    const val DAILYMED_BASE_URL = "https://dailymed.nlm.nih.gov/dailymed/"

    // API Keys (Add your keys here)
    object ApiKeys {
        const val OPENFDA_API_KEY = "" // Add your key if required
        const val DAILYMED_API_KEY = "" // Add if required
    }

    // Database
    const val DATABASE_NAME = "pharmai_database"
    const val DATABASE_VERSION = 1

    // Preferences
    const val PREF_NAME = "pharmai_preferences"
    const val KEY_FIRST_TIME_USER = "first_time_user"
    const val KEY_USER_ID = "user_id"
    const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
    const val KEY_DARK_MODE = "dark_mode"
    const val KEY_REMINDER_ENABLED = "reminder_enabled"
    const val KEY_EXPIRY_ALERT_DAYS = "expiry_alert_days"

    // Pagination
    const val PAGE_SIZE = 20

    // Timeouts
    const val NETWORK_TIMEOUT = 30L // seconds

    // ML Model paths
    const val PILL_CLASSIFIER_MODEL = "pill_classifier.tflite"
    const val PACKAGE_CLASSIFIER_MODEL = "package_classifier.tflite"
    const val TEXT_DETECTOR_MODEL = "text_detector.tflite"
    const val LABELS_PATH = "labels/"

    // Notification
    const val REMINDER_CHANNEL_ID = "reminder_channel"
    const val REMINDER_CHANNEL_NAME = "Medicine Reminders"
    const val EXPIRY_CHANNEL_ID = "expiry_channel"
    const val EXPIRY_CHANNEL_NAME = "Expiry Alerts"
    const val NOTIFICATION_ID_REMINDER = 1001
    const val NOTIFICATION_ID_EXPIRY = 1002

    // Cache
    const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB
    const val CACHE_MAX_AGE = 7 // days
}