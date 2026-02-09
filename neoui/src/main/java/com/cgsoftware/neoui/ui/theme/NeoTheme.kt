package com.cgsoftware.neoui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class NeoColors(
    val background: Color,
    val lightShadow: Color,
    val darkShadow: Color,
    val accent: Color,
    val isDark: Boolean
)

/**
 * Colori calibrati per il neomorfismo puro.
 * Lo sfondo e l'oggetto condividono la stessa base cromatica.
 * Le ombre sono calcolate per dare l'illusione di estrusione.
 */
val LightNeoColors = NeoColors(
    background = Color(0xFFE0E5EC), // Grigio neutro classico neomorfico
    lightShadow = Color(0xFFFFFFFF), // Luce pura
    darkShadow = Color(0xFFA3B1C6).copy(alpha = 0.6f), // Ombra morbida
    accent = Color(0xFF00E5FF),
    isDark = false
)

val DarkNeoColors = NeoColors(
    background = Color(0xFF2D3238), // Grigio scuro profondo
    lightShadow = Color(0xFF3B424A), // Luce soffusa per dark mode
    darkShadow = Color(0xFF1A1E22), // Ombra profonda
    accent = Color(0xFF00E5FF),
    isDark = true
)

val LocalNeoColors = staticCompositionLocalOf { LightNeoColors }

object NeoTheme {
    val colors: NeoColors
        @Composable
        @ReadOnlyComposable
        get() = LocalNeoColors.current
}

@Composable
fun NeoUITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentColor: Color = Color(0xFF00E5FF),
    content: @Composable () -> Unit
) {
    val neoColors = if (darkTheme) {
        DarkNeoColors.copy(accent = accentColor)
    } else {
        LightNeoColors.copy(accent = accentColor)
    }

    CompositionLocalProvider(LocalNeoColors provides neoColors) {
        MaterialTheme(content = content)
    }
}
