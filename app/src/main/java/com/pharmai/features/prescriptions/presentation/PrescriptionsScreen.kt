package com.pharmai.features.prescriptions.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pharmai.core.navigation.Destination
import com.pharmai.features.prescriptions.presentation.components.PrescriptionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionsScreen(
    navController: NavHostController,
    viewModel: PrescriptionViewModel = hiltViewModel()
) {
    val prescriptions by viewModel.prescriptions.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF5F7FA),
        topBar = {
            TopAppBar(title = { Text("Prescriptions") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Destination.AddPrescription.route) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        if (prescriptions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    "No prescriptions found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(prescriptions) { prescription ->
                    PrescriptionCard(prescription = prescription)
                }
            }
        }
    }
}