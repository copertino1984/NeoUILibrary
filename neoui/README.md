# NeoUI Library

Hardware-style neomorphic UI component library for Jetpack Compose.

## Installation

### Local Module (same project)
```gradle
// settings.gradle.kts
include(":neoui")

// app/build.gradle.kts
dependencies {
    implementation(project(":neoui"))
}
```

### Maven Local (for testing in other projects)
```bash
./gradlew :neoui:publishToMavenLocal
```
```gradle
// settings.gradle.kts
repositories {
    mavenLocal()
}

// app/build.gradle.kts
dependencies {
    implementation("com.cgsoftware:neoui:1.0.0")
}
```

## Quick Start
```kotlin
import com.cgsoftware.neoui.ui.theme.NeoUITheme
import com.cgsoftware.neoui.ui.components.audio.*
import com.cgsoftware.neoui.ui.components.button.NeumorphicButton
import com.cgsoftware.neoui.ui.components.panel.NeumorphicPanel

@Composable
fun MyApp() {
    NeoUITheme(darkTheme = true, accentColor = Color.Cyan) {
        var volume by remember { mutableFloatStateOf(0.5f) }
        
        Column {
            NeoKnob(
                value = volume,
                onValueChange = { volume = it }
            )
            
            NeoFader(
                value = volume,
                onValueChange = { volume = it }
            )
            
            NeumorphicButton(onClick = { }) {
                Text("PLAY")
            }
        }
    }
}
```

## Components

### Theme

#### NeoUITheme
Main theme wrapper. Must wrap all NeoUI components.
```kotlin
NeoUITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentColor: Color = Color(0xFF00E5FF),
    content: @Composable () -> Unit
)
```

**Parameters:**
- `darkTheme`: Light or dark mode
- `accentColor`: Accent color for LEDs and highlights

---

### Audio Controls

#### NeoKnob
Rotary knob control with LED ring indicator.
```kotlin
NeoKnob(
    value: Float,                           // 0.0 - 1.0
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,
    enabled: Boolean = true,
    accentColor: Color = NeoTheme.colors.accent,
    sensitivity: Float = 0.0018f,
    startAngleDeg: Float = 135f,
    sweepAngleDeg: Float = 270f,
    tickCount: Int = 21
)
```

**Sizes:**
- `NeoSize.SMALL`: 64dp (phone) → ~115dp (tablet)
- `NeoSize.MEDIUM`: 96dp (phone) → ~172dp (tablet)
- `NeoSize.LARGE`: 140dp (phone) → ~252dp (tablet)

**Example:**
```kotlin
var volume by remember { mutableFloatStateOf(0.7f) }

NeoKnob(
    value = volume,
    onValueChange = { volume = it },
    componentSize = NeoSize.LARGE,
    accentColor = Color.Red
)
```

---

#### NeoFader
Vertical or horizontal fader with LED track.
```kotlin
NeoFader(
    value: Float,                           // 0.0 - 1.0
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,
    enabled: Boolean = true,
    orientation: FaderOrientation = FaderOrientation.VERTICAL,
    accentColor: Color = NeoTheme.colors.accent,
    trackThickness: Dp? = null,             // auto-calculated
    gripLines: Int = 3
)
```

**Sizes (height for VERTICAL, width for HORIZONTAL):**
- `NeoSize.SMALL`: 200dp → ~360dp (tablet)
- `NeoSize.MEDIUM`: 280dp → ~504dp (tablet)
- `NeoSize.LARGE`: 400dp → ~720dp (tablet)

**Example:**
```kotlin
NeoFader(
    value = bass,
    onValueChange = { bass = it },
    orientation = FaderOrientation.HORIZONTAL,
    componentSize = NeoSize.SMALL
)
```

---

#### NeoTimeline
Horizontal progress/seek bar with LED fill.
```kotlin
NeoTimeline(
    progress: Float,                        // 0.0 - 1.0
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,
    enabled: Boolean = true,
    accentColor: Color = NeoTheme.colors.accent
)
```

**Sizes (height):**
- `NeoSize.SMALL`: 32dp → ~58dp (tablet)
- `NeoSize.MEDIUM`: 48dp → ~86dp (tablet)
- `NeoSize.LARGE`: 64dp → ~115dp (tablet)

**Example:**
```kotlin
NeoTimeline(
    progress = playbackPosition,
    onSeek = { seekTo(it) },
    modifier = Modifier.fillMaxWidth()
)
```

---

### Buttons & Panels

#### NeumorphicButton
Raised button with animated LED border.
```kotlin
NeumorphicButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,
    accentColor: Color = NeoTheme.colors.accent,
    uiState: UiState = UiState.IDLE,
    ledConfig: LedConfig = LedConfig(style = LedStyle.CONTINUOUS),
    content: @Composable RowScope.() -> Unit
)
```

**UI States:**
- `UiState.DISABLED`: No LED, grayed out
- `UiState.IDLE`: Solid LED at 35% intensity
- `UiState.ACTIVE`: Breathing LED animation
- `UiState.CONNECTING`: Sweeping LED animation
- `UiState.ALERT`: Pulsing LED animation

**LED Styles:**
- `LedStyle.CONTINUOUS`: Solid border
- `LedStyle.SEGMENTED`: Discrete LED segments

**Example:**
```kotlin
var state by remember { mutableStateOf(UiState.IDLE) }

NeumorphicButton(
    onClick = { state = UiState.ACTIVE },
    uiState = state,
    ledConfig = LedConfig(
        style = LedStyle.SEGMENTED,
        segments = 16
    )
) {
    Text("PLAY")
}
```

---

#### NeumorphicPanel
Sunken panel container for grouping controls.
```kotlin
NeumorphicPanel(
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,
    content: @Composable BoxScope.() -> Unit
)
```

**Example:**
```kotlin
NeumorphicPanel {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("VOLUME")
        NeoKnob(volume, { volume = it })
    }
}
```

---

## Responsive Design

All components automatically scale based on device size:
- **Phone (< 600dp)**: Scale factor 0.9 - 1.0
- **Tablet 7-8" (600-800dp)**: Scale factor 1.4
- **Tablet 10" (800-1000dp)**: Scale factor 1.6
- **Tablet 12"+ (> 1000dp)**: Scale factor 1.8

**Override with fixed size:**
```kotlin
NeoKnob(
    value = volume,
    onValueChange = { volume = it },
    modifier = Modifier.size(200.dp)  // ignores componentSize
)
```

---

## Design Principles

### Neomorphic Effect
- Components share the same base color as background
- Shadows create illusion of depth (raised/sunken)
- Never override `cornerRadius` or `elevation` - values are calibrated for optimal effect

### LED Behavior
- LEDs use accent color
- Alpha varies from 0.0 (off) to 1.0 (full)
- Animations: SOLID, BREATH, SWEEP, PULSE

### Touch Targets
- Minimum 48dp touch area (Material Design standard)
- All interactive components meet accessibility guidelines

---

## License

Dual-licensed:
- **MIT License**: Free for personal/open-source projects
- **Commercial License**: Required for commercial applications

Contact: [copertino1984@gmail.com]

---

## Credits

Developed by CICCARESE GIUSEPPE - CG SOFTWARE
NeoUI Library v1.0.0