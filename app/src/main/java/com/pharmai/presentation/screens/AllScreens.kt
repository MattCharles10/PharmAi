package com.pharmai.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pharmai.core.Navigation
import com.pharmai.domain.models.*
import com.pharmai.presentation.components.*
import com.pharmai.presentation.viewmodel.*

// Home Screen
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val expiringItems by viewModel.expiringItems.collectAsState()
    val reminders by viewModel.todayReminders.collectAsState()

    Scaffold(bottomBar = { Navigation.BottomNavigationBar(navController) }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item { Text("Quick Actions", style = MaterialTheme.typography.titleLarge) }
            if (expiringItems.isNotEmpty()) {
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("⚠️ Expiring Soon", style = MaterialTheme.typography.titleMedium)
                            expiringItems.take(3).forEach { Text("${it.drugName} - ${it.daysUntilExpiry} days left") }
                        }
                    }
                }
            }
            item { Text("Today's Reminders", style = MaterialTheme.typography.titleLarge) }
            if (reminders.isEmpty()) item { Text("No reminders") }
            else items(reminders) { reminder -> ReminderCard(reminder) }
        }
    }
}

// Search Screen
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()

    Scaffold(bottomBar = { Navigation.BottomNavigationBar(navController) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Search medicines...") }, modifier = Modifier.fillMaxWidth().padding(16.dp), singleLine = true)
            Button(onClick = { viewModel.search(query) }, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) { Text("Search") }
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(searchResults) { drug -> DrugCard(drug) { navController.navigate(Navigation.Screen.DrugDetail.createRoute(drug.id)) } }
            }
        }
    }
}

// Inventory Screen
@Composable
fun InventoryScreen(navController: NavHostController, viewModel: InventoryViewModel = hiltViewModel()) {
    val items by viewModel.items.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text("My Inventory") }) }, floatingActionButton = { FloatingActionButton(onClick = { }) { Icon(Icons.Default.Add, "Add") } }, bottomBar = { Navigation.BottomNavigationBar(navController) }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items) { item -> InventoryCard(item, onDelete = { viewModel.deleteItem(item.id) }) }
        }
    }
}

// Camera Screen
@Composable
fun CameraScreen(navController: NavHostController, viewModel: CameraViewModel = hiltViewModel()) {
    Scaffold(bottomBar = { Navigation.BottomNavigationBar(navController) }) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(64.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /* Open camera */ }) { Text("Scan Medicine") }
                Button(onClick = { navController.navigate(Navigation.Screen.ScanResult.createRoute("test")) }) { Text("Test Result") }
            }
        }
    }
}

// Profile Screen
@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(bottomBar = { Navigation.BottomNavigationBar(navController) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Profile", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = { navController.navigate(Navigation.Screen.Reminders.route) }) { Text("Reminders") }
        }
    }
}

// Reminder Screen
@Composable
fun ReminderScreen(navController: NavHostController, viewModel: ReminderViewModel = hiltViewModel()) {
    val reminders by viewModel.reminders.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text("Reminders") }) }, floatingActionButton = { FloatingActionButton(onClick = { }) { Icon(Icons.Default.Add, "Add") } }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) { items(reminders) { reminder -> ReminderCard(reminder) } }
    }
}

// Drug Detail Screen
@Composable
fun DrugDetailScreen(navController: NavHostController, drugId: String) {
    Scaffold(topBar = { TopAppBar(title = { Text("Drug Details") }, navigationIcon = { IconButton(onClick = { navController.navigateUp() }) { Icon(Icons.Default.ArrowBack, "Back") } }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) { Text("Drug ID: $drugId", style = MaterialTheme.typography.bodyLarge) }
    }
}

// Scan Result Screen
@Composable
fun ScanResultScreen(navController: NavHostController, scanId: String) {
    Scaffold(topBar = { TopAppBar(title = { Text("Scan Result") }, navigationIcon = { IconButton(onClick = { navController.navigateUp() }) { Icon(Icons.Default.ArrowBack, "Back") } }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Medicine Identified!", style = MaterialTheme.typography.headlineSmall)
            Text("Paracetamol 500mg", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { navController.navigate(Navigation.Screen.Inventory.route) { popUpTo(Navigation.Screen.Home.route) } }) { Text("Add to Inventory") }
            TextButton(onClick = { navController.navigateUp() }) { Text("Scan Again") }
        }
    }
}

// Cards
@Composable
fun DrugCard(drug: Drug, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(drug.name, style = MaterialTheme.typography.titleMedium)
            drug.genericName?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
        }
    }
}

@Composable
fun InventoryCard(item: UserInventory, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = when (item.status) {
        ExpiryStatus.EXPIRED -> MaterialTheme.colorScheme.errorContainer
        ExpiryStatus.EXPIRING_SOON -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.surface
    })) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.drugName, style = MaterialTheme.typography.titleMedium)
                Text("Qty: ${item.quantity} ${item.unit}")
            }
            IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, "Delete") }
        }
    }
}

@Composable
fun ReminderCard(reminder: Reminder) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column { Text(reminder.medicineName, style = MaterialTheme.typography.titleMedium); Text(reminder.dosage) }
            Text(reminder.toTimeString(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}