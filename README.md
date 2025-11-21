# Snake-Game

# 🐍 Snake Game (Java)

A classic Snake game built with Java Swing featuring custom graphics, sound effects, and smooth gameplay.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

<img width="746" height="785" alt="image" src="https://github.com/user-attachments/assets/b21401c6-ad27-4cac-a774-d3607664104e" />

## 🎮 Features

- **Custom Graphics** - Snake with animated head, eyes that follow movement direction, forked tongue, and scale patterns
- **Mouse Food** - Cute mouse icon instead of generic food block
- **Wrap-Around Movement** - Snake wraps to the opposite side instead of dying at walls
- **High Score Tracking** - Keeps track of your best score during the session
- **Sound Effects** - Audio feedback for eating, direction changes, and game over
- **Restart Functionality** - Press SPACE to restart after game over

## 📸 Screenshot

![Snake Game Demo](screenshot.png)

## 🚀 Getting Started

### Prerequisites

- Java JDK 8 or higher
- IDE (VS Code, IntelliJ, Eclipse) or command line

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/snake-java.git
   cd snake-java
   ```

2. Compile the Java files
   ```bash
   javac App.java SnakeGame.java
   ```

3. Run the game
   ```bash
   java App
   ```

## 🎵 Sound Effects (Optional)

To enable sound effects, add the following WAV files to the project root:

| File | Description |
|------|-------------|
| `eat.wav` | Plays when snake eats the mouse |
| `move.wav` | Plays when changing direction |
| `gameover.wav` | Plays when achieving a new high score |

**Important:** WAV files must be PCM 16-bit, 44100 Hz format. You can convert audio files at [online-audio-converter.com](https://online-audio-converter.com).

Free sound effects available at:
- [Freesound.org](https://freesound.org)
- [Mixkit.co](https://mixkit.co/free-sound-effects)
- [Pixabay Sound Effects](https://pixabay.com/sound-effects)

## 🕹️ Controls

| Key | Action |
|-----|--------|
| ↑ | Move Up |
| ↓ | Move Down |
| ← | Move Left |
| → | Move Right |
| SPACE | Restart (after game over) |

## 📁 Project Structure

```
snake-java/
├── App.java          # Main entry point, creates game window
├── SnakeGame.java    # Game logic, rendering, and controls
├── eat.wav           # Sound effect (optional)
├── move.wav          # Sound effect (optional)
├── gameover.wav      # Sound effect (optional)
└── README.md
```

## 🛠️ Built With

- **Java AWT** - Graphics rendering
- **Java Swing** - GUI components and game loop

## 📝 How It Works

1. **Game Loop** - Timer fires every 100ms to update game state and repaint
2. **Movement** - Snake head moves based on velocity, body segments follow
3. **Collision Detection** - Checks for food collision (grow) and self-collision (game over)
4. **Wrap-Around** - When snake exits one edge, it appears on the opposite side

## 🎯 Future Improvements

- [ ] Difficulty levels (adjustable speed)
- [ ] Pause functionality
- [ ] Background music
- [ ] Persistent high score (save to file)
- [ ] Different snake skins/themes
- [ ] Power-ups

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🙏 Acknowledgments

- Inspired by the classic Nokia Snake game
- Original tutorial by [Kenny Yip](https://youtu.be/Y62MJny9LHg)
