package com.pharmai.features.search.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrugDetailScreen(navController: NavHostController, drugId: String, viewModel: DrugDetailViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(drugId) { viewModel.handleEvent(DrugDetailEvent.LoadDrug(drugId)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.drug?.name ?: "Drug Details") },
                navigationIcon = { IconButton(onClick = { navController.navigateUp() }) { Icon(Icons.Default.ArrowBack, null) } },
                actions = {
                    state.drug?.let { drug ->
                        IconButton(onClick = { viewModel.handleEvent(DrugDetailEvent.ToggleFavorite(drug.id)) }) {
                            Icon(if (drug.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, null)
                        }
                    }
                }
            )
        }
    ) { padding ->
        state.drug?.let { drug ->
            Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp).verticalScroll(rememberScrollState())) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(drug.name, style = MaterialTheme.typography.headlineSmall)
                        drug.genericName?.let { Text(it, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                        drug.manufacturer?.let { Text("Manufacturer: $it", style = MaterialTheme.typography.bodyMedium) }
                        drug.description?.let { Text("Description: $it", style = MaterialTheme.typography.bodyMedium) }
                    }
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}