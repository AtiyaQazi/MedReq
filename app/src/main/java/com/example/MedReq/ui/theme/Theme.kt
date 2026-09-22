package com.example.MedReq.ui.theme

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

/*
 * MedReq Theme
 *
 * Primary: Medical Teal
 * Secondary: Soft Teal
 * Background: Soft Medical Mint
 * Surface: Clean White
 * Text: Deep Medical Green
 */

// ---------------------------------------------------------
// Light Color Scheme
// ---------------------------------------------------------

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2F7D72),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFD2EEE9),
    onPrimaryContainer = Color(0xFF0B3B35),

    secondary = Color(0xFF69AFA5),
    onSecondary = Color.White,

    secondaryContainer = Color(0xFFD5EEEA),
    onSecondaryContainer = Color(0xFF173D38),

    tertiary = Color(0xFF8BCFC4),
    onTertiary = Color(0xFF183B38),

    background = Color(0xFFF2F8F6),
    onBackground = Color(0xFF183B38),

    surface = Color.White,
    onSurface = Color(0xFF183B38),

    surfaceVariant = Color(0xFFE4F0ED),
    onSurfaceVariant = Color(0xFF49635F),

    outline = Color(0xFF78918D),

    error = Color(0xFFBA1A1A),
    onError = Color.White,

    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

// ---------------------------------------------------------
// Dark Color Scheme
// ---------------------------------------------------------

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF69AFA5),
    onPrimary = Color(0xFF073731),

    primaryContainer = Color(0xFF205B53),
    onPrimaryContainer = Color(0xFFC5EDE7),

    secondary = Color(0xFF8BCFC4),
    onSecondary = Color(0xFF073731),

    secondaryContainer = Color(0xFF376D65),
    onSecondaryContainer = Color(0xFFD5EEEA),

    tertiary = Color(0xFFA7DCD4),
    onTertiary = Color(0xFF073731),

    background = Color(0xFF102B28),
    onBackground = Color(0xFFE3F2EF),

    surface = Color(0xFF183B38),
    onSurface = Color(0xFFE3F2EF),

    surfaceVariant = Color(0xFF294540),
    onSurfaceVariant = Color(0xFFB9CCC8),

    outline = Color(0xFF819995),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),

    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

// ---------------------------------------------------------
// Typography
// ---------------------------------------------------------

private val AppTypography = Typography(

    headlineLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF183B38)
    ),

    headlineMedium = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF183B38)
    ),

    titleLarge = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF183B38)
    ),

    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF183B38)
    ),

    bodyLarge = TextStyle(
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF183B38)
    ),

    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF183B38)
    ),

    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF49635F)
    ),

    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF2F7D72)
    )
)

// ---------------------------------------------------------
// MedReq App Theme
// ---------------------------------------------------------

@Composable
fun FieldMedicAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}