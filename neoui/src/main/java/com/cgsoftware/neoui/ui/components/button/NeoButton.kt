package com.cgsoftware.neoui.ui.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cgsoftware.neoui.ui.components.common.UiState
import com.cgsoftware.neoui.ui.components.led.*
import com.cgsoftware.neoui.ui.theme.NeoDefaults
import com.cgsoftware.neoui.ui.theme.NeoSize
import com.cgsoftware.neoui.ui.theme.NeoTheme
import com.cgsoftware.neoui.ui.utils.NeumorphicStyle
import com.cgsoftware.neoui.ui.utils.neumorphic

@Composable
fun NeumorphicButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,  // <-- AGGIUNGI
    //cornerRadius: Dp? = null,  // <-- CAMBIA a nullable
  //  elevation: Dp? = null,  // <-- CAMBIA a nullable
    accentColor: Color = NeoTheme.colors.accent,
    uiState: UiState = UiState.IDLE,
    ledConfig: LedConfig = LedConfig(style = LedStyle.CONTINUOUS),
    ledInset: Dp = 0.dp,
    ledWidth: Dp? = null,  // <-- CAMBIA a nullable
    ledGlow: Dp? = null,  // <-- CAMBIA a nullable
    content: @Composable RowScope.() -> Unit
) {
    val colors = NeoTheme.colors
    val interactionSource = remember { MutableInteractionSource() }

    // Defaults responsive
    val actualCornerRadius = NeoDefaults.Button.cornerRadius()
    val actualElevation = NeoDefaults.Button.elevation()
    val actualLedWidth = ledWidth ?: NeoDefaults.Led.width()
    val actualLedGlow = ledGlow ?: NeoDefaults.Led.glow()
    val contentPadding = NeoDefaults.Button.contentPadding()

    val ledMode = when (uiState) {
        UiState.DISABLED -> LedMode.OFF
        UiState.IDLE -> LedMode.SOLID
        UiState.ACTIVE -> LedMode.BREATH
        UiState.CONNECTING -> LedMode.SWEEP
        UiState.ALERT -> LedMode.PULSE
    }

    val intensity = when (uiState) {
        UiState.DISABLED -> 0f
        UiState.IDLE -> 0.35f
        UiState.ACTIVE -> 1f
        UiState.CONNECTING -> 1f
        UiState.ALERT -> 1f
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
        modifier = modifier.padding(NeoDefaults.Spacing.sm()),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .neumorphic(
                    lightShadowColor = colors.lightShadow,
                    darkShadowColor = colors.darkShadow,
                    backgroundColor = colors.background,
                    cornerRadius = actualCornerRadius,
                    elevation = actualElevation,
                    style = NeumorphicStyle.Raised,
                    accentColor = colors.background
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = uiState != UiState.DISABLED,
                    onClick = onClick
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = contentPadding * 1.8f, vertical = contentPadding)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )

            if (anim.mode != LedMode.OFF) {
                LedBorderOverlay(
                    modifier = Modifier.matchParentSize(),
                    cornerRadius = actualCornerRadius,
                    accentColor = accentColor,
                    width = actualLedWidth,
                    glow = actualLedGlow,
                    inset = ledInset,
                    animationState = anim,
                    config = ledConfig,
                    intensity = intensity
                )
            }
        }
    }
}