package com.cgsoftware.neoui.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

object NeumorphicUtils {
    fun getLightShadowColor(baseColor: Color, isDark: Boolean): Color {
        return if (isDark) {
            // In dark mode, light shadow is a slightly lighter version of the background
            lighten(baseColor, 0.05f)
        } else {
            // In light mode, light shadow is white
            Color.White
        }
    }

    fun getDarkShadowColor(baseColor: Color, isDark: Boolean): Color {
        return if (isDark) {
            // In dark mode, dark shadow is much darker
            darken(baseColor, 0.15f)
        } else {
            // In light mode, dark shadow is a darker grey/blue
            darken(baseColor, 0.15f)
        }
    }

    private fun lighten(color: Color, fraction: Float): Color {
        val hsl = FloatArray(3)
        ColorUtils.colorToHSL(color.toArgb(), hsl)
        hsl[2] = (hsl[2] + fraction).coerceIn(0f, 1f)
        return Color(ColorUtils.HSLToColor(hsl))
    }

    private fun darken(color: Color, fraction: Float): Color {
        val hsl = FloatArray(3)
        ColorUtils.colorToHSL(color.toArgb(), hsl)
        hsl[2] = (hsl[2] - fraction).coerceIn(0f, 1f)
        return Color(ColorUtils.HSLToColor(hsl))
    }
}
