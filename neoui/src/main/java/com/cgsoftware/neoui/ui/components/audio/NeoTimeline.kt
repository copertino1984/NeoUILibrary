package com.cgsoftware.neoui.ui.components.audio

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.cgsoftware.neoui.ui.theme.NeoTheme
import kotlin.math.max
import kotlin.math.min
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.gestures.detectTapGestures
import com.cgsoftware.neoui.ui.theme.NeoDefaults
import com.cgsoftware.neoui.ui.theme.NeoSize

@Composable
fun NeoTimeline(
    progress: Float,
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,  // <-- AGGIUNGI
    enabled: Boolean = true,
    accentColor: Color = NeoTheme.colors.accent
) {
    val p = progress.coerceIn(0f, 1f)
    val pAnim by animateFloatAsState(p, tween(120), label = "timelineProgress")
    val isDark = NeoTheme.colors.isDark
    val accent = accentColor

    val timelineHeight = NeoDefaults.Timeline.height(componentSize)  // <-- AGGIUNGI

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(timelineHeight)
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput
                detectTapGestures { pos ->
                    onSeek((pos.x / size.width.toFloat()).coerceIn(0f, 1f))
                }
            }
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput
                detectDragGestures { change, _ ->
                    change.consume()
                    onSeek((change.position.x / size.width.toFloat()).coerceIn(0f, 1f))
                }
            }
    ) {
        val h = size.height

        val thumbW = h * 1.30f
        val thumbH = h * 0.92f
        val pad = thumbW * 0.55f

        val trackRect = Rect(
            left = pad,
            top = 0f,
            right = size.width - pad,
            bottom = h
        )
        val r = CornerRadius(h * 0.55f, h * 0.55f)

        val hi = if (isDark) Color.White.copy(alpha = 0.10f) else Color.White.copy(alpha = 0.22f)
        val sh = if (isDark) Color.Black.copy(alpha = 0.24f) else Color.Black.copy(alpha = 0.14f)

        // ---- Outer recessed groove ----
        drawRoundRect(
            color = Color.Black.copy(alpha = if (isDark) 0.28f else 0.18f),
            topLeft = Offset(trackRect.left, trackRect.top + h * 0.10f),
            size = trackRect.size,
            cornerRadius = r
        )
        drawRoundRect(
            color = Color.Black.copy(alpha = if (isDark) 0.16f else 0.10f),
            topLeft = trackRect.topLeft,
            size = trackRect.size,
            cornerRadius = r
        )
        drawRoundRect(
            brush = Brush.linearGradient(
                colors = listOf(hi, Color.Transparent, Color.Transparent),
                start = Offset(trackRect.left, trackRect.top),
                end = Offset(trackRect.right, trackRect.bottom)
            ),
            topLeft = trackRect.topLeft,
            size = trackRect.size,
            cornerRadius = r
        )
        drawRoundRect(
            brush = Brush.linearGradient(
                colors = listOf(Color.Transparent, Color.Transparent, sh),
                start = Offset(trackRect.left, trackRect.top),
                end = Offset(trackRect.right, trackRect.bottom)
            ),
            topLeft = trackRect.topLeft,
            size = trackRect.size,
            cornerRadius = r
        )

        // ---- Inner channel ----
        val chPad = h * 0.18f
        val channel = Rect(
            left = trackRect.left + chPad,
            top = trackRect.top + chPad,
            right = trackRect.right - chPad,
            bottom = trackRect.bottom - chPad
        )
        val cr2 = CornerRadius(channel.height * 0.65f, channel.height * 0.65f)
        drawRoundRect(
            color = Color.Black.copy(alpha = if (isDark) 0.22f else 0.12f),
            topLeft = channel.topLeft,
            size = channel.size,
            cornerRadius = cr2
        )

        // fill
        val fillW = channel.width * pAnim
        if (fillW > 0.5f) {
            val fill = Rect(channel.left, channel.top, channel.left + fillW, channel.bottom)
            val fillBrush = Brush.linearGradient(
                colors = listOf(
                    accent.copy(alpha = if (isDark) 0.28f else 0.22f),
                    accent.copy(alpha = if (isDark) 0.65f else 0.55f),
                    accent.copy(alpha = if (isDark) 0.95f else 0.85f)
                ),
                start = Offset(fill.left, fill.top),
                end = Offset(fill.left, fill.bottom)
            )
            drawRoundRect(fillBrush, fill.topLeft, fill.size, cr2)
        }

        // specular line
        drawRoundRect(
            color = Color.White.copy(alpha = if (isDark) 0.06f else 0.10f),
            topLeft = Offset(channel.left, channel.top),
            size = Size(channel.width, max(1f, h * 0.06f)),
            cornerRadius = CornerRadius(999f, 999f)
        )

        // ---- Thumb ----
        val x = (trackRect.left + trackRect.width * pAnim).coerceIn(trackRect.left, trackRect.right)
        val th = Rect(
            left = x - thumbW / 2f,
            top = h / 2f - thumbH / 2f,
            right = x + thumbW / 2f,
            bottom = h / 2f + thumbH / 2f
        )
        val tcr = CornerRadius(min(thumbW, thumbH) * 0.38f, min(thumbW, thumbH) * 0.38f)

        // shadow
        drawRoundRect(
            color = Color.Black.copy(alpha = if (isDark) 0.30f else 0.18f),
            topLeft = Offset(th.left + 3f, th.top + 4f),
            size = th.size,
            cornerRadius = tcr
        )

        val body = Brush.linearGradient(
            colors = if (isDark) {
                listOf(
                    Color(0xFF303741).copy(alpha = 0.62f),
                    Color(0xFF161B22).copy(alpha = 0.82f),
                    Color(0xFF0C1016).copy(alpha = 0.92f)
                )
            } else {
                listOf(
                    Color(0xFFE9EDF3).copy(alpha = 0.75f),
                    Color(0xFFD2DAE4).copy(alpha = 0.88f),
                    Color(0xFFB9C3CF).copy(alpha = 0.92f)
                )
            },
            start = Offset(th.left, th.top),
            end = Offset(th.left, th.bottom)
        )
        drawRoundRect(body, th.topLeft, th.size, tcr)

        val bevelW = max(1f, h * 0.08f)
        drawRoundRect(
            color = Color.White.copy(alpha = if (isDark) 0.12f else 0.30f),
            topLeft = th.topLeft,
            size = th.size,
            cornerRadius = tcr,
            style = Stroke(width = bevelW)
        )
        drawRoundRect(
            color = Color.Black.copy(alpha = if (isDark) 0.22f else 0.12f),
            topLeft = Offset(th.left + 1.5f, th.top + 1.5f),
            size = th.size,
            cornerRadius = tcr,
            style = Stroke(width = max(1f, bevelW * 0.75f))
        )

        // micro LED (sottile)
        val ledH = max(1f, h * 0.10f)
        val led = Rect(
            left = th.left + thumbW * 0.20f,
            top = th.center.y - ledH / 2f,
            right = th.right - thumbW * 0.20f,
            bottom = th.center.y + ledH / 2f
        )
        drawRoundRect(
            color = accent.copy(alpha = if (isDark) 0.16f else 0.12f),
            topLeft = Offset(led.left - 2f, led.top - 2f),
            size = Size(led.width + 4f, led.height + 4f),
            cornerRadius = CornerRadius(999f, 999f)
        )
        drawRoundRect(
            color = accent.copy(alpha = 0.85f),
            topLeft = led.topLeft,
            size = led.size,
            cornerRadius = CornerRadius(999f, 999f)
        )
    }
}