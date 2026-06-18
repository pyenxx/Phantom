# Phantom

A feature-packed addon for [Cactus](https://modrinth.com/mod/cactus) — the Minecraft utility mod.

Phantom extends Cactus with additional modules, HUD elements, and quality-of-life features for both gameplay and server administration.

---

## Features

### Modules

| Module | Description |
|--------|-------------|
| **Custom Name Tag** | Adds a custom text line to your own name tag. Includes a Y-offset slider for precise vertical positioning (no more clunky above/below toggle). Only visible to you. |
| **Hit Effect** | Spawns particles when you hit entities. Choose from over 20 different particle effects. |
| **Hit Sound** | Plays a sound when you hit entities. Choose from a variety of sounds including cat noises, explosions, and more. |

### HUD Elements

| Element | Description |
|---------|-------------|
| **Server IP** | Displays the current server IP address directly on your HUD. Shows "Singleplayer" when playing offline. Fully positionable via the Cactus HUD editor. |

---

## Requirements

- Minecraft **1.21.11**
- [Fabric Loader](https://fabricmc.net/) **0.18.4+**
- [Cactus](https://modrinth.com/mod/cactus) **0.12.3+**

### Recommended

- [Fabric API](https://modrinth.com/mod/fabric-api) **0.141.3+**

---

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.11
2. Download [Cactus](https://modrinth.com/mod/cactus) and place it in your `mods` folder
3. Download the Phantom JAR from [Releases](https://github.com/pyenxx/Phantom/releases) and place it in your `mods` folder
4. Launch the game

---

## Usage

### Modules
Open the Cactus menu (default: `RShift`), navigate to the **Phantom** category, and toggle any module:

- **Custom Name Tag** — enter your text, adjust the Y offset with the slider (values from `-3.00` to `3.00`, default `0.35` blocks)
- **Hit Effect** — select a particle type from the dropdown
- **Hit Sound** — select a sound from the dropdown

### HUD Elements
1. Open the Cactus menu → **HUD Editor**
2. Click the **+** button
3. Select **Server IP** from the list
4. Drag and position it anywhere on your screen
5. Customize colors, background style, and shadow settings in the element properties

---

## Building from Source

```bash
git clone https://github.com/pyenxx/Phantom.git
cd Phantom
./gradlew build
```

The compiled JAR will be in `build/libs/`.

---

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

> **Note:** Some translations in this project were generated with the assistance of AI.
