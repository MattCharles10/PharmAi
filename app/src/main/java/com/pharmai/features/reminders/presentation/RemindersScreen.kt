package com.pharmai.features.reminders.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.pharmai.features.reminders.presentation.components.ReminderCard
import kotlin.math.cos
import kotlin.math.sin

private val ReminderBackground = Color(0xFFFFF3E0)
private val ReminderPrimary = Color(0xFFE65100)
private val ReminderDark = Color(0xFFBF360C)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    navController: NavHostController,
    viewModel: ReminderViewModel = hiltViewModel()
) {
    val reminders by viewModel.reminders.collectAsState()
    var isVisible by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "rem_pills")
    val pillX by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(7000, easing = LinearEasing)), label = "x")
    val pillY by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(9000, easing = LinearEasing)), label = "y")
    val pillRot by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(5000, easing = LinearEasing)), label = "rot")

    LaunchedEffect(Unit) { isVisible = true }

    Scaffold(
        containerColor = ReminderBackground,
        topBar = {
            TopAppBar(
                title = { Text("Reminders", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ReminderPrimary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create_reminder/") },
                containerColor = ReminderPrimary
            ) {
                Icon(Icons.Default.Add, "Add", tint = Color.White)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Animated pills
            Canvas(Modifier.fillMaxSize()) {
                drawPill(Offset(size.width * 0.2f + cos(Math.toRadians(pillX.toDouble())).toFloat() * 60f, size.height * 0.2f + sin(Math.toRadians(pillY.toDouble())).toFloat() * 50f), Size(75f, 35f), ReminderPrimary.copy(alpha = 0.18f), pillRot)
            }
            Canvas(Modifier.fillMaxSize()) {
                drawPill(Offset(size.width * 0.8f + sin(Math.toRadians((pillY + 90).toDouble())).toFloat() * 55f, size.height * 0.75f + cos(Math.toRadians((pillX + 45).toDouble())).toFloat() * 35f), Size(50f, 22f), ReminderDark.copy(alpha = 0.12f), pillRot * 0.9f)
            }

            if (reminders.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("No reminders set", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tap + to add a reminder", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Text(
                            "Your Reminders",
                            style = MaterialTheme.typography.headlineMedium,
                            color = ReminderDark,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    itemsIndexed(reminders) { index, reminder ->
                        ReminderCard(
                            reminder = reminder,
                            onToggle = { viewModel.handleEvent(ReminderEvent.ToggleReminder(reminder.id, !reminder.isActive)) },
                            onDelete = { viewModel.handleEvent(ReminderEvent.DeleteReminder(reminder.id)) }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawPill(center: Offset, size: Size, color: Color, rotation: Float) {
    val halfHeight = size.height / 2
    val radius = halfHeight
    drawContext.canvas.save()
    drawContext.canvas.translate(center.x, center.y)
    drawContext.canvas.rotate(rotation)
    drawContext.canvas.translate(-center.x, -center.y)
    drawRoundRect(color, Offset(center.x - size.width / 2, center.y - halfHeight), Size(size.width, size.height), CornerRadius(radius, radius))
    drawRoundRect(Color.White.copy(alpha = 0.3f), Offset(center.x - size.width / 2 + 4f, center.y - halfHeight + 2f), Size(size.width - 8f, size.height * 0.35f), CornerRadius(radius * 0.7f, radius * 0.7f))
    drawLine(Color.White.copy(alpha = 0.5f), Offset(center.x, center.y - halfHeight + 6f), Offset(center.x, center.y + halfHeight - 6f), 2f)
    drawContext.canvas.restore()
}