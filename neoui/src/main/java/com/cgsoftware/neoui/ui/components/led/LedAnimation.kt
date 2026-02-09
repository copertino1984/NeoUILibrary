package com.cgsoftware.neoui.ui.components.led

import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import com.cgsoftware.neoui.ui.components.led.LedMode
import kotlinx.coroutines.delay

@Immutable
data class LedAnimationState(
    val mode: LedMode,
    val breathValue: Float = 0f,
    val sweepPosition: Float = 0f,
    val pulseValue: Float = 0f
) {
    val effectiveAlpha: Float
        get() = when (mode) {
            LedMode.SOLID -> 1f
            LedMode.BREATH -> breathValue
            LedMode.PULSE -> pulseValue
            LedMode.SWEEP -> 1f
            else -> 0f
        }
}

@Composable
fun rememberLedAnimation(
    ledMode: LedMode,
    breathMs: Int,
    sweepMs: Int,
    pulseMs: Int,
    enabled: Boolean,
    // PRO: in play si usa BREATH, in connecting SWEEP loop, pulse è evento
    pulseAuto: Boolean = false,
    sweepLoop: Boolean = true,
): LedAnimationState {

    val breathSpeed = breathMs.coerceAtLeast(600)
    val sweepSpeed = sweepMs.coerceAtLeast(200)
    val pulseSpeed = pulseMs.coerceAtLeast(120)

    // BREATH (infinite)
    val breathTransition = rememberInfiniteTransition(label = "breath")
    val breath by breathTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = breathSpeed
                0.0f at 0
                1.0f at durationMillis / 2
                0.0f at durationMillis
            }
        ),
        label = "breath"
    )

    // SWEEP
    val sweepAnim = remember { Animatable(0f) }
    LaunchedEffect(enabled, ledMode, sweepSpeed, sweepLoop) {
        if (!enabled || ledMode != LedMode.SWEEP) {
            sweepAnim.snapTo(0f)
            return@LaunchedEffect
        }

        if (sweepLoop) {
            while (true) {
                sweepAnim.snapTo(0f)
                sweepAnim.animateTo(1f, animationSpec = tween(sweepSpeed, easing = LinearEasing))
            }
        } else {
            sweepAnim.snapTo(0f)
            sweepAnim.animateTo(1f, animationSpec = tween(sweepSpeed, easing = LinearEasing))
        }
    }

    // PULSE
    val pulseAnim = remember { Animatable(0f) }
    LaunchedEffect(enabled, ledMode, pulseSpeed, pulseAuto) {
        if (!enabled || ledMode != LedMode.PULSE) {
            pulseAnim.snapTo(0f)
            return@LaunchedEffect
        }

        if (pulseAuto) {
            while (true) {
                pulseAnim.animateTo(1f, tween(pulseSpeed / 2, easing = LinearEasing))
                pulseAnim.animateTo(0f, tween(pulseSpeed / 2, easing = LinearEasing))
                delay(16)
            }
        } else {
            pulseAnim.snapTo(0f)
            pulseAnim.animateTo(1f, tween(pulseSpeed / 2, easing = LinearEasing))
            pulseAnim.animateTo(0f, tween(pulseSpeed / 2, easing = LinearEasing))
        }
    }

    return remember(ledMode, breath, sweepAnim.value, pulseAnim.value, enabled) {
        if (!enabled) LedAnimationState(LedMode.OFF)
        else LedAnimationState(
            mode = ledMode,
            breathValue = if (ledMode == LedMode.BREATH) breath else 0f,
            sweepPosition = if (ledMode == LedMode.SWEEP) sweepAnim.value else 0f,
            pulseValue = if (ledMode == LedMode.PULSE) pulseAnim.value else 0f
        )
    }
}
