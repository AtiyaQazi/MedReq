package com.example.fieldmedicapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6B7C3A), // Dark green
    onPrimary = Color.White,
    secondary = Color(0xFFFFD700), // Gold
    background = Color(0xFF4A5D23), // Darker green base
    onBackground = Color.White,
    surface = Color(0xFF6B7C3A).copy(alpha = 0.9f), // Semi-transparent surface
    onSurface = Color.White,
    tertiary = Color(0xFF8BC34A) // Light green for accents
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6B7C3A), // Dark green
    onPrimary = Color.White,
    secondary = Color(0xFFFFD700), // Gold
    background = Color(0xFF4A5D23), // Darker green base
    onBackground = Color.White,
    surface = Color(0xFF6B7C3A).copy(alpha = 0.9f), // Semi-transparent surface
    onSurface = Color.White,
    tertiary = Color(0xFF8BC34A) // Light green for accents
)

// Define a custom Typography object
private val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    ),
    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.White
    )
)

@Composable
fun FieldMedicAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // Use the custom Typography
        shapes = MaterialTheme.shapes,
        content = content
    )
}