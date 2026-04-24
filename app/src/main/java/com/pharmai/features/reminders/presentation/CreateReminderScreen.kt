package com.pharmai.features.reminders.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import javax.inject.Inject
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.navigation.NavHostController
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReminderScreen(
    navController: NavHostController,
    medicineId: Long? = null,
    viewModel: CreateReminderViewModel = hiltViewModel()
) {
    var medicineName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var selectedHour by remember { mutableStateOf(9) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedDays by remember { mutableStateOf(setOf<Int>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Reminder") },
                navigationIcon = { IconButton(onClick = { navController.navigateUp() }) { Icon(Icons.Default.ArrowBack, null) } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Save reminder */ }) {
                Icon(Icons.Default.Check, "Save")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(value = medicineName, onValueChange = { medicineName = it }, label = { Text("Medicine Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = dosage, onValueChange = { dosage = it }, label = { Text("Dosage") }, modifier = Modifier.fillMaxWidth())

            Text("Time", style = MaterialTheme.typography.titleMedium)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                NumberPicker(value = selectedHour, onValueChange = { selectedHour = it }, range = 0..23)
                Text(":", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.align(androidx.compose.ui.Alignment.CenterVertically))
                NumberPicker(value = selectedMinute, onValueChange = { selectedMinute = it }, range = 0..59)
            }

            Text("Repeat", style = MaterialTheme.typography.titleMedium)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEachIndexed { index, day ->
                    FilterChip(
                        selected = selectedDays.contains(index + 1),
                        onClick = {
                            selectedDays = if (selectedDays.contains(index + 1)) selectedDays - (index + 1) else selectedDays + (index + 1)
                        },
                        label = { Text(day) }
                    )
                }
            }
        }
    }
}

@Composable
fun NumberPicker(value: Int, onValueChange: (Int) -> Unit, range: IntRange) {
    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        IconButton(onClick = { if (value < range.last) onValueChange(value + 1) }) {
            Icon(Icons.Default.KeyboardArrowUp, null)
        }
        Text(String.format("%02d", value), style = MaterialTheme.typography.headlineMedium)
        IconButton(onClick = { if (value > range.first) onValueChange(value - 1) }) {
            Icon(Icons.Default.KeyboardArrowDown, null)
        }
    }
}

@HiltViewModel
class CreateReminderViewModel @Inject constructor() : androidx.lifecycle.ViewModel()