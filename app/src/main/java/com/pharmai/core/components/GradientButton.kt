package com.pharmai.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pharmai.core.theme.*

@Composable
fun GradientButton(
    text: String, onClick: () -> Unit, modifier: Modifier = Modifier,
    gradientColors: List<Color> = GradientCool, height: Dp = 56.dp
) {
    Box(
        modifier = modifier.fillMaxWidth().height(height).clip(PharmAiShapes.ButtonShape)
            .background(brush = Brush.horizontalGradient(gradientColors)).clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = Color.White)
    }
}