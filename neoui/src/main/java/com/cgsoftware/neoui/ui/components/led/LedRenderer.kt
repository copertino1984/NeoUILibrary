package com.cgsoftware.neoui.ui.components.led

import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import com.cgsoftware.neoui.ui.theme.NeoDefaults
import kotlin.math.abs
import kotlin.math.max

fun DrawScope.renderContinuousBorder(
    rect: Rect,
    cornerRadius: Float,
    core: Color,
    width: Float,
    glow: Float,
    mode: LedMode,
    alpha: Float,
    sweepPos: Float
) {
    val path = Path().apply {
        addRoundRect(RoundRect(rect, CornerRadius(cornerRadius, cornerRadius)))
    }

    val baseA = (alpha * NeoDefaults.Led.SOLID_ALPHA).coerceIn(0f, 1f)

    if (glow > 0f && baseA > 0.05f) {
        drawPath(
            path = path,
            color = core.copy(alpha = baseA * NeoDefaults.Led.GLOW_ALPHA),
            style = Stroke(width + glow * 2, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }

    if (mode == LedMode.SWEEP) {
        val dash = floatArrayOf(22f, 280f)
        val phase = (1f - sweepPos) * (dash[0] + dash[1])
        val pe = PathEffect.dashPathEffect(dash, phase)

        drawPath(
            path = path,
            color = core.copy(alpha = baseA),
            style = Stroke(width = max(1f, width * 1.2f), cap = StrokeCap.Round, join = StrokeJoin.Round, pathEffect = pe)
        )
        return
    }

    if (baseA > 0.02f) {
        drawPath(
            path = path,
            color = core.copy(alpha = baseA),
            style = Stroke(width, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

fun DrawScope.renderSegmentedBorder(
    rect: Rect,
    cornerRadius: Float,
    core: Color,
    width: Float,
    animationState: LedAnimationState,
    config: LedConfig,
    intensity: Float
) {
    val total = config.segments.coerceAtLeast(12)
    val perSide = max(3, total / 4)
    val totalLeds = perSide * 4

    fun isLit(index: Int): Boolean = when (animationState.mode) {
        LedMode.SOLID -> true
        LedMode.BREATH -> true
        LedMode.PULSE -> animationState.pulseValue > 0.08f
        LedMode.SWEEP -> {
            val p = index.toFloat() / totalLeds
            val range = 0.08f
            val d = abs(animationState.sweepPosition - p)
            d < range || (animationState.sweepPosition > 0.92f && p < 0.08f)
        }
        else -> false
    }

    val a = (animationState.effectiveAlpha * intensity * NeoDefaults.Led.SOLID_ALPHA).coerceIn(0f, 1f)
    val glowA = (animationState.effectiveAlpha * intensity * NeoDefaults.Led.GLOW_ALPHA).coerceIn(0f, 1f)

    fun seg(p1: Offset, p2: Offset) {
        drawLine(core.copy(alpha = a), p1, p2, strokeWidth = width, cap = StrokeCap.Round)
        drawLine(core.copy(alpha = glowA * 0.8f), p1, p2, strokeWidth = width + width * 1.4f, cap = StrokeCap.Round)
        if (config.hasBevel) {
            drawLine(Color.White.copy(alpha = 0.12f * a), p1, p2, strokeWidth = max(1f, width * 0.35f), cap = StrokeCap.Round)
        }
    }

    for (i in 0 until perSide) {
        val idx = i
        if (!isLit(idx)) continue
        val t = (i + 1f) / (perSide + 1f)
        val x = rect.left + rect.width * t
        seg(Offset(x - 6f, rect.top), Offset(x + 6f, rect.top))
    }
    for (i in 0 until perSide) {
        val idx = perSide + i
        if (!isLit(idx)) continue
        val t = (i + 1f) / (perSide + 1f)
        val y = rect.top + rect.height * t
        seg(Offset(rect.right, y - 6f), Offset(rect.right, y + 6f))
    }
    for (i in 0 until perSide) {
        val idx = perSide * 2 + i
        if (!isLit(idx)) continue
        val t = (i + 1f) / (perSide + 1f)
        val x = rect.right - rect.width * t
        seg(Offset(x - 6f, rect.bottom), Offset(x + 6f, rect.bottom))
    }
    for (i in 0 until perSide) {
        val idx = perSide * 3 + i
        if (!isLit(idx)) continue
        val t = (i + 1f) / (perSide + 1f)
        val y = rect.bottom - rect.height * t
        seg(Offset(rect.left, y - 6f), Offset(rect.left, y + 6f))
    }
}