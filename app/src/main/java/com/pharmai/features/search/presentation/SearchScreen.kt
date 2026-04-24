package com.pharmai.features.search.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pharmai.core.navigation.Destination
import com.pharmai.core.navigation.PharmAiBottomNavBar
import com.pharmai.features.search.presentation.components.DrugCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color(0xFFF5F7FA),
        bottomBar = { PharmAiBottomNavBar(navController) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(2.dp)) {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(8.dp))
                    androidx.compose.foundation.text.BasicTextField(
                        value = query, onValueChange = { query = it },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f),
                        decorationBox = { if (query.isEmpty()) Text("Search medicines...", color = MaterialTheme.colorScheme.onSurfaceVariant) else it() }
                    )
                    if (query.isNotEmpty()) IconButton(onClick = { query = "" }) { Icon(Icons.Default.Clear, null) }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.handleEvent(SearchEvent.Search(query)) }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(12.dp)) {
                Text("Search")
            }
            Spacer(modifier = Modifier.height(16.dp))
            when {
                state.isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                state.error != null -> Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                state.results.isEmpty() && query.isNotEmpty() -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No results found") }
                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.results) { drug -> DrugCard(drug) { navController.navigate(Destination.DrugDetail.createRoute(drug.id)) } }
                }
            }
        }
    }
}