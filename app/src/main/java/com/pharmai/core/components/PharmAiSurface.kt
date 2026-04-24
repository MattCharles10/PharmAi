package com.pharmai.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pharmai.core.theme.LocalPharmAiShapes
import com.pharmai.core.theme.LocalPharmAiColors

@Composable
fun PharmAiSurface(modifier: Modifier = Modifier, elevated: Boolean = true, shape: Shape? = null, content: @Composable () -> Unit) {
    val colors = LocalPharmAiColors.current
    val shapes = LocalPharmAiShapes.current

    Box(
        modifier = modifier.background(color = colors.surface, shape = shape ?: shapes.MedicineCard)
            .then(if (elevated) Modifier.shadow(elevation = 4.dp, shape = shape ?: shapes.MedicineCard, clip = false) else Modifier)
    ) {
        content()
    }
}