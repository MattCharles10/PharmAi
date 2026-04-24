package com.pharmai.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.pharmai.core.theme.BackgroundPrimary
import com.pharmai.core.theme.BackgroundSecondary
import com.pharmai.core.theme.PharmAiPrimaryLight
import com.pharmai.core.theme.PharmAiSecondary

@Composable
fun PharmAiBackground(modifier: Modifier = Modifier, showGradient: Boolean = true, content: @Composable () -> Unit) {
    Box(
        modifier = modifier.fillMaxSize().background(
            brush = if (showGradient) Brush.verticalGradient(listOf(BackgroundPrimary, BackgroundSecondary))
            else Brush.verticalGradient(listOf(BackgroundPrimary, BackgroundPrimary))
        )
    ) {
        if (showGradient) {
            Box(modifier = Modifier.fillMaxSize().background(
                brush = Brush.radialGradient(
                    colors = listOf(PharmAiPrimaryLight.copy(alpha = 0.05f), Color.Transparent),
                    center = Offset(100f, 100f), radius = 500f
                )
            ))
            Box(modifier = Modifier.fillMaxSize().background(
                brush = Brush.radialGradient(
                    colors = listOf(PharmAiSecondary.copy(alpha = 0.08f), Color.Transparent),
                    center = Offset(300f, 600f), radius = 400f
                )
            ))
        }
        content()
    }
}