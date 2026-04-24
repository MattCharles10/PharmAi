package com.pharmai.features.scanner.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pharmai.core.utils.DateUtils
import com.pharmai.features.scanner.domain.models.ScanResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanHistoryScreen(navController: NavHostController, viewModel: ScannerViewModel = hiltViewModel()) {
    val scanHistory by viewModel.scanHistory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan History") },
                navigationIcon = { IconButton(onClick = { navController.navigateUp() }) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(scanHistory) { scan ->
                ScanHistoryCard(
                    scan = scan,
                    onDelete = { viewModel.handleEvent(ScannerEvent.DeleteScan(scan.id)) }
                )
            }
        }
    }
}

@Composable
fun ScanHistoryCard(scan: ScanResult, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(scan.medicineName ?: "Unknown", style = MaterialTheme.typography.titleSmall)
                Text("Confidence: ${(scan.confidence * 100).toInt()}%", style = MaterialTheme.typography.bodySmall)
                Text(DateUtils.formatDate(scan.scanDate), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, null)
            }
        }
    }
}