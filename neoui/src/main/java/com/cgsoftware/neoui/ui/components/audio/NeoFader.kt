package com.cgsoftware.neoui.ui.components.audio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cgsoftware.neoui.ui.theme.NeoTheme
import kotlin.math.max
import kotlin.math.min
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import com.cgsoftware.neoui.ui.theme.NeoDefaults
import com.cgsoftware.neoui.ui.theme.NeoSize

enum class FaderOrientation { VERTICAL, HORIZONTAL }

@Composable
fun NeoFader(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,  // <-- AGGIUNGI
    enabled: Boolean = true,
    orientation: FaderOrientation = FaderOrientation.VERTICAL,
    accentColor: Color = NeoTheme.colors.accent,
    trackThickness: Dp? = null,  // <-- CAMBIA a nullable
    gripLines: Int = 3
) {
    val v = value.coerceIn(0f, 1f)
    val current by rememberUpdatedState(v)
    val isDark = NeoTheme.colors.isDark
    val accent = accentColor

    val faderHeight = NeoDefaults.Fader.height(componentSize)  // <-- AGGIUNGI
    val actualTrackThickness = trackThickness ?: NeoDefaults.Fader.trackThickness()  // <-- AGGIUNGI

    Canvas(
        modifier = modifier
            .width(actualTrackThickness.times(4))
            .height(faderHeight)
            .pointerInput(enabled, orientation) {
            if (!enabled) return@pointerInput
            detectDragGestures(
                onDragStart = { pos ->
                    val nv = when (orientation) {
                        FaderOrientation.VERTICAL -> 1f - (pos.y / size.height.toFloat())
                        FaderOrientation.HORIZONTAL -> (pos.x / size.width.toFloat())
                    }.coerceIn(0f, 1f)
                    onValueChange(nv)
                },
                onDrag = { change, drag ->
                    change.consume()
                    val nv = when (orientation) {
                        FaderOrientation.VERTICAL -> current + (-drag.y / size.height.toFloat())
                        FaderOrientation.HORIZONTAL -> current + (drag.x / size.width.toFloat())
                    }.coerceIn(0f, 1f)
                    onValueChange(nv)
                }
            )
        }
    ) {
        val w = actualTrackThickness.toPx().coerceAtLeast(8f)
        // CONTRASTO PIÃ™ FORTE (qui era il problema)
        val hiStrong = if (isDark) Color.White.copy(alpha = 0.18f) else Color.White.copy(alpha = 0.28f)
        val hiSoft   = if (isDark) Color.White.copy(alpha = 0.10f) else Color.White.copy(alpha = 0.18f)
        val shStrong = if (isDark) Color.Black.copy(alpha = 0.55f) else Color.Black.copy(alpha = 0.22f)
        val shSoft   = if (isDark) Color.Black.copy(alpha = 0.28f) else Color.Black.copy(alpha = 0.14f)

        val frameBase = if (isDark) Color(0xFF101418) else Color(0xFFCBD3DD)
        val slotBase  = if (isDark) Color(0xFF070A0D) else Color(0xFFB5C0CC)

        // thumb size (piÃ¹ â€œcapâ€ da mixer)
        val longPx = if (orientation == FaderOrientation.VERTICAL) size.height else size.width
        val shortPx = if (orientation == FaderOrientation.VERTICAL) size.width else size.height

        val thumbMajor = (longPx * -0.22f).coerceIn(w * 4.2f, w * 6.6f)
        val thumbMinor = (shortPx * 0.34f).coerceIn(w * 3.0f, w * 4.6f)

        val pad = max(thumbMajor, thumbMinor) * 0.70f

        val trackRect = when (orientation) {
            FaderOrientation.VERTICAL -> Rect(
                left = (size.width - w) / 2f,
                top = pad,
                right = (size.width + w) / 2f,
                bottom = size.height - pad
            )
            FaderOrientation.HORIZONTAL -> Rect(
                left = pad,
                top = (size.height - w) / 2f,
                right = size.width - pad,
                bottom = (size.height + w) / 2f
            )
        }

        val cr = CornerRadius(w * 0.90f, w * 0.90f)

        // ====== FRAME esterno (come â€œbinarioâ€ metallico) ======
        // drop shadow piÃ¹ evidente
        drawRoundRect(
            color = shStrong,
            topLeft = Offset(trackRect.left + 2f, trackRect.top + 4f),
            size = trackRect.size,
            cornerRadius = cr
        )
        drawRoundRect(
            color = frameBase.copy(alpha = if (isDark) 0.92f else 0.98f),
            topLeft = trackRect.topLeft,
            size = trackRect.size,
            cornerRadius = cr
        )

        // bevel esterno netto (luce in alto-sinistra / ombra in basso-destra)
        val frameStroke = max(1.5f, w * 0.14f)
        drawRoundRect(
            color = hiStrong,
            topLeft = Offset(trackRect.left - 1f, trackRect.top - 1f),
            size = Size(trackRect.width + 2f, trackRect.height + 2f),
            cornerRadius = cr,
            style = Stroke(width = frameStroke, cap = StrokeCap.Round)
        )
        drawRoundRect(
            color = shSoft,
            topLeft = Offset(trackRect.left + 1.5f, trackRect.top + 1.5f),
            size = trackRect.size,
            cornerRadius = cr,
            style = Stroke(width = frameStroke, cap = StrokeCap.Round)
        )

        // micro speculare sul frame
        drawRoundRect(
            brush = Brush.linearGradient(
                listOf(hiSoft, Color.Transparent),
                start = Offset(trackRect.left, trackRect.top),
                end = Offset(trackRect.right, trackRect.bottom)
            ),
            topLeft = trackRect.topLeft,
            size = trackRect.size,
            cornerRadius = cr
        )

        // ====== SLOT interno inciso (qui nasce lâ€™effetto â€œincassatoâ€) ======
        val slotPad = w * 0.26f
        val slot = Rect(
            left = trackRect.left + slotPad,
            top = trackRect.top + slotPad,
            right = trackRect.right - slotPad,
            bottom = trackRect.bottom - slotPad
        )
        val slotCr = CornerRadius(slot.width.coerceAtMost(slot.height) * 0.55f, slot.width.coerceAtMost(slot.height) * 0.55f)

        // base slot molto scura
        drawRoundRect(
            color = slotBase.copy(alpha = if (isDark) 1f else 0.95f),
            topLeft = slot.topLeft,
            size = slot.size,
            cornerRadius = slotCr
        )
        // inner shadow (alto e basso) -> INCASSO
        drawRoundRect(
            brush = Brush.linearGradient(
                listOf(shStrong, Color.Transparent, shStrong.copy(alpha = shSoft.alpha)),
                start = Offset(slot.left, slot.top),
                end = Offset(slot.left, slot.bottom)
            ),
            topLeft = slot.topLeft,
            size = slot.size,
            cornerRadius = slotCr
        )
        // inner bevel (luce sopra)
        val slotStroke = max(1.2f, w * 0.10f)
        drawRoundRect(
            color = hiSoft,
            topLeft = Offset(slot.left, slot.top),
            size = slot.size,
            cornerRadius = slotCr,
            style = Stroke(width = slotStroke, cap = StrokeCap.Round)
        )

        // ===== LED â€œrailâ€ sottile (parte da alpha 0) =====
        val ledAlpha = v.coerceIn(0f, 1f)
        if (ledAlpha > 0f) {
            val railThin = max(1.4f, w * 0.16f) // sottile vero
            val railRect = if (orientation == FaderOrientation.VERTICAL) {
                Rect(
                    left = slot.center.x - railThin / 2f,
                    top = slot.bottom - slot.height * v,
                    right = slot.center.x + railThin / 2f,
                    bottom = slot.bottom
                )
            } else {
                Rect(
                    left = slot.left,
                    top = slot.center.y - railThin / 2f,
                    right = slot.left + slot.width * v,
                    bottom = slot.center.y + railThin / 2f
                )
            }

            // glow minimo (non neon)
            drawRoundRect(
                color = accent.copy(alpha = ledAlpha * 0.10f),
                topLeft = Offset(railRect.left - 1.5f, railRect.top - 1.5f),
                size = Size(railRect.width + 3f, railRect.height + 3f),
                cornerRadius = CornerRadius(999f, 999f)
            )
            // core
            drawRoundRect(
                brush = Brush.linearGradient(
                    listOf(
                        accent.copy(alpha = ledAlpha * 0.25f),
                        accent.copy(alpha = ledAlpha * 0.85f)
                    ),
                    start = Offset(railRect.left, railRect.top),
                    end = Offset(railRect.left, railRect.bottom)
                ),
                topLeft = railRect.topLeft,
                size = railRect.size,
                cornerRadius = CornerRadius(999f, 999f)
            )
        }

        // ===== THUMB â€œMIXER CAPâ€ (non giocattolo) =====
        val c = when (orientation) {
            FaderOrientation.VERTICAL -> Offset(slot.center.x, slot.bottom - slot.height * v)
            FaderOrientation.HORIZONTAL -> Offset(slot.left + slot.width * v, slot.center.y)
        }

        val tW = if (orientation == FaderOrientation.VERTICAL) thumbMajor else thumbMinor
        val tH = if (orientation == FaderOrientation.VERTICAL) thumbMinor else thumbMajor
        val tcr = CornerRadius(min(tW, tH) * 0.18f, min(tW, tH) * 0.18f)

        val thumb = Rect(
            left = c.x - tW / 2f,
            top = c.y - tH / 2f,
            right = c.x + tW / 2f,
            bottom = c.y + tH / 2f
        )

        // ombra â€œappoggiata dentro lo slotâ€ (piÃ¹ evidente)
        drawRoundRect(
            color = shStrong.copy(alpha = if (isDark) 0.60f else 0.22f),
            topLeft = Offset(thumb.left + 3f, thumb.top + 5f),
            size = thumb.size,
            cornerRadius = tcr
        )

        // corpo thumb (grafite, non vetro)
        val thumbBody = Brush.linearGradient(
            colors = if (isDark) {
                listOf(
                    Color(0xFF2A3038).copy(alpha = 0.98f),
                    Color(0xFF12161C).copy(alpha = 0.98f),
                    Color(0xFF0A0D12).copy(alpha = 0.98f)
                )
            } else {
                listOf(
                    Color(0xFFF2F5F9).copy(alpha = 0.98f),
                    Color(0xFFD8E0EA).copy(alpha = 0.98f),
                    Color(0xFFBFCAD7).copy(alpha = 0.98f)
                )
            },
            start = Offset(thumb.left, thumb.top),
            end = Offset(thumb.left, thumb.bottom)
        )
        drawRoundRect(thumbBody, thumb.topLeft, thumb.size, tcr)

        // spalle laterali (undercut) -> look â€œcapâ€
        val cheekW = max(1.5f, tW * 0.06f)
        drawRoundRect(
            color = Color.Black.copy(alpha = if (isDark) 0.35f else 0.16f),
            topLeft = Offset(thumb.left, thumb.top + tH * 0.12f),
            size = Size(cheekW, tH * 0.76f),
            cornerRadius = CornerRadius(999f, 999f)
        )
        drawRoundRect(
            color = Color.Black.copy(alpha = if (isDark) 0.35f else 0.16f),
            topLeft = Offset(thumb.right - cheekW, thumb.top + tH * 0.12f),
            size = Size(cheekW, tH * 0.76f),
            cornerRadius = CornerRadius(999f, 999f)
        )

        // bevel netto del thumb
        val bevel = max(1.5f, w * 0.16f)
        drawRoundRect(
            color = hiStrong,
            topLeft = Offset(thumb.left - 1f, thumb.top - 1f),
            size = Size(thumb.width + 2f, thumb.height + 2f),
            cornerRadius = tcr,
            style = Stroke(width = bevel, cap = StrokeCap.Round)
        )
        drawRoundRect(
            color = shSoft,
            topLeft = Offset(thumb.left + 1.5f, thumb.top + 1.5f),
            size = thumb.size,
            cornerRadius = tcr,
            style = Stroke(width = bevel, cap = StrokeCap.Round)
        )

        // grip lines (incise)
        val lines = gripLines.coerceIn(0, 4)
        if (lines > 0) {
            val span = tW * 0.42f
            val startX = thumb.center.x - span / 2f
            val step = span / (lines + 1)
            val y1 = thumb.top + tH * 0.28f
            val y2 = thumb.bottom - tH * 0.28f
            val lineW = max(1f, w * 0.10f)
            for (i in 1..lines) {
                val x = startX + step * i
                // incisione scura
                drawLine(
                    Color.Black.copy(alpha = if (isDark) 0.55f else 0.22f),
                    Offset(x, y1),
                    Offset(x, y2),
                    strokeWidth = lineW,
                    cap = StrokeCap.Round
                )
                // micro highlight lato
                drawLine(
                    Color.White.copy(alpha = if (isDark) 0.12f else 0.18f),
                    Offset(x - 1f, y1),
                    Offset(x - 1f, y2),
                    strokeWidth = max(1f, lineW * 0.55f),
                    cap = StrokeCap.Round
                )
            }
        }

        // micro-led sul thumb (sottile, parte da 0)
        val microH = max(1.2f, w * 0.16f)
        val micro = Rect(
            left = thumb.left + tW * 0.18f,
            top = thumb.center.y - microH / 2f,
            right = thumb.right - tW * 0.18f,
            bottom = thumb.center.y + microH / 2f
        )
        val microA = (ledAlpha * 0.95f).coerceIn(0f, 1f)
        if (microA > 0f) {
            drawRoundRect(
                color = accent.copy(alpha = microA * 0.12f),
                topLeft = Offset(micro.left - 2f, micro.top - 2f),
                size = Size(micro.width + 4f, micro.height + 4f),
                cornerRadius = CornerRadius(999f, 999f)
            )
            drawRoundRect(
                color = accent.copy(alpha = microA),
                topLeft = micro.topLeft,
                size = micro.size,
                cornerRadius = CornerRadius(999f, 999f)
            )
        }
    }
}