package com.pharmai.features.profile.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var biometricEnabled by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }
    var expiryAlertDays by remember { mutableStateOf(30) }
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color(0xFFF5F7FA),
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Notifications Section
            SettingsSection(title = "Notifications") {
                SettingsToggle(
                    title = "Push Notifications",
                    subtitle = "Receive medicine reminders",
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
                Divider()
                SettingsToggle(
                    title = "Sound",
                    subtitle = "Play sound for reminders",
                    checked = soundEnabled,
                    onCheckedChange = { soundEnabled = it }
                )
                Divider()
                SettingsToggle(
                    title = "Vibration",
                    subtitle = "Vibrate for reminders",
                    checked = vibrationEnabled,
                    onCheckedChange = { vibrationEnabled = it }
                )
            }

            // Security Section
            SettingsSection(title = "Security") {
                SettingsToggle(
                    title = "Biometric Authentication",
                    subtitle = "Use fingerprint or face unlock",
                    checked = biometricEnabled,
                    onCheckedChange = { biometricEnabled = it }
                )
            }

            // Appearance Section
            SettingsSection(title = "Appearance") {
                SettingsToggle(
                    title = "Dark Mode",
                    subtitle = "Switch to dark theme",
                    checked = darkMode,
                    onCheckedChange = { darkMode = it }
                )
            }

            // Medicine Alerts Section
            SettingsSection(title = "Medicine Alerts") {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Expiry Alert Days",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Get alerts $expiryAlertDays days before medicine expires",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        value = expiryAlertDays.toFloat(),
                        onValueChange = { expiryAlertDays = it.toInt() },
                        valueRange = 7f..90f,
                        steps = 82,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("7 days", style = MaterialTheme.typography.labelSmall)
                        Text("90 days", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            // Data Section
            SettingsSection(title = "Data & Storage") {
                SettingsClickable(
                    title = "Clear Search History",
                    subtitle = "Remove all recent searches",
                    onClick = { /* Clear history */ }
                )
                Divider()
                SettingsClickable(
                    title = "Clear Scan History",
                    subtitle = "Remove all scan records",
                    onClick = { /* Clear scans */ }
                )
                Divider()
                SettingsClickable(
                    title = "Export Data",
                    subtitle = "Download your medicine data",
                    onClick = { /* Export */ }
                )
            }

            // About Section
            SettingsSection(title = "About") {
                SettingsClickable(
                    title = "Rate App",
                    subtitle = "Share your feedback on Play Store",
                    onClick = { /* Rate */ }
                )
                Divider()
                SettingsClickable(
                    title = "Privacy Policy",
                    subtitle = "How we handle your data",
                    onClick = { /* Privacy */ }
                )
                Divider()
                SettingsClickable(
                    title = "Terms of Service",
                    subtitle = "Terms and conditions",
                    onClick = { /* Terms */ }
                )
                Divider()
                SettingsInfo(
                    title = "App Version",
                    value = "1.0.0"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(vertical = 4.dp)
    )
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(content = content)
    }
}

@Composable
fun SettingsToggle(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingsClickable(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SettingsInfo(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall)
        Text(value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}