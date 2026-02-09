package com.cgsoftware.neoui.ui.components.led

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cgsoftware.neoui.ui.theme.NeoDefaults

data class LedConfig(
    val style: LedStyle = LedStyle.CONTINUOUS,
    val segments: Int = NeoDefaults.Led.SEGMENTS,
    val segmentDepth: Dp = 0.5.dp,
    val segmentWidth: Dp = 4.dp,
    val segmentSpacing: Dp = 2.dp,
    val cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    val hasBevel: Boolean = true,
    val isRecessed: Boolean = false
)

enum class LedStyle { CONTINUOUS, SEGMENTED }
enum class CornerStyle { ROUNDED, SHARP, BEVELED, CHAMFERED }

// DEPRECATO - usa NeoDefaults.Led invece
@Deprecated("Use NeoDefaults.Led", ReplaceWith("NeoDefaults.Led"))
object LedMasterDefaults {
    val inset: Dp = 0.dp

    @Composable
    fun width(): Dp = NeoDefaults.Led.width()

    @Composable
    fun glow(): Dp = NeoDefaults.Led.glow()

    const val glowAlpha: Float = NeoDefaults.Led.GLOW_ALPHA
    const val solidAlpha: Float = NeoDefaults.Led.SOLID_ALPHA
    const val breathMs: Int = NeoDefaults.Led.BREATH_MS
    const val pulseMs: Int = NeoDefaults.Led.PULSE_MS
    const val sweepMs: Int = NeoDefaults.Led.SWEEP_MS
    const val segments: Int = NeoDefaults.Led.SEGMENTS
}