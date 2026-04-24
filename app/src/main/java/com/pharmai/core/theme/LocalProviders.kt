package com.pharmai.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Use staticCompositionLocalOf instead of compositionLocalOf
val LocalPharmAiColors = staticCompositionLocalOf { LightPharmAiColors }
val LocalPharmAiShapes = staticCompositionLocalOf { PharmAiShapes }
val LocalPharmAiTypography = staticCompositionLocalOf { PharmAiTypography }
val LocalSpacing = staticCompositionLocalOf { Spacing() }

data class PharmAiColorScheme(
    val primary: androidx.compose.ui.graphics.Color,
    val primaryLight: androidx.compose.ui.graphics.Color,
    val primaryDark: androidx.compose.ui.graphics.Color,
    val secondary: androidx.compose.ui.graphics.Color,
    val secondaryVariant: androidx.compose.ui.graphics.Color,
    val accent: androidx.compose.ui.graphics.Color,
    val accentLight: androidx.compose.ui.graphics.Color,
    val background: androidx.compose.ui.graphics.Color,
    val backgroundSecondary: androidx.compose.ui.graphics.Color,
    val surface: androidx.compose.ui.graphics.Color,
    val surfaceSecondary: androidx.compose.ui.graphics.Color,
    val textPrimary: androidx.compose.ui.graphics.Color,
    val textSecondary: androidx.compose.ui.graphics.Color,
    val textTertiary: androidx.compose.ui.graphics.Color,
    val textOnPrimary: androidx.compose.ui.graphics.Color,
    val statusAvailable: androidx.compose.ui.graphics.Color,
    val statusBusy: androidx.compose.ui.graphics.Color,
    val statusAway: androidx.compose.ui.graphics.Color,
    val statusEmergency: androidx.compose.ui.graphics.Color,
    val gradientCool: List<androidx.compose.ui.graphics.Color>,
    val gradientWarm: List<androidx.compose.ui.graphics.Color>
)

val LightPharmAiColors = PharmAiColorScheme(
    primary = PharmAiPrimary,
    primaryLight = PharmAiPrimaryLight,
    primaryDark = PharmAiPrimaryDark,
    secondary = PharmAiSecondary,
    secondaryVariant = PharmAiSecondaryVariant,
    accent = PharmAiAccent,
    accentLight = PharmAiAccentLight,
    background = BackgroundPrimary,
    backgroundSecondary = BackgroundSecondary,
    surface = SurfacePrimary,
    surfaceSecondary = SurfaceSecondary,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textTertiary = TextTertiary,
    textOnPrimary = TextOnPrimary,
    statusAvailable = StatusAvailable,
    statusBusy = StatusBusy,
    statusAway = StatusAway,
    statusEmergency = StatusEmergency,
    gradientCool = GradientCool,
    gradientWarm = GradientWarm
)

data class Spacing(
    val none: Dp = 0.dp,
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 48.dp,
    val xxxl: Dp = 64.dp
)