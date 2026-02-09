package com.cgsoftware.neoui.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class DeviceType { PHONE, TABLET }

data class DeviceConfig(
    val type: DeviceType,
    val screenWidthDp: Int,
    val screenHeightDp: Int,
    val scaleFactor: Float
) {
    companion object {
        private const val TABLET_THRESHOLD_DP = 600

        fun calculate(widthDp: Int, heightDp: Int): DeviceConfig {
            val type = if (widthDp >= TABLET_THRESHOLD_DP) DeviceType.TABLET else DeviceType.PHONE

            // Scale factor: phone=1.0, tablet=1.4-1.8 (proporzionale)
            val scaleFactor = when {
                widthDp < 400 -> 0.9f          // phone piccoli
                widthDp < TABLET_THRESHOLD_DP -> 1.0f  // phone normali
                widthDp < 800 -> 1.4f          // tablet 7-8"
                widthDp < 1000 -> 1.6f         // tablet 10"
                else -> 1.8f                   // tablet 12"+
            }

            return DeviceConfig(type, widthDp, heightDp, scaleFactor)
        }
    }
}

@Composable
fun rememberDeviceConfig(): DeviceConfig {
    val configuration = LocalConfiguration.current
    return remember(configuration.screenWidthDp, configuration.screenHeightDp) {
        DeviceConfig.calculate(
            configuration.screenWidthDp,
            configuration.screenHeightDp
        )
    }
}

@Composable
fun Dp.scaled(): Dp {
    val config = rememberDeviceConfig()
    return this * config.scaleFactor
}