package com.pharmai.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.pharmai.core.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREF_NAME)

class PreferencesManager @Inject constructor(private val context: Context) {
    private val biometricEnabled = booleanPreferencesKey(Constants.KEY_BIOMETRIC_ENABLED)
    private val darkMode = booleanPreferencesKey(Constants.KEY_DARK_MODE)
    private val expiryAlertDays = intPreferencesKey(Constants.KEY_EXPIRY_ALERT_DAYS)

    val isBiometricEnabled: Flow<Boolean> = context.dataStore.data.map { it[biometricEnabled] ?: false }
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { it[darkMode] ?: false }
    val expiryAlertDaysFlow: Flow<Int> = context.dataStore.data.map { it[expiryAlertDays] ?: 30 }

    suspend fun setBiometricEnabled(enabled: Boolean) { context.dataStore.edit { it[biometricEnabled] = enabled } }
    suspend fun setDarkMode(enabled: Boolean) { context.dataStore.edit { it[darkMode] = enabled } }
    suspend fun setExpiryAlertDays(days: Int) { context.dataStore.edit { it[expiryAlertDays] = days } }
}