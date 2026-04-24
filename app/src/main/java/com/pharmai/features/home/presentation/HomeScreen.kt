package com.pharmai.features.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pharmai.core.navigation.Destination
import com.pharmai.core.navigation.PharmAiBottomNavBar
import com.pharmai.core.theme.LocalSpacing
import com.pharmai.features.home.presentation.components.ExpiryAlertCard
import com.pharmai.features.home.presentation.components.QuickActionsRow
import com.pharmai.features.home.presentation.components.WelcomeSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val spacing = LocalSpacing.current
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF5F7FA),
        bottomBar = { PharmAiBottomNavBar(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = spacing.md),
            verticalArrangement = Arrangement.spacedBy(spacing.md)
        ) {
            item { WelcomeSection(userName = "Sarah") }
            item {
                QuickActionsRow(
                    onScan = { navController.navigate(Destination.Scanner.route) },
                    onSearch = { navController.navigate(Destination.Search.route) },
                    onInventory = { navController.navigate(Destination.Inventory.route) },
                    onReminders = { navController.navigate(Destination.Reminders.route) }
                )
            }
            if (state.expiringMedicines.isNotEmpty()) {
                item { ExpiryAlertCard(items = state.expiringMedicines) }
            }
            item {
                Text("Today's Schedule", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp))
            }
            if (state.todayReminders.isEmpty()) {
                item { Card(modifier = Modifier.fillMaxWidth()) { Text("No reminders", modifier = Modifier.padding(16.dp)) } }
            } else {
                items(state.todayReminders) { reminder ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column { Text(reminder.medicineName, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold); Text(reminder.dosage) }
                            Text(reminder.time, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}