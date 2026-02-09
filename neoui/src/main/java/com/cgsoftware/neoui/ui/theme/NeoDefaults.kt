package com.cgsoftware.neoui.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cgsoftware.neoui.ui.utils.rememberDeviceConfig

enum class NeoSize { SMALL, MEDIUM, LARGE }

object NeoDefaults {

    // KNOB
    object Knob {
        @Composable
        fun size(size: NeoSize = NeoSize.MEDIUM): Dp {
            val config = rememberDeviceConfig()
            val base = when (size) {
                NeoSize.SMALL -> 64.dp
                NeoSize.MEDIUM -> 96.dp
                NeoSize.LARGE -> 140.dp
            }
            return base * config.scaleFactor
        }

        const val SENSITIVITY = 0.0018f
        const val START_ANGLE = 135f
        const val SWEEP_ANGLE = 270f
        const val TICK_COUNT = 21
    }

    // FADER
    object Fader {
        @Composable
        fun height(size: NeoSize = NeoSize.MEDIUM): Dp {
            val config = rememberDeviceConfig()
            val base = when (size) {
                NeoSize.SMALL -> 200.dp
                NeoSize.MEDIUM -> 280.dp
                NeoSize.LARGE -> 400.dp
            }
            return base * config.scaleFactor
        }

        @Composable
        fun trackThickness(): Dp {
            val config = rememberDeviceConfig()
            return 18.dp * config.scaleFactor
        }

        const val GRIP_LINES = 3
    }

    // TIMELINE
    object Timeline {
        @Composable
        fun height(size: NeoSize = NeoSize.MEDIUM): Dp {
            val config = rememberDeviceConfig()
            val base = when (size) {
                NeoSize.SMALL -> 32.dp
                NeoSize.MEDIUM -> 48.dp
                NeoSize.LARGE -> 64.dp
            }
            return base * config.scaleFactor
        }
    }

    // BUTTON
    object Button {
        @Composable
        fun minHeight(size: NeoSize = NeoSize.MEDIUM): Dp {
            val config = rememberDeviceConfig()
            val base = when (size) {
                NeoSize.SMALL -> 48.dp
                NeoSize.MEDIUM -> 56.dp
                NeoSize.LARGE -> 72.dp
            }
            return base * config.scaleFactor
        }

        @Composable
        fun cornerRadius(): Dp {
            val config = rememberDeviceConfig()
            return 12.dp * config.scaleFactor
        }

        @Composable
        fun elevation(): Dp {
            val config = rememberDeviceConfig()
            return 3.dp * config.scaleFactor
        }

        @Composable
        fun contentPadding(): Dp {
            val config = rememberDeviceConfig()
            return 12.dp * config.scaleFactor
        }
    }

    // PANEL
    object Panel {
        @Composable
        fun cornerRadius(): Dp {
            val config = rememberDeviceConfig()
            return 16.dp * config.scaleFactor
        }

        @Composable
        fun elevation(): Dp {
            val config = rememberDeviceConfig()
            return 3.dp * config.scaleFactor
        }

        @Composable
        fun contentPadding(): Dp {
            val config = rememberDeviceConfig()
            return 12.dp * config.scaleFactor
        }
    }

    // LED (scalati automaticamente)
    object Led {
        @Composable
        fun width(): Dp {
            val config = rememberDeviceConfig()
            return 1.dp * config.scaleFactor
        }

        @Composable
        fun glow(): Dp {
            val config = rememberDeviceConfig()
            return 2.dp * config.scaleFactor
        }

        const val INSET = 0f
        const val GLOW_ALPHA = 0.25f
        const val SOLID_ALPHA = 0.90f
        const val BREATH_MS = 1600
        const val PULSE_MS = 220
        const val SWEEP_MS = 600
        const val SEGMENTS = 12
    }

    // SPACING SYSTEM
    object Spacing {
        @Composable
        fun xs(): Dp {
            val config = rememberDeviceConfig()
            return 4.dp * config.scaleFactor
        }

        @Composable
        fun sm(): Dp {
            val config = rememberDeviceConfig()
            return 8.dp * config.scaleFactor
        }

        @Composable
        fun md(): Dp {
            val config = rememberDeviceConfig()
            return 16.dp * config.scaleFactor
        }

        @Composable
        fun lg(): Dp {
            val config = rememberDeviceConfig()
            return 24.dp * config.scaleFactor
        }

        @Composable
        fun xl(): Dp {
            val config = rememberDeviceConfig()
            return 32.dp * config.scaleFactor
        }
    }
}