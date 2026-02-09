package com.cgsoftware.neoui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cgsoftware.neoui.ui.components.audio.*
import com.cgsoftware.neoui.ui.components.button.NeumorphicButton
import com.cgsoftware.neoui.ui.components.common.UiState
import com.cgsoftware.neoui.ui.components.led.LedConfig
import com.cgsoftware.neoui.ui.components.led.LedStyle
import com.cgsoftware.neoui.ui.components.panel.NeumorphicPanel
import com.cgsoftware.neoui.ui.theme.NeoSize
import com.cgsoftware.neoui.ui.theme.NeoUITheme
import androidx.compose.foundation.background
import com.cgsoftware.neoui.ui.theme.NeoTheme

@Composable
fun NeoUITestScreen() {
    var volume by remember { mutableFloatStateOf(0.5f) }
    var bass by remember { mutableFloatStateOf(0.7f) }
    var treble by remember { mutableFloatStateOf(0.3f) }
    var progress by remember { mutableFloatStateOf(0.4f) }
    var buttonState by remember { mutableStateOf(UiState.IDLE) }

    // CONTROLLI TEMA
    var isDarkTheme by remember { mutableStateOf(true) }
    var accentHue by remember { mutableFloatStateOf(0.5f) } // 0-1 per HSL

    // Calcola accent color da Hue
    val accentColor = Color.hsl(accentHue * 360f, 1f, 0.5f)

    val textColor = if (isDarkTheme) Color.White else Color.Black

    NeoUITheme(darkTheme = isDarkTheme, accentColor = accentColor) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(NeoTheme.colors.background)  // <-- AGGIUNGI BACKGROUND
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // ===== TITLE =====
                Text(
                    text = "NeoUI Component Test",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                // ===== THEME CONTROLS =====
                NeumorphicPanel(
                    componentSize = NeoSize.MEDIUM,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("THEME SETTINGS", color = textColor, fontSize = 18.sp)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NeumorphicButton(
                                onClick = { isDarkTheme = !isDarkTheme },
                                componentSize = NeoSize.SMALL,
                                uiState = UiState.IDLE
                            ) {
                                Text(
                                    if (isDarkTheme) "DARK" else "LIGHT",
                                    color = textColor
                                )
                            }
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Accent Color", color = textColor, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            NeoKnob(
                                value = accentHue,
                                onValueChange = { accentHue = it },
                                componentSize = NeoSize.MEDIUM,

                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Hue: ${(accentHue * 360).toInt()}°",
                                color = textColor,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                // ===== KNOBS SECTION =====
                NeumorphicPanel(
                    componentSize = NeoSize.MEDIUM,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("KNOBS", color = textColor, fontSize = 18.sp)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                NeoKnob(
                                    value = volume,
                                    onValueChange = { volume = it },
                                    componentSize = NeoSize.SMALL,
                                    accentColor = accentColor
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Small", color = textColor, fontSize = 12.sp)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                NeoKnob(
                                    value = bass,
                                    onValueChange = { bass = it },
                                    componentSize = NeoSize.MEDIUM,
                                    accentColor = accentColor
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Medium", color = textColor, fontSize = 12.sp)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                NeoKnob(
                                    value = treble,
                                    onValueChange = { treble = it },
                                    componentSize = NeoSize.LARGE,
                                    accentColor = accentColor
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Large", color = textColor, fontSize = 12.sp)
                            }
                        }
                    }
                }

                // ===== FADERS SECTION =====
                NeumorphicPanel(
                    componentSize = NeoSize.MEDIUM,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("FADERS", color = textColor, fontSize = 18.sp)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                NeoFader(
                                    value = volume,
                                    onValueChange = { volume = it },
                                    componentSize = NeoSize.SMALL,
                                    accentColor = accentColor
                                )
                                Text("Small", color = textColor, fontSize = 12.sp)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                NeoFader(
                                    value = bass,
                                    onValueChange = { bass = it },
                                    componentSize = NeoSize.MEDIUM,
                                    accentColor = accentColor
                                )
                                Text("Medium", color = textColor, fontSize = 12.sp)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                NeoFader(
                                    value = treble,
                                    onValueChange = { treble = it },
                                    componentSize = NeoSize.LARGE,
                                    accentColor = accentColor
                                )
                                Text("Large", color = textColor, fontSize = 12.sp)
                            }
                        }
                    }
                }

                // ===== TIMELINE SECTION =====
                NeumorphicPanel(
                    componentSize = NeoSize.MEDIUM,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("TIMELINE", color = textColor, fontSize = 18.sp)

                        NeoTimeline(
                            progress = progress,
                            onSeek = { progress = it },
                            componentSize = NeoSize.SMALL,
                            accentColor = accentColor
                        )

                        NeoTimeline(
                            progress = progress,
                            onSeek = { progress = it },
                            componentSize = NeoSize.MEDIUM,
                            accentColor = accentColor
                        )

                        NeoTimeline(
                            progress = progress,
                            onSeek = { progress = it },
                            componentSize = NeoSize.LARGE,
                            accentColor = accentColor
                        )
                    }
                }

                // ===== BUTTONS SECTION =====
                NeumorphicPanel(
                    componentSize = NeoSize.MEDIUM,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("BUTTONS", color = textColor, fontSize = 18.sp)

                        NeumorphicButton(
                            onClick = {
                                buttonState = when(buttonState) {
                                    UiState.IDLE -> UiState.ACTIVE
                                    UiState.ACTIVE -> UiState.CONNECTING
                                    UiState.CONNECTING -> UiState.ALERT
                                    UiState.ALERT -> UiState.IDLE
                                    else -> UiState.IDLE
                                }
                            },
                            componentSize = NeoSize.SMALL,
                            uiState = buttonState,
                            accentColor = accentColor,
                            ledConfig = LedConfig(style = LedStyle.CONTINUOUS)
                        ) {
                            Text("CONTINUOUS LED", color = textColor)
                        }

                        NeumorphicButton(
                            onClick = { },
                            componentSize = NeoSize.MEDIUM,
                            uiState = UiState.ACTIVE,
                            accentColor = accentColor,
                            ledConfig = LedConfig(style = LedStyle.SEGMENTED, segments = 16)
                        ) {
                            Text("SEGMENTED LED", color = textColor)
                        }

                        NeumorphicButton(
                            onClick = { },
                            componentSize = NeoSize.LARGE,
                            uiState = UiState.CONNECTING,
                            accentColor = accentColor
                        ) {
                            Text("CONNECTING", color = textColor)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}