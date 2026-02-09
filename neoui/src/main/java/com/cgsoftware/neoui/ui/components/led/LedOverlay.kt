package com.cgsoftware.neoui.ui.components.led

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun LedBorderOverlay(
    modifier: Modifier,
    cornerRadius: Dp,
    accentColor: Color,
    width: Dp,
    glow: Dp,
    inset: Dp,
    animationState: LedAnimationState,
    config: LedConfig,
    intensity: Float = 1f
) {
    Canvas(modifier = modifier) {
        val w = width.toPx().coerceAtLeast(1f)
        val g = glow.toPx().coerceAtLeast(0f)
        val r = cornerRadius.toPx().coerceAtLeast(0f)
        val insetPx = inset.toPx().coerceAtLeast(0f)

        // anti-clipping: lascia un margine minimo pari a glow + stroke
        val safe = (g + w) * 0.75f

        val rect = Rect(
            left = w / 2 + insetPx + safe,
            top = w / 2 + insetPx + safe,
            right = size.width - w / 2 - insetPx - safe,
            bottom = size.height - w / 2 - insetPx - safe
        )

        val effectiveCorner = (r - w / 2 - insetPx - safe).coerceAtLeast(0f)

        when (config.style) {
            LedStyle.CONTINUOUS -> renderContinuousBorder(
                rect = rect,
                cornerRadius = effectiveCorner,
                core = accentColor,
                width = w,
                glow = g,
                mode = animationState.mode,
                alpha = (animationState.effectiveAlpha * intensity).coerceIn(0f, 1f),
                sweepPos = animationState.sweepPosition
            )

            LedStyle.SEGMENTED -> renderSegmentedBorder(
                rect = rect,
                cornerRadius = effectiveCorner,
                core = accentColor,
                width = w,
                animationState = animationState,
                config = config,
                intensity = intensity
            )
        }
    }
}
