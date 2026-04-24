package com.pharmai.features.profile.presentation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pharmai.core.navigation.Destination
import com.pharmai.core.navigation.PharmAiBottomNavBar
import com.pharmai.features.profile.presentation.drawPill
import kotlin.math.cos
import kotlin.math.sin

// Pink Theme Colors
private val ProfileBackground = Color(0xFFFCE4EC)
private val ProfilePrimary = Color(0xFFC2185B)
private val ProfileDark = Color(0xFF880E4F)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.profile.collectAsState()

    // Animated pills (NEW - visual only)
    val infiniteTransition = rememberInfiniteTransition(label = "profile_pills")
    val pillX by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(7500, easing = LinearEasing)), label = "x")
    val pillY by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(9500, easing = LinearEasing)), label = "y")
    val pillRot by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(5500, easing = LinearEasing)), label = "rot")
    val pillColor1 by infiniteTransition.animateColor(ProfilePrimary, Color(0xFFE91E63), infiniteRepeatable(tween(3000), RepeatMode.Reverse), label = "c1")

    Scaffold(
        containerColor = ProfileBackground,  // Soft pink background
        topBar = {
            TopAppBar(
                title = {
                    Text("Profile", fontWeight = FontWeight.Bold, color = Color.White)
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Destination.Settings.route) }) {
                        Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ProfilePrimary)
            )
        },
        bottomBar = { PharmAiBottomNavBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {

            // Animated pills background (NEW)
            Canvas(Modifier.fillMaxSize()) {
                drawPill(Offset(size.width * 0.15f + cos(Math.toRadians(pillX.toDouble())).toFloat() * 55f, size.height * 0.18f + sin(Math.toRadians(pillY.toDouble())).toFloat() * 45f), Size(80f, 35f), pillColor1.copy(alpha = 0.15f), pillRot)
                drawPill(Offset(size.width * 0.8f + sin(Math.toRadians((pillY + 90).toDouble())).toFloat() * 65f, size.height * 0.7f + cos(Math.toRadians((pillX + 45).toDouble())).toFloat() * 40f), Size(55f, 25f), Color(0xFFAD1457).copy(alpha = 0.12f), pillRot * 0.85f)
            }

            // ORIGINAL LOGIC UNCHANGED BELOW
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Profile Header Card
                ProfileHeader(
                    name = profile?.name ?: "Sarah Wilson",
                    email = profile?.email ?: "sarah.wilson@email.com",
                    onEditClick = { /* Edit profile */ }
                )

                // Statistics Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(value = "12", label = "Medicines", icon = Icons.Outlined.Medication, modifier = Modifier.weight(1f))
                    StatCard(value = "4", label = "Reminders", icon = Icons.Outlined.Alarm, modifier = Modifier.weight(1f))
                    StatCard(value = "3", label = "Scans", icon = Icons.Outlined.QrCodeScanner, modifier = Modifier.weight(1f))
                }

                // Quick Actions
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column {
                        ProfileMenuItem(icon = Icons.Outlined.Notifications, title = "My Reminders", subtitle = "Manage your medicine reminders", onClick = { navController.navigate(Destination.Reminders.route) })
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileMenuItem(icon = Icons.Outlined.Inventory, title = "My Inventory", subtitle = "View and manage your medicines", onClick = { navController.navigate(Destination.Inventory.route) })
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileMenuItem(icon = Icons.Outlined.History, title = "Scan History", subtitle = "View your medicine scan history", onClick = { navController.navigate(Destination.ScanHistory.route) })
                    }
                }

                // Account Settings
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column {
                        ProfileMenuItem(icon = Icons.Outlined.Favorite, title = "Favorites", subtitle = "Your saved medicines", onClick = { navController.navigate(Destination.Favorites.route) })
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileMenuItem(icon = Icons.Outlined.Description, title = "Prescriptions", subtitle = "Uploaded prescriptions", onClick = { navController.navigate(Destination.Prescriptions.route) })
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileMenuItem(icon = Icons.Outlined.Settings, title = "Settings", subtitle = "App preferences and configuration", onClick = { navController.navigate(Destination.Settings.route) })
                    }
                }

                // Support Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column {
                        ProfileMenuItem(icon = Icons.Outlined.Help, title = "Help & Support", subtitle = "Get assistance and FAQs", onClick = { })
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileMenuItem(icon = Icons.Outlined.Info, title = "About PharmAi", subtitle = "Version 1.0.0", onClick = { })
                    }
                }

                // Logout Button
                Button(
                    onClick = { viewModel.handleEvent(ProfileEvent.Logout) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ProfilePrimary, contentColor = Color.White)
                ) {
                    Icon(Icons.Outlined.Logout, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout", fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProfileEffect.NavigateToLogin -> {
                    navController.navigate(Destination.Login.route) { popUpTo(0) { inclusive = true } }
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(name: String, email: String, onEditClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box {
                Box(modifier = Modifier.size(80.dp).clip(RoundedCornerShape(20.dp)).background(ProfilePrimary), contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.Person, null, modifier = Modifier.size(40.dp), tint = Color.White)
                }
                Box(modifier = Modifier.align(Alignment.BottomEnd).size(28.dp).clip(RoundedCornerShape(8.dp)).background(ProfileDark).clickable(onClick = onEditClick), contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.Edit, "Edit", modifier = Modifier.size(16.dp), tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(name, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), color = ProfileDark)
            Spacer(modifier = Modifier.height(4.dp))
            Text(email, style = MaterialTheme.typography.bodyMedium, color = ProfilePrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Surface(color = ProfilePrimary.copy(alpha = 0.1f), shape = RoundedCornerShape(20.dp)) {
                Text("Verified Account", modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), style = MaterialTheme.typography.labelSmall, color = ProfilePrimary)
            }
        }
    }
}

@Composable
fun StatCard(value: String, label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, modifier = Modifier.size(24.dp), tint = ProfilePrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = ProfileDark)
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, title: String, subtitle: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(ProfilePrimary.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
            Icon(icon, null, modifier = Modifier.size(20.dp), tint = ProfilePrimary)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium), color = ProfileDark)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Icon(Icons.Filled.ChevronRight, null, modifier = Modifier.size(20.dp), tint = Color.Gray)
    }
}

// Pill drawing function (NEW)
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