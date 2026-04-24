package com.pharmai.features.inventory.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import com.pharmai.core.navigation.Destination
import com.pharmai.core.navigation.PharmAiBottomNavBar
import com.pharmai.features.inventory.presentation.components.MedicineCard
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    navController: NavHostController,
    viewModel: InventoryViewModel = hiltViewModel()
) {
    val medicines by viewModel.medicines.collectAsState()

    // Animated pills (NEW - visual only)
    val infiniteTransition = rememberInfiniteTransition(label = "inv_pills")
    val pillX by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(8000, easing = LinearEasing)), label = "x")
    val pillY by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(10000, easing = LinearEasing)), label = "y")
    val pillRot by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(6000, easing = LinearEasing)), label = "rot")

    // GIF-like pill colors
    val pillColor1 by infiniteTransition.animateColor(Color(0xFF2E7D32), Color(0xFF4CAF50), infiniteRepeatable(tween(3000), RepeatMode.Reverse), label = "c1")
    val pillColor2 by infiniteTransition.animateColor(Color(0xFF1B5E20), Color(0xFF66BB6A), infiniteRepeatable(tween(4000), RepeatMode.Reverse), label = "c2")

    Scaffold(
        containerColor = Color(0xFFE8F5E9),  // Changed to soft green
        topBar = {
            TopAppBar(
                title = { Text("My Inventory", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Destination.AddMedicine.route) },
                containerColor = Color(0xFF2E7D32)
            ) {
                Icon(Icons.Default.Add, "Add", tint = Color.White)
            }
        },
        bottomBar = { PharmAiBottomNavBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {

            // Animated pills background (NEW)
            Canvas(Modifier.fillMaxSize()) {
                drawPill(Offset(size.width * 0.1f + cos(Math.toRadians(pillX.toDouble())).toFloat() * 50f, size.height * 0.2f + sin(Math.toRadians(pillY.toDouble())).toFloat() * 40f), Size(85f, 38f), pillColor1.copy(alpha = 0.2f), pillRot)
                drawPill(Offset(size.width * 0.85f + sin(Math.toRadians((pillY + 90).toDouble())).toFloat() * 60f, size.height * 0.75f + cos(Math.toRadians((pillX + 45).toDouble())).toFloat() * 35f), Size(60f, 28f), pillColor2.copy(alpha = 0.18f), pillRot * 0.9f)
                drawPill(Offset(size.width * 0.55f + cos(Math.toRadians((pillY + 180).toDouble())).toFloat() * 90f, size.height * 0.5f + sin(Math.toRadians((pillX + 270).toDouble())).toFloat() * 25f), Size(45f, 20f), Color(0xFF81C784).copy(alpha = 0.12f), pillRot * 1.5f)
            }

            // ORIGINAL LOGIC UNCHANGED BELOW
            if (medicines.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("No medicines in inventory", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tap + to add medicine", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(medicines) { medicine ->
                        MedicineCard(
                            medicine = medicine,
                            onDelete = { viewModel.handleEvent(InventoryEvent.DeleteMedicine(medicine.id)) }
                        )
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