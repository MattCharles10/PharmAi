package com.pharmai.features.inventory.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pharmai.core.navigation.PharmAiBottomNavBar
import com.pharmai.features.inventory.presentation.components.MedicineCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(navController: NavHostController, viewModel: InventoryViewModel = hiltViewModel()) {
    val medicines by viewModel.medicines.collectAsState()

    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color(0xFFF5F7FA),
        topBar = { TopAppBar(title = { Text("My Inventory") }) },
        floatingActionButton = { FloatingActionButton(onClick = { /* Navigate to add */ }) { Icon(Icons.Default.Add, "Add") } },
        bottomBar = { PharmAiBottomNavBar(navController) }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(medicines) { medicine ->
                MedicineCard(medicine = medicine, onDelete = { viewModel.handleEvent(InventoryEvent.DeleteMedicine(medicine.id)) })
            }
        }
    }
}