package com.pharmai.features.search.presentation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.pharmai.features.search.presentation.components.DrugCard
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    // Animated pills (NEW - visual only)
    val infiniteTransition = rememberInfiniteTransition(label = "search_pills")
    val pillX by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(7000, easing = LinearEasing)), label = "x")
    val pillY by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(9000, easing = LinearEasing)), label = "y")
    val pillRot by infiniteTransition.animateFloat(0f, 360f, infiniteRepeatable(tween(5000, easing = LinearEasing)), label = "rot")
    val pillColor1 by infiniteTransition.animateColor(Color(0xFF7C4DFF), Color(0xFFE040FB), infiniteRepeatable(tween(3000), RepeatMode.Reverse), label = "c1")

    Scaffold(
        containerColor = Color(0xFFF3E8FF),  // Changed to soft purple
        bottomBar = { PharmAiBottomNavBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {

            // Animated pills background (NEW)
            Canvas(Modifier.fillMaxSize()) {
                drawPill(Offset(size.width * 0.2f + cos(Math.toRadians(pillX.toDouble())).toFloat() * 60f, size.height * 0.25f + sin(Math.toRadians(pillY.toDouble())).toFloat() * 50f), Size(90f, 40f), pillColor1.copy(alpha = 0.18f), pillRot)
                drawPill(Offset(size.width * 0.75f + sin(Math.toRadians((pillY + 90).toDouble())).toFloat() * 70f, size.height * 0.65f + cos(Math.toRadians((pillX + 45).toDouble())).toFloat() * 40f), Size(65f, 30f), Color(0xFF4A148C).copy(alpha = 0.15f), pillRot * 0.8f)
                drawPill(Offset(size.width * 0.5f + cos(Math.toRadians((pillY + 180).toDouble())).toFloat() * 80f, size.height * 0.8f + sin(Math.toRadians((pillX + 270).toDouble())).toFloat() * 30f), Size(50f, 22f), Color(0xFFB388FF).copy(alpha = 0.12f), pillRot * 1.3f)
            }

            // ORIGINAL LOGIC UNCHANGED BELOW
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Search, null, tint = Color(0xFF7C4DFF))
                        Spacer(modifier = Modifier.width(8.dp))
                        androidx.compose.foundation.text.BasicTextField(
                            value = query, onValueChange = { query = it },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f),
                            decorationBox = { if (query.isEmpty()) Text("Search medicines...", color = Color(0xFFB388FF)) else it() }
                        )
                        if (query.isNotEmpty()) IconButton(onClick = { query = "" }) { Icon(Icons.Default.Clear, null, tint = Color(0xFF7C4DFF)) }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.handleEvent(SearchEvent.Search(query)) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C4DFF))
                ) {
                    Text("Search", fontWeight = FontWeight.SemiBold, color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                when {
                    state.isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = Color(0xFF7C4DFF)) }
                    state.error != null -> Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                    state.results.isEmpty() && query.isNotEmpty() -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No results found") }
                    else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(state.results) { drug -> DrugCard(drug) { navController.navigate(Destination.DrugDetail.createRoute(drug.id)) } }
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