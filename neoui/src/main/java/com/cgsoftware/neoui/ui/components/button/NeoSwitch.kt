package com.cgsoftware.neoui.ui.components.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cgsoftware.neoui.ui.components.common.UiState
import com.cgsoftware.neoui.ui.components.led.*
import com.cgsoftware.neoui.ui.theme.NeoDefaults
import com.cgsoftware.neoui.ui.theme.NeoSize
import com.cgsoftware.neoui.ui.theme.NeoTheme
import com.cgsoftware.neoui.ui.utils.NeumorphicStyle
import com.cgsoftware.neoui.ui.utils.neumorphic

/**
 * NeoSwitch - Hardware-style toggle switch con effetto neomorfico
 *
 * @param checked Stato corrente dello switch (true = ON, false = OFF)
 * @param onCheckedChange Callback invocato quando lo stato cambia
 * @param modifier Modifier per personalizzare layout
 * @param componentSize Dimensione responsive (SMALL/MEDIUM/LARGE)
 * @param accentColor Colore LED e accenti
 * @param uiState Stato UI (DISABLED, IDLE, ACTIVE)
 * @param ledConfig Configurazione LED border
 * @param ledInset Distanza LED dal bordo
 * @param ledWidth Larghezza LED (null = default responsive)
 * @param ledGlow Alone luminoso LED (null = default responsive)
 * @param animationDurationMs Durata animazione slide (default 280ms)
 */
