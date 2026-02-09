# Changelog

All notable changes to NeoUI Library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Planned
- NeoSwitch component (toggle switch)
- NeoSlider component (horizontal slider)
- NeoMeter component (VU meter display)
- Additional LED animation modes
- Color scheme presets
- Haptic feedback support

---

## [1.0.0] - 2026-02-09

### Added
- **Core Components**
    - `NeoKnob`: Rotary knob with LED ring indicator
    - `NeoFader`: Vertical/horizontal fader with LED track
    - `NeoTimeline`: Progress/seek bar with LED fill
    - `NeumorphicButton`: Button with animated LED border
    - `NeumorphicPanel`: Sunken panel container

- **Theme System**
    - `NeoUITheme`: Main theme wrapper with dark/light mode support
    - `NeoTheme`: Theme access object for colors
    - Custom accent color support

- **LED System**
    - `LedMode`: OFF, SOLID, BREATH, SWEEP, PULSE
    - `LedStyle`: CONTINUOUS, SEGMENTED
    - `LedConfig`: Configurable LED behavior
    - Smooth animations with customizable timing

- **Responsive Design**
    - Automatic scaling for phone/tablet (scale factors: 0.9-1.8x)
    - `NeoSize`: SMALL, MEDIUM, LARGE presets
    - `DeviceConfig`: Device detection and scaling system
    - `NeoDefaults`: Centralized responsive defaults

- **Utilities**
    - `NeumorphicModifier`: Raised/Sunken/Flat styles
    - `NeumorphicUtils`: Shadow calculation helpers
    - `UiState`: Component state management (DISABLED, IDLE, ACTIVE, CONNECTING, ALERT)

### Design Decisions
- **Calibrated neomorphic shadows**: Fixed elevation values for consistent effect
- **Hardware-inspired aesthetics**: All components mimic professional audio equipment
- **Touch-friendly**: Minimum 48dp touch targets
- **Performance-optimized**: Canvas-based rendering, minimal recomposition

### Technical
- **Minimum SDK**: 26 (Android 8.0)
- **Kotlin**: 2.0.21
- **Compose**: BOM 2024.02.00
- **License**: Dual (MIT + Commercial)

---

## Version History

### Version Numbering
- **Major (X.0.0)**: Breaking API changes, major new features
- **Minor (1.X.0)**: New components, backward-compatible features
- **Patch (1.0.X)**: Bug fixes, performance improvements

### Upgrade Guide
When upgrading major versions, check migration guides in the documentation.

---

## Links
- [Full Documentation](README.md)
- [License Information](LICENSE.txt)
- [Contributing Guide](CONTRIBUTING.md)