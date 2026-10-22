# Intro App – Introduction to Android

Introductory Android project built with Kotlin and Jetpack Compose. Its purpose is to practice the
basics of building a screen: understanding the project structure, sizing units, laying out UI with
`Row` and `Column`, customizing components with `Modifier`, and applying colors and themes. The
written theory lives in the KDoc at the top of `MainActivity.kt`, and this README works as a
chapter index to find each topic quickly.

## Chapters

All paths are relative to `app/src/main/java/com/vazch/introapp/`.

| Chapter | Topic | Where to find it |
|---------|-------|------------------|
| 2 | Project structure (manifests, res, Gradle Scripts, kotlin + java) | `MainActivity.kt` KDoc, "Introduction to Android Studio" |
| 3 | `MainActivity`, `onCreate`, `setContent`, Jetpack Compose | `MainActivity.kt` KDoc + `onCreate` |
| 3 | dp and sp | `MainActivity.kt` KDoc, "TextUnit" and "Fonts" sections; `8.dp` / `16.sp` in `Greeting` |
| 4 | Modifier (padding, margin, order of modifiers) | `MainActivity.kt` KDoc, "Modifier"; `Modifier` chains in `Greeting` |
| 5 | Row, Column, Alignment, Arrangement | `MainActivity.kt` KDoc; `Greeting`, `firstExercise`, `secondExercise` |
| 6 | weight, spacedBy, aspectRatio, Box | `MainActivity.kt` KDoc, "Combining Containers"; `Greeting` |
| 8 | Scaffold, PaddingValues, insets, custom components | `MainActivity.kt` KDoc; `Scaffold` in `onCreate` |
| 9 | Material Theme, Dynamic Color | `MainActivity.kt` KDoc; `ui/theme/Theme.kt`, `ui/theme/Color.kt` |
| 9 | `enableEdgeToEdge` (Edge to Edge) | `MainActivity.kt` KDoc; `onCreate` |
| – | ARGB colors (`Color(0xAARRGGBB)`: alpha, red, green, blue) | `ui/theme/Color.kt` (not covered in the KDoc) |
