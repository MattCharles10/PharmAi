
package com.pharmai.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.pharmai.ui.theme.Typography
import androidx.compose.ui.platform.LocalContext
import com.pharmai.ui.theme.Black
import com.pharmai.ui.theme.Gray900
import com.pharmai.ui.theme.Pink80
import com.pharmai.ui.theme.Primary
import com.pharmai.ui.theme.Purple80
import com.pharmai.ui.theme.PurpleGrey80
import com.pharmai.ui.theme.Secondary
import com.pharmai.ui.theme.White

@Composable
fun PharmAiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = Purple80,
            secondary = PurpleGrey80,
            tertiary = Pink80
        )
        else -> lightColorScheme(
            primary = Primary,
            onPrimary = White,
            secondary = Secondary,
            onSecondary = Black,
            error = MaterialTheme.colorScheme.error,
            onError = White,
            background = White,
            onBackground = Gray900,
            surface = White,
            onSurface = Gray900
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}