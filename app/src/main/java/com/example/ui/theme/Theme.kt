package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SurgirDarkPrimary,
    onPrimary = Color.Black,
    primaryContainer = SurgirDarkPrimaryContainer,
    onPrimaryContainer = Color.White,
    secondary = SurgirDarkSecondary,
    onSecondary = Color.Black,
    tertiary = SurgirDarkTertiary,
    background = SurgirDarkBackground,
    onBackground = Color.White,
    surface = SurgirDarkSurface,
    onSurface = Color.White,
    surfaceVariant = SurgirDarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = SurgirDarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = SurgirPrimary,
    onPrimary = Color.White,
    primaryContainer = SurgirPrimaryContainer,
    onPrimaryContainer = SurgirPrimary,
    secondary = SurgirSecondary,
    onSecondary = Color.White,
    tertiary = SurgirTertiary,
    background = SurgirLightBackground,
    onBackground = SurgirSecondary,
    surface = SurgirLightSurface,
    onSurface = SurgirSecondary,
    surfaceVariant = SurgirLightSurfaceVariant,
    onSurfaceVariant = Color(0xFF64748B),
    outline = SurgirBorder
)

@Composable
fun SurgirTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
