package com.pharmai.features.home.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pharmai.core.theme.LocalSpacing

@Composable
fun WelcomeSection(userName: String, modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier) {
        Text("Hello, $userName! 👋", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(spacing.xs))
        Text("Ready to manage your health today?", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}