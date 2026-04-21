package com.pharmai.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define colors first
private val Primary = Color(0xFF2196F3)
private val Secondary = Color(0xFF4CAF50)
private val Tertiary = Color(0xFFFF9800)
private val BackgroundLight = Color(0xFFF5F5F5)
private val BackgroundDark = Color(0xFF121212)
private val SurfaceLight = Color(0xFFFFFFFF)
private val SurfaceDark = Color(0xFF1E1E1E)
private val ErrorLight = Color(0xFFB3261E)
private val ErrorDark = Color(0xFFF2B8B5)

// Define color schemes after colors are declared
private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = Primary.copy(alpha = 0.3f),
    onPrimaryContainer = Color.White,
    secondary = Secondary,
    onSecondary = Color.White,
    secondaryContainer = Secondary.copy(alpha = 0.3f),
    onSecondaryContainer = Color.White,
    tertiary = Tertiary,
    onTertiary = Color.White,
    tertiaryContainer = Tertiary.copy(alpha = 0.3f),
    onTertiaryContainer = Color.White,
    background = BackgroundDark,
    onBackground = Color.White,
    surface = SurfaceDark,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    error = ErrorDark,
    onError = Color.White,
    errorContainer = ErrorDark.copy(alpha = 0.3f),
    onErrorContainer = Color.White,
    outline = Color(0xFF938F99)
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = Primary.copy(alpha = 0.1f),
    onPrimaryContainer = Color.Black,
    secondary = Secondary,
    onSecondary = Color.White,
    secondaryContainer = Secondary.copy(alpha = 0.1f),
    onSecondaryContainer = Color.Black,
    tertiary = Tertiary,
    onTertiary = Color.White,
    tertiaryContainer = Tertiary.copy(alpha = 0.1f),
    onTertiaryContainer = Color.Black,
    background = BackgroundLight,
    onBackground = Color.Black,
    surface = SurfaceLight,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    error = ErrorLight,
    onError = Color.White,
    errorContainer = ErrorLight.copy(alpha = 0.1f),
    onErrorContainer = Color.Black,
    outline = Color(0xFF79747E)
)

@Composable
fun PharmAiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

@Composable
fun Typography() = Typography(
    displayLarge = MaterialTheme.typography.displayLarge,
    displayMedium = MaterialTheme.typography.displayMedium,
    displaySmall = MaterialTheme.typography.displaySmall,
    headlineLarge = MaterialTheme.typography.headlineLarge,
    headlineMedium = MaterialTheme.typography.headlineMedium,
    headlineSmall = MaterialTheme.typography.headlineSmall,
    titleLarge = MaterialTheme.typography.titleLarge,
    titleMedium = MaterialTheme.typography.titleMedium,
    titleSmall = MaterialTheme.typography.titleSmall,
    bodyLarge = MaterialTheme.typography.bodyLarge,
    bodyMedium = MaterialTheme.typography.bodyMedium,
    bodySmall = MaterialTheme.typography.bodySmall,
    labelLarge = MaterialTheme.typography.labelLarge,
    labelMedium = MaterialTheme.typography.labelMedium,
    labelSmall = MaterialTheme.typography.labelSmall
)

@Composable
fun Shapes() = Shapes(
    extraSmall = MaterialTheme.shapes.extraSmall,
    small = MaterialTheme.shapes.small,
    medium = MaterialTheme.shapes.medium,
    large = MaterialTheme.shapes.large,
    extraLarge = MaterialTheme.shapes.extraLarge
)