# NeoUI Library

[![](https://jitpack.io/v/copertino1984/NeoUILibrary.svg)](https://jitpack.io/#copertino1984/NeoUILibrary)

Hardware-style neomorphic UI component library for Jetpack Compose.

![NeoUI Components](screenshot.png)

## ✨ Features

- 🎛️ **Professional Audio Controls**: Knobs, faders, timeline with hardware aesthetics
- 💡 **Animated LED System**: Continuous/segmented LEDs with BREATH, SWEEP, PULSE modes
- 📱 **Fully Responsive**: Auto-scales from phone to tablet (0.9x - 1.8x)
- 🎨 **Neomorphic Design**: Authentic raised/sunken effects
- ⚡ **Performance Optimized**: Canvas-based rendering
- 🌓 **Dark/Light Themes**: Full theme support

## 📦 Installation

Add JitPack repository:
```gradle
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add dependency:
```gradle
// app/build.gradle.kts
dependencies {
    implementation("com.github.copertino1984:NeoUILibrary:1.0.0")
}
```

## 🚀 Quick Start
```kotlin
@Composable
fun MyScreen() {
    NeoUITheme(darkTheme = true, accentColor = Color.Cyan) {
        var volume by remember { mutableFloatStateOf(0.5f) }
        
        Column {
            NeoKnob(
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

## 📚 Components

- **NeoKnob**: Rotary knob with LED ring
- **NeoFader**: Vertical/horizontal fader
- **NeoTimeline**: Progress/seek bar
- **NeumorphicButton**: Button with LED animations
- **NeumorphicPanel**: Container panel

[Full Documentation →](neoui/README.md)

## 📸 Screenshots

| Dark Theme | Light Theme | Tablet |
|------------|-------------|--------|
| ![Dark](screenshot_dark.png) | ![Light](screenshot_light.png) | ![Tablet](screenshot_tablet.png) |

## 📄 License

Dual-licensed:
- **MIT License**: Free for personal/open-source projects
- **Commercial License**: Required for commercial use

See [LICENSE.txt](neoui/LICENSE.txt) for details.

## 🤝 Contributing

Contributions welcome! See [CONTRIBUTING.md](neoui/CONTRIBUTING.md)

## 📧 Contact

**Giuseppe Ciccarese** - CG Software  
Email: [copertino1984@gmail.com]  
Website: [www.cgsoftware.net]

---

Made with ❤️ in Italy