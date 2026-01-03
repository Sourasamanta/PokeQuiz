# 🧠 PokeQuiz — AI-Powered Pokémon Quiz App (Android | Kotlin | Jetpack Compose)

An **interactive Android quiz application** built using **Kotlin** and **Jetpack Compose**, featuring **AI-generated Pokémon questions using Google Gemini**, real-time score tracking, animated UI, and modern Android architecture patterns.

Designed to showcase **Android development fundamentals**, **state management**, **API integration**, and **production-ready UI practices** — ideal for **internship and junior Android developer roles**.

---

## 🚀 Overview

PokeQuiz is a modern quiz game that dynamically generates Pokémon trivia questions using **Google Gemini (Firebase AI)**.  
Users enter their name, watch an animated intro, answer AI-generated questions, and receive a detailed results summary with score feedback.

The project emphasizes:

- Declarative UI with **Jetpack Compose**
- Clean separation of **UI, navigation, and business logic**
- **Asynchronous AI API integration**
- Safe input handling and fallback logic
- Material 3–styled components

---

## 🎯 Key Features

- 🎮 **AI-Generated Quiz Questions**
  - Uses **Gemini 2.5 Flash** via Firebase AI
  - Generates **20 Pokémon questions per session**
  - Covers generations from **Kanto to Galar**
  - JSON parsing with structured validation

- 🧠 **Offline Fallback Mode**
  - Automatically switches to local question bank if internet is unavailable

- 🎥 **Animated Intro Screen**
  - Full-screen looping video using `VideoView`
  - Lifecycle-aware playback (pause/resume on background)

- 🧭 **Navigation with Arguments**
  - Name passed across screens using **Navigation Compose**
  - Result screen displays personalized feedback

- 🎨 **Custom Pokémon UI Theme**
  - Pokémon fonts, colors, and icons
  - Material 3 TopAppBar and BottomAppBar
  - Visual feedback for correct / wrong answers

- 📊 **Real-Time Score Tracking**
  - Live progress indicator
  - Final score summary with percentage and feedback message

- 🌐 **Dynamic Pokémon Sprites**
  - Loads Pokémon images via **PokeAPI** when internet is available

---

## 🧩 Architecture Highlights

- **Jetpack Compose**
  - Stateless composables where possible
  - State managed via `remember` and `mutableStateOf`

- **Navigation**
  - Typed routes with arguments
  - Clean screen separation (`Home`, `Quiz`, `Result`)

- **AI Integration**
  - Firebase AI + Gemini SDK
  - Structured prompt engineering
  - JSON response parsing with fallback handling

- **Lifecycle Awareness**
  - Handles app background/foreground transitions
  - Screen on/off broadcast handling

---

## 🖼️ Screenshots

### Home & Intro Screen

<p align="center">
  <img
    src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/PokeQuiz/PokeQuiz1.jpeg"
    width="240"
    alt="PokeQuiz Home Screen"
  />
</p>

---

### Quiz Gameplay

<p align="center">
  <img
    src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/PokeQuiz/PokeQuiz2.jpeg"
    width="240"
    alt="PokeQuiz Quiz Gameplay Screen"
  />
</p>

---

### Result Screen

<p align="center">
  <img
    src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/PokeQuiz/PokeQuiz3.jpeg"
    width="240"
    alt="PokeQuiz Result Screen"
  />
</p>

---

## 🎬 Demo (GIF)

### Full App Walkthrough

<p align="center">
  <img
    src="https://raw.githubusercontent.com/Sourasamanta/ScreenShots/main/PokeQuiz/PokeQuizDemo.gif"
    width="240"
    alt="PokeQuiz Full App Demo"
  />
</p>

<em>
Shows animated intro, AI question generation, gameplay flow, score tracking, and result screen.
</em>

---

## 🧪 Example Flow

1. User enters their name
2. Animated Pokémon intro video plays
3. Gemini AI generates quiz questions
4. User selects answers
5. Immediate visual feedback (correct / wrong)
6. Final score summary with percentage and message

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **Navigation:** Navigation Compose
- **AI:** Firebase AI + Google Gemini 2.5 Flash
- **Async:** Kotlin Coroutines
- **Image Loading:** Coil
- **Serialization:** Kotlinx Serialization
- **Platform:** Android

---

## ⚙️ Installation & Run

### Requirements
- Android Studio (latest stable)
- Android SDK
- Internet connection (optional – offline fallback available)

### Build
```bash
./gradlew clean assembleDebug
```

### Run

```bash
./gradlew installDebug
```

---

## ⚠️ Limitations

* Gemini responses depend on network availability
* No automated unit/UI tests yet
* Quiz difficulty not adaptive
* Currently Pokémon-specific only

---

## 🛣️ Future Improvements

* Difficulty scaling based on performance
* Leaderboard & local persistence
* UI & unit testing
* Accessibility improvements
* Category-based quizzes

---

## 👨‍💻 Author

**Sourajit Samanta**
Android Developer | Kotlin | Jetpack Compose
B.Tech CSE

---

⭐ If you like this project, consider giving it a star!
