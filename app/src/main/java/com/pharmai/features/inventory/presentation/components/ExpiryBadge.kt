package com.pharmai.features.inventory.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pharmai.features.inventory.domain.models.ExpiryStatus

@Composable
fun ExpiryBadge(status: ExpiryStatus, daysLeft: Int, modifier: Modifier = Modifier) {
    val (text, color) = when (status) {
        ExpiryStatus.EXPIRED -> "Expired" to MaterialTheme.colorScheme.error
        ExpiryStatus.EXPIRING_SOON -> "$daysLeft days left" to MaterialTheme.colorScheme.tertiary
        ExpiryStatus.VALID -> "Valid" to MaterialTheme.colorScheme.primary
    }

    Box(modifier = modifier.clip(RoundedCornerShape(4.dp)).background(color.copy(alpha = 0.15f)).padding(horizontal = 8.dp, vertical = 2.dp)) {
        Text(text, style = MaterialTheme.typography.labelSmall, color = color)
    }
}