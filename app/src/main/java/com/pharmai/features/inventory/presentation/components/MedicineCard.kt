package com.pharmai.features.inventory.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pharmai.features.inventory.domain.models.ExpiryStatus
import com.pharmai.features.inventory.domain.models.Medicine
import com.pharmai.features.inventory.presentation.components.ExpiryBadge

@Composable
fun MedicineCard(medicine: Medicine, onDelete: () -> Unit, modifier: Modifier = Modifier) {
    val statusColor = when (medicine.status) {
        ExpiryStatus.EXPIRED -> MaterialTheme.colorScheme.error
        ExpiryStatus.EXPIRING_SOON -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }

    Card(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(2.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(4.dp).height(40.dp).clip(RoundedCornerShape(2.dp)).background(statusColor))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(medicine.name, style = MaterialTheme.typography.titleSmall)
                Text("Qty: ${medicine.quantity} ${medicine.unit}", style = MaterialTheme.typography.bodySmall)
                ExpiryBadge(status = medicine.status, daysLeft = medicine.daysUntilExpiry)
            }
            IconButton(onClick = onDelete) { Icon(Icons.Outlined.Delete, null) }
        }
    }
}