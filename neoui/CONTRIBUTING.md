# Contributing to NeoUI Library

Thank you for your interest in contributing to NeoUI! This document provides guidelines for contributing to the project.

## Table of Contents
- [Code of Conduct](#code-of-conduct)
- [How to Contribute](#how-to-contribute)
- [Development Setup](#development-setup)
- [Component Design Guidelines](#component-design-guidelines)
- [Pull Request Process](#pull-request-process)
- [Coding Standards](#coding-standards)
- [Testing](#testing)

---

## Code of Conduct

### Our Pledge
- Be respectful and inclusive
- Focus on constructive feedback
- Prioritize the project's goals and user experience
- Help newcomers learn and contribute

### Unacceptable Behavior
- Harassment or discrimination
- Trolling or inflammatory comments
- Publishing private information
- Unprofessional conduct

---

## How to Contribute

### Reporting Bugs
1. **Check existing issues** to avoid duplicates
2. **Use the bug report template**
3. **Provide minimal reproducible example**
4. **Include environment details**: Android version, device, library version

**Bug Report Template:**
```
**Description:**
Clear description of the bug

**Steps to Reproduce:**
1. Step one
2. Step two
3. ...

**Expected Behavior:**
What should happen

**Actual Behavior:**
What actually happens

**Environment:**
- NeoUI version: 1.0.0
- Android version: 14
- Device: Pixel 8
- Compose version: 2024.02.00

**Screenshots/Video:**
If applicable
```

### Suggesting Features
1. **Check roadmap** in CHANGELOG.md
2. **Open a discussion** before implementing large features
3. **Explain use case** and benefit to users
4. **Consider API consistency** with existing components

### Contributing Code
1. **Fork the repository**
2. **Create a feature branch**: `feature/neo-switch`
3. **Follow coding standards** (see below)
4. **Test thoroughly** on phone and tablet
5. **Submit pull request**

---

## Development Setup

### Prerequisites
- Android Studio Ladybug or newer
- JDK 17
- Android SDK 26+

### Clone and Build
```bash
git clone https://github.com/your-username/NeoUILibrary.git
cd NeoUILibrary
./gradlew :neoui:build
```

### Project Structure
```
neoui/
├── ui/
│   ├── components/        # UI components
│   │   ├── audio/         # Audio controls (knob, fader, timeline)
│   │   ├── button/        # Buttons
│   │   ├── led/           # LED system
│   │   └── panel/         # Panels
│   ├── theme/             # Theme and defaults
│   └── utils/             # Utilities (neumorphic, device config)
```

### Running Tests
```bash
./gradlew :neoui:test
./gradlew :app:connectedAndroidTest  # Run TestScreen
```

---

## Component Design Guidelines

### Neomorphic Principles
1. **Same base color**: Component color = background color
2. **Shadow-based depth**: Use light/dark shadows for 3D effect
3. **Calibrated values**: Never expose elevation/cornerRadius to users
4. **Subtle effects**: Avoid excessive shadows or colors

### Responsive Design
1. **Use NeoDefaults**: All sizing through centralized system
2. **Scale with device**: Multiply base values by `config.scaleFactor`
3. **componentSize parameter**: SMALL, MEDIUM, LARGE presets
4. **Touch targets**: Minimum 48dp interactive area

### LED System
1. **Use accent color**: LED color from `NeoTheme.colors.accent`
2. **Alpha-based**: 0.0 (off) to 1.0 (full intensity)
3. **LedMode integration**: Support OFF, SOLID, BREATH, SWEEP, PULSE
4. **Performance**: Use `remember` for animation states

### API Design
1. **Required parameters first**: `value`, `onValueChange`, `onClick`
2. **Modifier second**: Always `Modifier = Modifier`
3. **Optional parameters**: Sensible defaults, nullable if auto-calculated
4. **Composable content last**: Lambda parameters at the end

**Example:**
```kotlin
@Composable
fun NeoComponent(
    value: Float,                           // Required
    onValueChange: (Float) -> Unit,         // Required
    modifier: Modifier = Modifier,          // Modifier
    componentSize: NeoSize = NeoSize.MEDIUM,// Optional with default
    enabled: Boolean = true,                // Optional with default
    accentColor: Color = NeoTheme.colors.accent
)
```

---

## Pull Request Process

### Before Submitting
- [ ] Code compiles without warnings
- [ ] Tested on phone emulator (360dp width)
- [ ] Tested on tablet emulator (1024dp width)
- [ ] Follows coding standards
- [ ] Documentation updated (README.md if new component)
- [ ] CHANGELOG.md updated

### PR Template
```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New component
- [ ] Enhancement
- [ ] Documentation
- [ ] Performance improvement

## Testing
- [ ] Phone (360dp)
- [ ] Tablet (1024dp)
- [ ] Dark theme
- [ ] Light theme

## Screenshots
Before/After screenshots if UI change

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] No breaking changes (or documented in CHANGELOG)
```

### Review Process
1. Maintainer reviews code within 1 week
2. Feedback addressed by contributor
3. Approved PRs merged to `main`
4. Included in next release

---

## Coding Standards

### Kotlin Style
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use Android Studio auto-formatter (Ctrl+Alt+L)
- Max line length: 120 characters

### Naming
- Components: `Neo` prefix (e.g., `NeoKnob`, `NeoFader`)
- Files: PascalCase matching component name
- Functions: camelCase
- Constants: UPPER_SNAKE_CASE

### Comments
- KDoc for public APIs
- Inline comments for complex logic
- Avoid obvious comments

**Example:**
```kotlin
/**
 * Rotary knob control with LED ring indicator.
 *
 * Automatically scales based on device size. Use [componentSize] for
 * size presets or [modifier] with explicit size for custom dimensions.
 *
 * @param value Current value (0.0 - 1.0)
 * @param onValueChange Callback when value changes
 * @param componentSize Size preset (SMALL, MEDIUM, LARGE)
 * @param accentColor LED color
 */
@Composable
fun NeoKnob(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    componentSize: NeoSize = NeoSize.MEDIUM,
    accentColor: Color = NeoTheme.colors.accent
) {
    // Implementation
}
```

### Performance
- Use `remember` for expensive calculations
- Avoid allocations in `Canvas` lambda
- Use `derivedStateOf` for computed values
- Profile with Android Studio Profiler

---

## Testing

### Manual Testing Checklist
- [ ] Component renders correctly
- [ ] Interaction works (tap, drag)
- [ ] Scales properly on tablet
- [ ] Works in dark/light theme
- [ ] LED animations smooth
- [ ] No visual glitches
- [ ] Accessible (TalkBack compatible)

### Test Screen
Use `app/TestScreen.kt` to verify components:
```kotlin
// Add your component to TestScreen
NeoYourComponent(
    value = testValue,
    onValueChange = { testValue = it },
    componentSize = NeoSize.MEDIUM
)
```

---

## Questions?

- **General questions**: Open a Discussion
- **Bug reports**: Open an Issue
- **Feature requests**: Open an Issue with `enhancement` label
- **Security issues**: Email directly to [copertino1984@gmail.com]

---

## Recognition

Contributors will be listed in:
- CHANGELOG.md for their contributions
- README.md if significant feature

Thank you for contributing to NeoUI! 🚀