package com.cgsoftware.neoui.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class NeumorphicStyle { Flat, Raised, Sunken }

fun Modifier.neumorphic(
    lightShadowColor: Color,
    darkShadowColor: Color,
    backgroundColor: Color,
    cornerRadius: Dp = 12.dp,
    elevation: Dp = 6.dp,
    style: NeumorphicStyle = NeumorphicStyle.Raised,
    accentColor: Color = backgroundColor
) = this.drawWithContent {

    val r = cornerRadius.toPx().coerceAtLeast(0f)
    val e = elevation.toPx().coerceAtLeast(0f)

    val roundedPath = Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(0f, 0f, size.width, size.height),
                cornerRadius = CornerRadius(r, r)
            )
        )
    }

    when (style) {
        NeumorphicStyle.Raised -> {
            // Ombre esterne: NON clipparle fuori, altrimenti spariscono
            drawIntoCanvas { canvas ->
                val paint = Paint()
                val offset = e * 1.5f
                val blur = e * 4f

                // Ombra scura (bottom-right)
                paint.asFrameworkPaint().apply {
                    color = android.graphics.Color.TRANSPARENT
                    setShadowLayer(
                        blur,
                        offset,
                        offset,
                        darkShadowColor.copy(alpha = 0.80f).toArgb()
                    )
                }
                canvas.drawRoundRect(0f, 0f, size.width, size.height, r, r, paint)

                // Ombra chiara (top-left)
                paint.asFrameworkPaint().apply {
                    color = android.graphics.Color.TRANSPARENT
                    setShadowLayer(
                        blur,
                        -offset,
                        -offset,
                        lightShadowColor.copy(alpha = 0.80f).toArgb()
                    )
                }
                canvas.drawRoundRect(0f, 0f, size.width, size.height, r, r, paint)
            }

            // corpo sopra le ombre
            drawRoundRect(
                color = backgroundColor,
                cornerRadius = CornerRadius(r, r)
            )
        }

        NeumorphicStyle.Sunken -> {

            // Base
            drawRoundRect(
                color = backgroundColor,
                cornerRadius = CornerRadius(r, r)
            )

            clipPath(roundedPath) {
                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    val blur = e * 3f
                    val offset = e * 1.2f

                    // Ombra scura interna (top-left)
                    paint.asFrameworkPaint().apply {
                        color = android.graphics.Color.TRANSPARENT
                        setShadowLayer(
                            blur,
                            -offset,
                            -offset,
                            darkShadowColor.copy(alpha = 0.6f).toArgb()
                        )
                    }
                    canvas.drawRoundRect(
                        0f, 0f, size.width, size.height,
                        r, r, paint
                    )

                    // Ombra chiara interna (bottom-right)
                    paint.asFrameworkPaint().apply {
                        color = android.graphics.Color.TRANSPARENT
                        setShadowLayer(
                            blur,
                            offset,
                            offset,
                            lightShadowColor.copy(alpha = 0.45f).toArgb()
                        )
                    }
                    canvas.drawRoundRect(
                        0f, 0f, size.width, size.height,
                        r, r, paint
                    )
                }
            }
        }


        NeumorphicStyle.Flat -> {
            drawRoundRect(
                color = backgroundColor,
                cornerRadius = CornerRadius(r, r)
            )
        }
    }

    drawContent()
}
