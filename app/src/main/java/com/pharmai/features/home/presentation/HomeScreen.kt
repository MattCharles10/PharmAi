package com.pharmai.features.home.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlin.math.cos
import kotlin.math.sin

// Theme Colors
private val HomeBackground = Color(0xFFE8F4FD)
private val HomePrimary = Color(0xFF1976D2)
private val HomePrimaryDark = Color(0xFF0D47A1)
private val HomeAccent = Color(0xFF00BCD4)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var isVisible by remember { mutableStateOf(false) }

    // Floating tablet animations
    val infiniteTransition = rememberInfiniteTransition(label = "tablet")
    val tabletX by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(6000, easing = LinearEasing)), label = "x")
    val tabletY by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(8000, easing = LinearEasing)), label = "y")
    val tabletRot by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(4000, easing = LinearEasing)), label = "rot")
    val pillColor by infiniteTransition.animateColor(HomePrimary, HomeAccent, infiniteRepeatable(tween(3000), RepeatMode.Reverse), label = "color")

    LaunchedEffect(Unit) { isVisible = true }

    Scaffold(
        containerColor = HomeBackground,
        bottomBar = { PharmAiBottomNavBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Animated floating pills in background
            AnimatedPills(tabletX, tabletY, tabletRot, pillColor)

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Welcome
                item {
                    AnimatedVisibility(isVisible, enter = fadeIn(tween(600)) + slideInHorizontally { -50 }) {
                        Column {
                            Text("Hello, Sarah! 👋", style = MaterialTheme.typography.headlineMedium, color = HomePrimaryDark, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Ready to manage your health?", style = MaterialTheme.typography.bodyLarge, color = HomePrimary.copy(alpha = 0.7f))
                        }
                    }
                }

                // Quick Actions
                item {
                    AnimatedVisibility(isVisible, enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), 0.5f) + fadeIn(tween(400, delayMillis = 200))) {
                        Card(Modifier.fillMaxWidth(), RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                            Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                                ActionButton(Icons.Default.QrCodeScanner, "Scan", HomePrimary) { navController.navigate(Destination.Scanner.route) }
                                ActionButton(Icons.Default.Search, "Search", HomePrimary) { navController.navigate(Destination.Search.route) }
                                ActionButton(Icons.Default.Inventory, "Inventory", HomePrimary) { navController.navigate(Destination.Inventory.route) }
                                ActionButton(Icons.Default.Notifications, "Reminders", HomePrimary) { navController.navigate(Destination.Reminders.route) }
                            }
                        }
                    }
                }

                // Prescriptions Button
                item {
                    AnimatedVisibility(isVisible, enter = slideInHorizontally(tween(400, delayMillis = 300)) { it } + fadeIn()) {
                        Button(
                            onClick = { navController.navigate(Destination.Prescriptions.route) },
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = HomePrimary)
                        ) {
                            Icon(Icons.Filled.Description, null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("View Prescriptions", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                // Expiring Soon
                if (state.expiringMedicines.isNotEmpty()) {
                    item {
                        AnimatedVisibility(isVisible, enter = expandVertically(tween(500, delayMillis = 400)) + fadeIn()) {
                            Card(Modifier.fillMaxWidth(), RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))) {
                                Column(Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Warning, null, tint = Color(0xFFE65100))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Expiring Soon", fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    state.expiringMedicines.take(3).forEach {
                                        Text("${it.name} - ${it.daysLeft} days left", style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }
                        }
                    }
                }

                // Today's Schedule
                item {
                    AnimatedVisibility(isVisible, enter = fadeIn(tween(400, delayMillis = 500))) {
                        Text("Today's Schedule", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = HomePrimaryDark)
                    }
                }

                if (state.todayReminders.isEmpty()) {
                    item {
                        AnimatedVisibility(isVisible, enter = fadeIn(tween(400, delayMillis = 600))) {
                            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                                Text("No reminders", modifier = Modifier.padding(16.dp), color = Color.Gray)
                            }
                        }
                    }
                } else {
                    itemsIndexed(state.todayReminders) { index, reminder ->
                        AnimatedVisibility(isVisible, enter = slideInHorizontally(tween(400, delayMillis = 600 + index * 100)) { it } + fadeIn()) {
                            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                                Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Column {
                                        Text(reminder.medicineName, fontWeight = FontWeight.Bold, color = HomePrimaryDark)
                                        Text(reminder.dosage, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                                    }
                                    Text(reminder.time, color = HomePrimary, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
private fun ActionButton(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, color: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(onClick = onClick)) {
        Box(Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(color.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
            Icon(icon, label, Modifier.size(24.dp), tint = color)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.DarkGray)
    }
}

@Composable
private fun AnimatedPills(tabletX: Float, tabletY: Float, rotation: Float, color: Color) {
    // Pill 1 - Large Blue - DARK AND VISIBLE
    Canvas(Modifier.fillMaxSize()) {
        drawPill(
            center = Offset(
                size.width * 0.15f + cos(Math.toRadians(tabletX.toDouble())).toFloat() * 80f,
                size.height * 0.3f + sin(Math.toRadians(tabletY.toDouble())).toFloat() * 60f
            ),
            size = Size(120f, 55f),
            color = Color(0xFF1976D2),  // DARK BLUE
            rotation = rotation,
            alpha = 0.25f  // More visible
        )
    }

    // Pill 2 - Medium Green - DARK AND VISIBLE
    Canvas(Modifier.fillMaxSize()) {
        drawPill(
            center = Offset(
                size.width * 0.8f + cos(Math.toRadians((tabletY + 90).toDouble())).toFloat() * 50f,
                size.height * 0.7f + sin(Math.toRadians((tabletX + 45).toDouble())).toFloat() * 40f
            ),
            size = Size(85f, 40f),
            color = Color(0xFF2E7D32),  // DARK GREEN
            rotation = rotation * 0.7f,
            alpha = 0.25f
        )
    }

    // Pill 3 - Small Orange - DARK AND VISIBLE
    Canvas(Modifier.fillMaxSize()) {
        drawPill(
            center = Offset(
                size.width * 0.5f + sin(Math.toRadians((tabletY + 180).toDouble())).toFloat() * 100f,
                size.height * 0.15f + cos(Math.toRadians((tabletX + 270).toDouble())).toFloat() * 30f
            ),
            size = Size(65f, 30f),
            color = Color(0xFFE65100),  // DARK ORANGE
            rotation = rotation * 1.5f,
            alpha = 0.2f
        )
    }

    // Pill 4 - Small Purple - DARK AND VISIBLE
    Canvas(Modifier.fillMaxSize()) {
        drawPill(
            center = Offset(
                size.width * 0.35f + cos(Math.toRadians((tabletY + 45).toDouble())).toFloat() * 90f,
                size.height * 0.85f + sin(Math.toRadians((tabletX + 200).toDouble())).toFloat() * 40f
            ),
            size = Size(50f, 22f),
            color = Color(0xFF7B1FA2),  // DARK PURPLE
            rotation = rotation * 2f,
            alpha = 0.2f
        )
    }

    // Pill 5 - Tiny Red - DARK AND VISIBLE
    Canvas(Modifier.fillMaxSize()) {
        drawPill(
            center = Offset(
                size.width * 0.7f + sin(Math.toRadians((tabletX + 120).toDouble())).toFloat() * 70f,
                size.height * 0.4f + cos(Math.toRadians((tabletY + 300).toDouble())).toFloat() * 50f
            ),
            size = Size(40f, 18f),
            color = Color(0xFFC62828),  // DARK RED
            rotation = rotation * 1.2f,
            alpha = 0.2f
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawPill(
    center: Offset,
    size: Size,
    color: Color,
    rotation: Float,
    alpha: Float = 0.25f
) {
    val halfHeight = size.height / 2
    val radius = halfHeight

    drawContext.canvas.save()
    drawContext.canvas.translate(center.x, center.y)
    drawContext.canvas.rotate(rotation)
    drawContext.canvas.translate(-center.x, -center.y)

    // Pill body - DARK fill
    drawRoundRect(
        color = color.copy(alpha = alpha),
        topLeft = Offset(center.x - size.width / 2, center.y - halfHeight),
        size = Size(size.width, size.height),
        cornerRadius = CornerRadius(radius, radius)
    )

    // Pill border - Darker outline for visibility
    drawRoundRect(
        color = color.copy(alpha = alpha + 0.15f),
        topLeft = Offset(center.x - size.width / 2, center.y - halfHeight),
        size = Size(size.width, size.height),
        cornerRadius = CornerRadius(radius, radius),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
    )

    // Shine effect (highlight on top)
    drawRoundRect(
        color = Color.White.copy(alpha = 0.4f),
        topLeft = Offset(center.x - size.width / 2 + 5f, center.y - halfHeight + 3f),
        size = Size(size.width - 10f, size.height * 0.35f),
        cornerRadius = CornerRadius(radius * 0.8f, radius * 0.8f)
    )

    // Score line (middle line like real pill)
    drawLine(
        color = Color.White.copy(alpha = 0.6f),
        start = Offset(center.x, center.y - halfHeight + 8f),
        end = Offset(center.x, center.y + halfHeight - 8f),
        strokeWidth = 2.5f
    )

    drawContext.canvas.restore()
}