package com.pharmai.features.prescriptions.presentation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pharmai.core.navigation.PharmAiBottomNavBar
import com.pharmai.features.prescriptions.domain.models.Prescription
import com.pharmai.features.prescriptions.presentation.drawPill
import kotlin.math.cos
import kotlin.math.sin
import java.util.Date

// Teal Theme Colors
private val PrescriptionBackground = Color(0xFFE0F2F1)
private val PrescriptionPrimary = Color(0xFF00695C)
private val PrescriptionDark = Color(0xFF004D40)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionsScreen(
    navController: NavHostController,
    viewModel: PrescriptionViewModel = hiltViewModel()
) {
    val prescriptions by viewModel.prescriptions.collectAsState()

    // Animated pills (NEW - visual only)
    val infiniteTransition = rememberInfiniteTransition(label = "pres_pills")
    val pillX by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(7000, easing = LinearEasing)), label = "x")
    val pillY by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(9000, easing = LinearEasing)), label = "y")
    val pillRot by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(5000, easing = LinearEasing)), label = "rot")
    val pillColor1 by infiniteTransition.animateColor(PrescriptionPrimary, Color(0xFF009688), infiniteRepeatable(tween(3000), RepeatMode.Reverse), label = "c1")

    Scaffold(
        containerColor = PrescriptionBackground,  // Soft teal background
        topBar = {
            TopAppBar(
                title = { Text("Prescriptions", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrescriptionPrimary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_prescription") },
                containerColor = PrescriptionPrimary
            ) {
                Icon(Icons.Default.Add, "Add", tint = Color.White)
            }
        },
        bottomBar = { PharmAiBottomNavBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {

            // Animated pills background (NEW)
            Canvas(Modifier.fillMaxSize()) {
                drawPill(Offset(size.width * 0.2f + cos(Math.toRadians(pillX.toDouble())).toFloat() * 60f, size.height * 0.25f + sin(Math.toRadians(pillY.toDouble())).toFloat() * 50f), Size(75f, 35f), pillColor1.copy(alpha = 0.18f), pillRot)
                drawPill(Offset(size.width * 0.75f + sin(Math.toRadians((pillY + 90).toDouble())).toFloat() * 55f, size.height * 0.8f + cos(Math.toRadians((pillX + 45).toDouble())).toFloat() * 35f), Size(50f, 22f), PrescriptionDark.copy(alpha = 0.12f), pillRot * 0.9f)
            }

            // Show mock data if prescriptions are empty (for demonstration)
            val displayPrescriptions = if (prescriptions.isEmpty()) {
                listOf(
                    Prescription(1, "Dr. Smith", "City Hospital", Date(), null, "Amoxicillin", "500mg", "3 times daily", "7 days", "Take with food", listOf(), true),
                    Prescription(2, "Dr. Johnson", "Medical Center", Date(), null, "Ibuprofen", "400mg", "As needed", null, "For pain relief", listOf(), true)
                )
            } else {
                prescriptions
            }

            if (displayPrescriptions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("No prescriptions found", color = PrescriptionDark, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tap + to add a prescription", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(displayPrescriptions) { prescription ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(2.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(prescription.drugName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PrescriptionDark)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Dosage: ${prescription.dosage}", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
                                Text("Frequency: ${prescription.frequency}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                prescription.doctorName?.let {
                                    Text("Dr. $it", style = MaterialTheme.typography.bodySmall, color = PrescriptionPrimary, fontWeight = FontWeight.Medium)
                                }
                                prescription.duration?.let {
                                    Text("Duration: $it", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Helper function for drawing pills (NEW)
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawPill(center: Offset, size: Size, color: Color, rotation: Float) {
    val halfHeight = size.height / 2
    val radius = halfHeight
    drawContext.canvas.save()
    drawContext.canvas.translate(center.x, center.y)
    drawContext.canvas.rotate(rotation)
    drawContext.canvas.translate(-center.x, -center.y)
    drawRoundRect(color, Offset(center.x - size.width / 2, center.y - halfHeight), Size(size.width, size.height), CornerRadius(radius, radius))
    drawRoundRect(Color.White.copy(alpha = 0.25f), Offset(center.x - size.width / 2 + 4f, center.y - halfHeight + 2f), Size(size.width - 8f, size.height * 0.3f), CornerRadius(radius * 0.7f, radius * 0.7f))
    drawLine(Color.White.copy(alpha = 0.4f), Offset(center.x, center.y - halfHeight + 5f), Offset(center.x, center.y + halfHeight - 5f), 2f)
    drawContext.canvas.restore()
}