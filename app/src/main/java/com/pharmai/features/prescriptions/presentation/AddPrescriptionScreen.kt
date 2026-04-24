package com.pharmai.features.prescriptions.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPrescriptionScreen(navController: NavHostController, viewModel: AddPrescriptionViewModel = hiltViewModel()) {
    var doctorName by remember { mutableStateOf("") }
    var drugName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Prescription") },
                navigationIcon = { IconButton(onClick = { navController.navigateUp() }) { Icon(Icons.Default.ArrowBack, null) } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Save */ }) {
                Icon(Icons.Default.Check, "Save")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(value = doctorName, onValueChange = { doctorName = it }, label = { Text("Doctor Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = drugName, onValueChange = { drugName = it }, label = { Text("Medicine Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = dosage, onValueChange = { dosage = it }, label = { Text("Dosage") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = frequency, onValueChange = { frequency = it }, label = { Text("Frequency") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = instructions, onValueChange = { instructions = it }, label = { Text("Instructions") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
        }
    }
}

@HiltViewModel
class AddPrescriptionViewModel @Inject constructor() : androidx.lifecycle.ViewModel()