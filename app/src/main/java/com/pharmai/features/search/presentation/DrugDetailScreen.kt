package com.pharmai.features.search.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pharmai.features.search.domain.models.Drug

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrugDetailScreen(
    navController: NavHostController,
    drugId: String,
    viewModel: DrugDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var isVisible by remember { mutableStateOf(false) }

    // If no drug loaded yet, create a mock one for display
    val displayDrug = state.drug ?: Drug(
        id = drugId,
        name = drugId.replaceFirstChar { it.uppercase() },
        genericName = "Information loading...",
        manufacturer = "Loading...",
        description = "Loading drug information..."
    )

    LaunchedEffect(drugId) {
        viewModel.handleEvent(DrugDetailEvent.LoadDrug(drugId))
    }

    LaunchedEffect(Unit) { isVisible = true }

    Scaffold(
        containerColor = Color(0xFFF3E8FF),  // Purple theme
        topBar = {
            TopAppBar(
                title = { Text(displayDrug.name, fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.handleEvent(DrugDetailEvent.ToggleFavorite(drugId)) }) {
                        Icon(
                            if (displayDrug.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            "Favorite",
                            tint = if (displayDrug.isFavorite) Color(0xFFFF5252) else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF7C4DFF))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Drug Header Card
            AnimatedVisibility(isVisible, enter = fadeIn(tween(400)) + expandVertically()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.size(60.dp).clip(RoundedCornerShape(14.dp)).background(Color(0xFF7C4DFF).copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.Medication, null, Modifier.size(32.dp), tint = Color(0xFF7C4DFF))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(displayDrug.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
                                displayDrug.genericName?.let {
                                    Text(it, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF666666))
                                }
                            }
                        }
                    }
                }
            }

            // Loading indicator
            if (state.isLoading) {
                Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF7C4DFF))
                }
            }

            // Manufacturer Card
            AnimatedVisibility(isVisible, enter = slideInHorizontally(tween(400, delayMillis = 100)) { it } + fadeIn()) {
                InfoCard(
                    icon = Icons.Filled.Factory,
                    title = "Manufacturer",
                    content = displayDrug.manufacturer ?: "Unknown",
                    color = Color(0xFF1976D2)
                )
            }

            // Description Card
            AnimatedVisibility(isVisible, enter = slideInHorizontally(tween(400, delayMillis = 200)) { it } + fadeIn()) {
                InfoCard(
                    icon = Icons.Filled.Info,
                    title = "Description",
                    content = displayDrug.description ?: "No description available",
                    color = Color(0xFF00897B)
                )
            }

            // Indications Card
            if (displayDrug.indications.isNotEmpty()) {
                AnimatedVisibility(isVisible, enter = slideInHorizontally(tween(400, delayMillis = 300)) { it } + fadeIn()) {
                    InfoCard(
                        icon = Icons.Filled.Healing,
                        title = "Indications",
                        content = displayDrug.indications.joinToString("\n• ", "• "),
                        color = Color(0xFF4CAF50)
                    )
                }
            }

            // Side Effects Card
            if (displayDrug.sideEffects.isNotEmpty()) {
                AnimatedVisibility(isVisible, enter = slideInHorizontally(tween(400, delayMillis = 400)) { it } + fadeIn()) {
                    InfoCard(
                        icon = Icons.Filled.Warning,
                        title = "Side Effects",
                        content = displayDrug.sideEffects.joinToString("\n• ", "• "),
                        color = Color(0xFFFF9800),
                        isWarning = true
                    )
                }
            }

            // Warnings Card
            if (displayDrug.warnings.isNotEmpty()) {
                AnimatedVisibility(isVisible, enter = slideInHorizontally(tween(400, delayMillis = 500)) { it } + fadeIn()) {
                    InfoCard(
                        icon = Icons.Filled.GppBad,
                        title = "Warnings",
                        content = displayDrug.warnings.joinToString("\n• ", "• "),
                        color = Color(0xFFF44336),
                        isWarning = true
                    )
                }
            }

            // Action Buttons
            AnimatedVisibility(isVisible, enter = fadeIn(tween(400, delayMillis = 600)) + expandVertically()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("add_medicine") },
                        modifier = Modifier.weight(1f).height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C4DFF))
                    ) {
                        Icon(Icons.Filled.Add, null, Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add to Inventory")
                    }

                    OutlinedButton(
                        onClick = { navController.navigate("create_reminder/") },
                        modifier = Modifier.weight(1f).height(52.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Alarm, null, Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Set Reminder")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String,
    color: Color,
    isWarning: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isWarning) color.copy(alpha = 0.05f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, tint = color, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = color)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(content, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF424242))
        }
    }
}