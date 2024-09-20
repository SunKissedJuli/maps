package com.example.maps.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = RoundedButtonColor,
    secondary = Purple,
    secondaryContainer = PurpleDark,
    onSecondaryContainer = PurpleLite,
    background = White,
    onSurface = Gray,
    onSecondary = Color.Black,
    onTertiary = Green,
    onTertiaryContainer = Orange,
    tertiary = Red,
    tertiaryContainer = Yellow,
    onPrimary = YellowStar
)

private val LightColorPalette = lightColorScheme(
    primary = RoundedButtonColor,
    secondary = Purple,
    secondaryContainer = PurpleDark,
    onSecondaryContainer = PurpleLite,
    background = White,
    onSurface = Gray,
    onSecondary = Color.Black,
    onTertiary = Green,
    onTertiaryContainer = Orange,
    tertiary = Red,
    tertiaryContainer = Yellow,
    onPrimary = YellowStar
)

@Composable
fun MapsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Material2Typography,
        content = content
    )
}