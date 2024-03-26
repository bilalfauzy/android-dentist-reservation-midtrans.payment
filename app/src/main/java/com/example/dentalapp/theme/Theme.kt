package com.example.dentalapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = baseColor,
    primaryVariant = baseColor,
    secondary = baseColor,
    background = backColor
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black
)

private val LightColorPalette = lightColors(
    primary = baseColor,
    primaryVariant = baseColor,
    secondary = baseColor,
    background = backColor,
    surface = backColor,
//    onPrimary = baseColor,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black
)

@Composable
fun DentalAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}