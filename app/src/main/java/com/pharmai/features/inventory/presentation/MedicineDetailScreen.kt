package com.pharmai.features.inventory.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import com.pharmai.features.inventory.domain.models.Medicine
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailScreen(
    medicineId: Long,
    navController: NavHostController,
    viewModel: MedicineDetailViewModel = hiltViewModel()
) {
    val medicine = Medicine(
        id = medicineId,
        drugId = "1",
        name = "Paracetamol",
        genericName = "Acetaminophen",
        quantity = 30,
        unit = "tablets",
        expiryDate = java.util.Date()
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(medicine.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Edit */ }) {
                        Icon(Icons.Default.Edit, "Edit")
                    }
                    IconButton(onClick = {
                        // Delete and go back
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Default.Delete, "Delete")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow("Generic Name", medicine.genericName)
                    DetailRow("Quantity", "${medicine.quantity} ${medicine.unit}")
                    DetailRow("Status", medicine.status.name)
                    medicine.batchNumber?.let { DetailRow("Batch Number", it) }
                    medicine.notes?.let { DetailRow("Notes", it) }
                }
            }

            Button(
                onClick = { navController.navigate("create_reminder/$medicineId") },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Set Reminder")
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String?) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(100.dp)
        )
        Text(
            value ?: "N/A",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@HiltViewModel
class MedicineDetailViewModel @Inject constructor() : androidx.lifecycle.ViewModel()