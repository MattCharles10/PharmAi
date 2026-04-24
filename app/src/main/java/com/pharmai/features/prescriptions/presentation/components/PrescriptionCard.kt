package com.pharmai.features.prescriptions.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pharmai.core.utils.DateUtils
import com.pharmai.features.prescriptions.domain.models.Prescription

@Composable
fun PrescriptionCard(prescription: Prescription, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(prescription.drugName, style = MaterialTheme.typography.titleMedium)
            Text(prescription.dosage, style = MaterialTheme.typography.bodyMedium)
            Text(prescription.frequency, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            prescription.doctorName?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Dr. $it", style = MaterialTheme.typography.bodySmall)
            }
            Text("Prescribed: ${DateUtils.formatDate(prescription.prescriptionDate)}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}