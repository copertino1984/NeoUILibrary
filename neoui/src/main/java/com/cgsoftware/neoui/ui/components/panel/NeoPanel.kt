package com.cgsoftware.neoui.ui.components.panel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.cgsoftware.neoui.ui.theme.NeoDefaults
import com.cgsoftware.neoui.ui.theme.NeoSize
import com.cgsoftware.neoui.ui.theme.NeoTheme
import com.cgsoftware.neoui.ui.utils.NeumorphicStyle
import com.cgsoftware.neoui.ui.utils.neumorphic

@Composable
fun NeumorphicPanel(
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,  // <-- deve esserci questa riga
    //cornerRadius: Dp? = null,
    //elevation: Dp? = null,
    //contentPadding: Dp? = null,
    accentColor: Color = NeoTheme.colors.background,
    content: @Composable BoxScope.() -> Unit
) {
    val colors = NeoTheme.colors
    // Valori FISSI calibrati internamente (non esposti)
    val actualCornerRadius = NeoDefaults.Panel.cornerRadius()
    val actualElevation = NeoDefaults.Panel.elevation()
    val actualPadding = NeoDefaults.Panel.contentPadding()

    Box(
        modifier = modifier
            .neumorphic(
                lightShadowColor = colors.lightShadow,
                darkShadowColor = colors.darkShadow,
                backgroundColor = colors.background,
                cornerRadius = actualCornerRadius,
                elevation = actualElevation,
                style = NeumorphicStyle.Sunken,
                accentColor = accentColor
            )
            .padding(actualPadding),
        contentAlignment = Alignment.Center,
        content = content
    )
}