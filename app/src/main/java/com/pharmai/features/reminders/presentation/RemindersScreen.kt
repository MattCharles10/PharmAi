package com.pharmai.features.reminders.presentation

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
import com.pharmai.core.navigation.Destination
import com.pharmai.features.reminders.presentation.components.ReminderCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(navController: NavHostController, viewModel: ReminderViewModel = hiltViewModel()) {
    val reminders by viewModel.reminders.collectAsState()

    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color(0xFFF5F7FA),
        topBar = { TopAppBar(title = { Text("Reminders") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Destination.CreateReminder.createRoute()) }) {
                Icon(Icons.Default.Add, "Add")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(reminders) { reminder ->
                ReminderCard(
                    reminder = reminder,
                    onToggle = { viewModel.handleEvent(ReminderEvent.ToggleReminder(reminder.id, !reminder.isActive)) },
                    onDelete = { viewModel.handleEvent(ReminderEvent.DeleteReminder(reminder.id)) }
                )
            }
        }
    }
}