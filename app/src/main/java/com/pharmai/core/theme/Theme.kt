package com.pharmai.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun PharmAiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = PharmAiPrimary,
            secondary = PharmAiAccent,
            background = BackgroundPrimary,
            surface = SurfacePrimary,
            onSurface = TextPrimary,
            onBackground = TextPrimary,
            onPrimary = TextOnPrimary,
            error = StatusEmergency
        )
    } else {
        lightColorScheme(
            primary = PharmAiPrimary,
            onPrimary = TextOnPrimary,
            primaryContainer = PharmAiPrimaryLight,
            secondary = PharmAiAccent,
            onSecondary = TextOnPrimary,
            secondaryContainer = PharmAiSecondary,
            tertiary = PharmAiAccentLight,
            background = BackgroundPrimary,
            onBackground = TextPrimary,
            surface = SurfacePrimary,
            onSurface = TextPrimary,
            surfaceVariant = SurfaceSecondary,
            onSurfaceVariant = TextSecondary,
            error = StatusEmergency,
            onError = Color.White,
            outline = TextTertiary
        )
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalPharmAiColors provides LightPharmAiColors,
        LocalPharmAiShapes provides PharmAiShapes,
        LocalPharmAiTypography provides PharmAiTypography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = PharmAiTypography,
            content = content
        )
    }
}