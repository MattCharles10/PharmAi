package com.pharmai.features.scanner.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pharmai.core.navigation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanResultScreen(navController: NavHostController, scanId: String, viewModel: ScannerViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan Result") },
                navigationIcon = { IconButton(onClick = { navController.navigateUp() }) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        state.scanResult?.let { result ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding).padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.size(100.dp).background(MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(50.dp), tint = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("Medicine Identified!", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(result.medicineName ?: "Unknown", style = MaterialTheme.typography.bodyLarge)
                result.confidence.let { Text("Confidence: ${(it * 100).toInt()}%", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { navController.navigate(Destination.Inventory.route) { popUpTo(Destination.Home.route) } },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("Add to Inventory") }
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(onClick = { navController.navigateUp() }) { Text("Scan Again") }
            }
        }
    }
}