@Composable
fun NeoSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,
    uiState: UiState = UiState.IDLE,
    ledInset: Dp = 0.dp,
    ledWidth: Dp? = null,
    ledGlow: Dp? = null,
    animationDurationMs: Int = 280
) {
    val colors = NeoTheme.colors
    val interactionSource = remember { MutableInteractionSource() }

    // Defaults responsive
    val trackWidth = NeoDefaults.Switch.trackWidth(componentSize)
    val trackHeight = NeoDefaults.Switch.trackHeight(componentSize)
    val thumbSize = NeoDefaults.Switch.thumbSize(componentSize)
    val cornerRadius = NeoDefaults.Switch.cornerRadius(componentSize)
    val elevation = NeoDefaults.Switch.elevation()
    val accentColor = NeoTheme.colors.accent
    val actualLedWidth = ledWidth ?: NeoDefaults.Led.width()
    val actualLedGlow = ledGlow ?: NeoDefaults.Led.glow()

    // Animazione posizione thumb
    val thumbOffset by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = tween(durationMillis = animationDurationMs),
        label = "SwitchThumbOffset"
    )

    // LED mode basato su stato
    val ledMode = when {
        uiState == UiState.DISABLED -> LedMode.OFF
        !checked -> LedMode.SOLID
        uiState == UiState.ACTIVE -> LedMode.BREATH
        uiState == UiState.CONNECTING -> LedMode.SWEEP
        uiState == UiState.ALERT -> LedMode.PULSE
        else -> LedMode.SOLID
    }

    val intensity = when {
        uiState == UiState.DISABLED -> 0f
        !checked -> 0.25f
        uiState == UiState.ACTIVE -> 1f
        uiState == UiState.CONNECTING -> 1f
        uiState == UiState.ALERT -> 1f
        else -> 0.85f
    }

    val anim = rememberLedAnimation(
        ledMode = ledMode,
        breathMs = NeoDefaults.Led.BREATH_MS,
        sweepMs = NeoDefaults.Led.SWEEP_MS,
        pulseMs = NeoDefaults.Led.PULSE_MS,
        enabled = uiState != UiState.DISABLED,
        pulseAuto = false,
        sweepLoop = (uiState == UiState.CONNECTING)
    )

    Box(
        modifier = modifier
            .padding(NeoDefaults.Spacing.sm())
            .width(trackWidth)
            .height(trackHeight),
        contentAlignment = Alignment.CenterStart
    ) {
        // TRACK (binario di scorrimento - Sunken)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .neumorphic(
                    lightShadowColor = colors.darkShadow, // Invertiti per effetto sunken
                    darkShadowColor = colors.lightShadow,
                    backgroundColor = colors.background,
                    cornerRadius = cornerRadius,
                    elevation = elevation * 0.3f,
                    style = NeumorphicStyle.Sunken,
                    accentColor = colors.background
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = uiState != UiState.DISABLED,
                    onClick = { onCheckedChange(!checked) }
                )
        ) {
            // Gradient interno track (effetto profondità)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        val isDark = colors.isDark
                        val trackBg = if (isDark) {
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF1A1E22),
                                    Color(0xFF2D3238)
                                )
                            )
                        } else {
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFCDD4DD),
                                    Color(0xFFE0E5EC)
                                )
                            )
                        }

                        drawRoundRect(
                            brush = trackBg,
                            cornerRadius = CornerRadius(cornerRadius.toPx()),
                            size = size
                        )

                        // Bordo interno sottile
                        val borderColor = if (isDark) {
                            Color.Black.copy(alpha = 0.2f)
                        } else {
                            Color(0xFFA3B1C6).copy(alpha = 0.1f)
                        }

                        drawRoundRect(
                            color = borderColor,
                            cornerRadius = CornerRadius(cornerRadius.toPx()),
                            size = size,
                            style = Stroke(width = 1.dp.toPx())
                        )
                    }
            )
        }

        // THUMB (cursore mobile - Raised)
        val thumbPadding = (trackHeight - thumbSize) / 2f
        val maxOffset = trackWidth - thumbSize - (thumbPadding * 2f)

        Box(
            modifier = Modifier
                .offset(x = thumbPadding + (maxOffset * thumbOffset))
                .size(thumbSize)
                .neumorphic(
                    lightShadowColor = colors.lightShadow,
                    darkShadowColor = colors.darkShadow,
                    backgroundColor = colors.background,
                    cornerRadius = thumbSize / 2f,
                    elevation = elevation,
                    style = NeumorphicStyle.Raised,
                    accentColor = colors.background
                )
        ) {
            // Gradient metallico thumb
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        val isDark = colors.isDark
                        val thumbGradient = if (isDark) {
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF3B424A),
                                    Color(0xFF2D3238),
                                    Color(0xFF1A1E22)
                                ),
                                center = Offset(size.width * 0.3f, size.height * 0.3f),
                                radius = size.width * 0.8f
                            )
                        } else {
                            Brush.radialGradient(
                                colors = listOf(
                                    Color.White,
                                    Color(0xFFE0E5EC),
                                    Color(0xFFCDD4DD)
                                ),
                                center = Offset(size.width * 0.3f, size.height * 0.3f),
                                radius = size.width * 0.8f
                            )
                        }

                        drawCircle(brush = thumbGradient)

                        // Highlight superiore
                        val highlight = if (isDark) {
                            Color.White.copy(alpha = 0.08f)
                        } else {
                            Color.White.copy(alpha = 0.35f)
                        }

                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(highlight, Color.Transparent),
                                center = Offset(size.width * 0.35f, size.height * 0.25f),
                                radius = size.width * 0.4f
                            )
                        )
                    }
            )

            // LED indicator sul thumb
            if (anim.mode != LedMode.OFF && checked) {
                LedBorderOverlay(
                    modifier = Modifier.matchParentSize(),
                    cornerRadius = thumbSize / 2f,
                    accentColor = accentColor,
                    width = actualLedWidth * 0.8f,
                    glow = actualLedGlow,
                    inset = ledInset,
                    animationState = anim,
                    config = LedConfig(style = LedStyle.CONTINUOUS),
                    intensity = intensity
                )
            }

            // LED punto centrale quando ON
            if (checked) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawBehind {
                            val centerDotRadius = size.width * 0.12f
                            val alpha = if (uiState == UiState.ACTIVE) 1f else 0.7f

                            // Glow
                            drawCircle(
                                color = accentColor.copy(alpha = alpha * 0.3f),
                                radius = centerDotRadius * 2f,
                                center = center
                            )

                            // Dot solido
                            drawCircle(
                                color = accentColor.copy(alpha = alpha),
                                radius = centerDotRadius,
                                center = center
                            )
                        }
                )
            }
        }
    }
}