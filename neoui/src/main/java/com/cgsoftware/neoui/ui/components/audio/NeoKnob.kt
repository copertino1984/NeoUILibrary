package com.cgsoftware.neoui.ui.components.audio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import com.cgsoftware.neoui.ui.theme.NeoDefaults
import com.cgsoftware.neoui.ui.theme.NeoSize
import com.cgsoftware.neoui.ui.theme.NeoTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
@Composable
fun NeoKnob(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,  // <-- RINOMINATO da size a componentSize
    enabled: Boolean = true,
    accentColor: Color = NeoTheme.colors.accent,
    sensitivity: Float = 0.0018f,
    startAngleDeg: Float = 135f,
    sweepAngleDeg: Float = 270f,
    tickCount: Int = 21
) {
    val v = value.coerceIn(0f, 1f)
    val current by rememberUpdatedState(v)
    val isDark = NeoTheme.colors.isDark

    val knobSize = NeoDefaults.Knob.size(componentSize)  // <-- usa componentSize
    Canvas(
        modifier = modifier
            .size(knobSize)
            .pointerInput(enabled) {
            if (!enabled) return@pointerInput
            detectDragGestures { change, drag ->
                change.consume()
                val nv = (current + (-drag.y * sensitivity)).coerceIn(0f, 1f)
                onValueChange(nv)
            }
        }
    ) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val r = min(size.width, size.height) / 2f

        val bodyR = r * 0.98f
        val innerR = r * 0.62f
        val ringR = r * 0.78f
        val ringW = maxOf(1.2f, r * 0.035f)

        // FIX GAP: compensa l'allargamento dei cap Round sugli archi spessi
        val capPadDeg = 7f
        val arcStart = startAngleDeg + capPadDeg
        val arcSweep = sweepAngleDeg - capPadDeg * 2f


        // body graphite / light
        val bodyBrush = Brush.radialGradient(
            colors = if (isDark) {
                listOf(
                    Color(0xFF2A2F36).copy(alpha = 0.95f),
                    Color(0xFF14181E).copy(alpha = 0.98f),
                    Color(0xFF0C0F13).copy(alpha = 1f)
                )
            } else {
                listOf(
                    Color(0xFFF0F3F7).copy(alpha = 0.98f),
                    Color(0xFFD7DEE7).copy(alpha = 0.98f),
                    Color(0xFFBFC8D3).copy(alpha = 0.98f)
                )
            },
            center = Offset(cx - r * 0.18f, cy - r * 0.18f),
            radius = r * 1.25f
        )
        drawCircle(bodyBrush, bodyR, Offset(cx, cy))

        // bevel
        drawCircle(
            color = Color.White.copy(alpha = if (isDark) 0.10f else 0.28f),
            radius = bodyR,
            center = Offset(cx - 2f, cy - 2f),
            style = Stroke(width = maxOf(1f, r * 0.06f))
        )
        drawCircle(
            color = Color.Black.copy(alpha = if (isDark) 0.25f else 0.10f),
            radius = bodyR,
            center = Offset(cx + 2f, cy + 2f),
            style = Stroke(width = maxOf(1f, r * 0.06f))
        )


// inner lens (Opzione A): base + lente + highlight
        val innerBase = if (isDark) Color(0xFF1B2027) else Color(0xFFD6DDE6)
        drawCircle(
            color = innerBase.copy(alpha = if (isDark) 0.55f else 0.65f),
            radius = innerR,
            center = Offset(cx, cy)
        )

        // --- NOTCH INCISO (segue lâ€™angolo del valore) ---
        val notchAngleDeg = arcStart + arcSweep * v
// posizione notch sulla faccia (non sul ring esterno)
        val notchR = innerR * 0.78f
        val notchLen = r * 0.22f
        val notchH = maxOf(2f, r * 0.050f)

// colori incisione (scuro + highlight)
        val notchDark = Color.Black.copy(alpha = if (isDark) 0.55f else 0.28f)
        val notchLight = Color.White.copy(alpha = if (isDark) 0.10f else 0.18f)

        rotate(degrees = notchAngleDeg, pivot = Offset(cx, cy)) {
            // 1) â€œscavoâ€ scuro
            val topLeft = Offset(cx + notchR - notchLen / 2f, cy - notchH / 2f)
            drawRoundRect(
                color = notchDark,
                topLeft = topLeft,
                size = Size(notchLen, notchH),
                cornerRadius = CornerRadius(999f, 999f)
            )

            // 2) micro highlight sopra (effetto incisione)
            drawRoundRect(
                color = notchLight,
                topLeft = Offset(topLeft.x, topLeft.y - notchH * 0.35f),
                size = Size(notchLen, maxOf(1f, notchH * 0.35f)),
                cornerRadius = CornerRadius(999f, 999f)
            )

            // 3) bordo interno piÃ¹ scuro per profonditÃ
            drawRoundRect(
                color = Color.Black.copy(alpha = if (isDark) 0.35f else 0.18f),
                topLeft = Offset(topLeft.x + 0.8f, topLeft.y + 0.8f),
                size = Size(notchLen, notchH),
                cornerRadius = CornerRadius(999f, 999f),
                style = Stroke(width = maxOf(1f, notchH * 0.25f), cap = StrokeCap.Round)
            )
        }
        // angolo corrente coerente col ring (usa arcStart/arcSweep)
        val dotAngle = (arcStart + arcSweep * v) * (PI.toFloat() / 180f)

// centro lente mobile
        val lensMove = innerR * 0.10f
        val lensCenter = Offset(
            cx - r * 0.16f + lensMove * cos(dotAngle.toDouble()).toFloat(),
            cy - r * 0.16f + lensMove * sin(dotAngle.toDouble()).toFloat()
        )

        val lensBrush = Brush.radialGradient(
            colors = if (isDark) {
                listOf(
                    Color.White.copy(alpha = 0.14f),
                    Color(0xFF0B0E12).copy(alpha = 0.55f)
                )
            } else {
                listOf(
                    Color.White.copy(alpha = 0.35f),
                    Color(0xFF9AA6B4).copy(alpha = 0.35f)
                )
            },
            center = lensCenter,
            radius = innerR * 1.25f
        )
        drawCircle(
            brush = lensBrush,
            radius = innerR * 0.98f,
            center = Offset(cx, cy)
        )

        // rim neumorfico interno (luce + ombra) per effetto 3D
        val rimW = maxOf(1f, r * 0.035f)

        drawCircle(
            color = Color.White.copy(alpha = if (isDark) 0.10f else 0.22f),
            radius = innerR * 0.985f,
            center = Offset(cx - 2f, cy - 2f),
            style = Stroke(width = rimW)
        )

        drawCircle(
            color = Color.Black.copy(alpha = if (isDark) 0.30f else 0.14f),
            radius = innerR * 0.985f,
            center = Offset(cx + 2f, cy + 2f),
            style = Stroke(width = rimW)
        )

        drawCircle(
            color = Color.White.copy(alpha = if (isDark) 0.06f else 0.10f),
            radius = innerR * 0.90f,
            center = Offset(cx, cy),
            style = Stroke(width = maxOf(1f, r * 0.012f))
        )


// micro inner ring per â€œmagiaâ€ (sottile, elegante)
        drawCircle(
            color = Color.White.copy(alpha = if (isDark) 0.06f else 0.10f),
            radius = innerR * 0.92f,
            center = Offset(cx, cy),
            style = Stroke(width = maxOf(1f, r * 0.012f))
        )


        // tick marks esterni
        val ticks = tickCount.coerceIn(8, 41)
        val tickR1 = r * 1.05f
        val tickR2 = r * 1.12f
        for (i in 0 until ticks) {
            val t = i.toFloat() / (ticks - 1).toFloat()
            val ang = (arcStart + arcSweep * t) * (PI.toFloat() / 180f)
            val x1 = cx + tickR1 * cos(ang.toDouble()).toFloat()
            val y1 = cy + tickR1 * sin(ang.toDouble()).toFloat()
            val x2 = cx + tickR2 * cos(ang.toDouble()).toFloat()
            val y2 = cy + tickR2 * sin(ang.toDouble()).toFloat()

            val major = (i % 5 == 0)
            drawLine(
                color = (if (isDark) Color.White else Color.Black).copy(alpha = if (major) 0.22f else 0.14f),
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = if (major) maxOf(1f, r * 0.020f) else maxOf(1f, r * 0.014f)
            )
        }


        // LED ring (solo arco, tronco sotto)
        val ledAlpha = (v * v).coerceIn(0.03f, 1f)   // gamma 2.0
        // glow largo
        drawArc(
            color = accentColor.copy(alpha = ledAlpha * 0.18f),
            startAngle = arcStart,
            sweepAngle = arcSweep,
            useCenter = false,
            topLeft = Offset(cx - ringR, cy - ringR),
            size = androidx.compose.ui.geometry.Size(ringR * 2, ringR * 2),
            style = Stroke(width = ringW * 3.2f, cap = StrokeCap.Butt)
        )
        // glow medio
        drawArc(
            color = accentColor.copy(alpha = ledAlpha * 0.28f),
            startAngle = arcStart,
            sweepAngle = arcSweep,
            useCenter = false,
            topLeft = Offset(cx - ringR, cy - ringR),
            size = androidx.compose.ui.geometry.Size(ringR * 2, ringR * 2),
            style = Stroke(width = ringW * 2.0f, cap = StrokeCap.Butt)
        )
        // core ring
        drawArc(
            color = accentColor.copy(alpha = ledAlpha),
            startAngle = arcStart,
            sweepAngle = arcSweep,
            useCenter = false,
            topLeft = Offset(cx - ringR, cy - ringR),
            size = androidx.compose.ui.geometry.Size(ringR * 2, ringR * 2),
            style = Stroke(width = ringW, cap = StrokeCap.Round)
        )

        // progress â€œsul ringâ€
        drawArc(
            color = accentColor.copy(alpha = (ledAlpha * 1.0f).coerceIn(0f, 1f)),
            startAngle = arcStart,
            sweepAngle = arcSweep * v,
            useCenter = false,
            topLeft = Offset(cx - ringR, cy - ringR),
            size = androidx.compose.ui.geometry.Size(ringR * 2, ringR * 2),
            style = Stroke(width = ringW, cap = StrokeCap.Round)
        )

        // --- THUMB PRO: marker a capsula (tipo hardware), non cerchietto ---
        val angleDeg = (arcStart + arcSweep * v)   // stesso usato per ring/progress
        val indLen = r * 0.22f
        val indH   = maxOf(1.2f, r * 0.028f)
        val px = cx + ringR * 1.01f   // posizione su asse X, poi ruotiamo
        val py = cy

        rotate(degrees = angleDeg, pivot = Offset(cx, cy)) {
            // glow dietro
            drawRoundRect(
                color = accentColor.copy(alpha = 0.22f),
                topLeft = Offset(px - indLen/2f, py - indH/2f),
                size = Size(indLen, indH),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(999f, 999f),
                style = Stroke(width = indH * 1.25f, cap = StrokeCap.Round)
            )

            // corpo marker (capsula piena)
            drawRoundRect(
                color = accentColor.copy(alpha = 0.95f),
                topLeft = Offset(px - indLen/2f, py - indH/2f),
                size = Size(indLen, indH),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(999f, 999f)
            )

            // highlight sottile sopra
            drawRoundRect(
                color = Color.White.copy(alpha = if (isDark) 0.22f else 0.30f),
                topLeft = Offset(px - indLen/2f, py - indH/2f),
                size = Size(indLen, maxOf(1f, indH * 0.35f)),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(999f, 999f)
            )

            // micro ombra sotto
            drawRoundRect(
                color = Color.Black.copy(alpha = if (isDark) 0.25f else 0.14f),
                topLeft = Offset(px - indLen/2f, py + indH*0.10f),
                size = Size(indLen, maxOf(1f, indH * 0.55f)),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(999f, 999f)
            )
        }

    }
}