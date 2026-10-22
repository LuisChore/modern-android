package com.vazch.calculator.ui.theme

import androidx.compose.ui.graphics.Color


// COMMON
val Black = Color(0xFF0D0D0D)            // Dark: screen background · Light: base for text/icons
val White = Color(0xFFFFFFFF)            // Light: screen background · Dark: text/icons
val CalcHandleGrey = Color(0xFFD9D9D9)       // Drag handle under the display panel (both themes)
val CalcOperatorBlue = Color(0xFF084276)     // Base blue of operator buttons ( ) % ÷ × − + =
val CalcClearGreen = Color(0xFF006600)       // Base green of the AC (clear) button
val CalcBatteryEmpty = Color(0x4D0D0D0D)     // Status bar: empty part of battery icon (30%)

// DARK THEME
val DarkBackground = Color(0xFF0D0D0D)           // Screen background (keypad area)
val DarkDisplayBackground = Color(0xFF333333)    // Top display panel + status bar background
val DarkStatusBarContent = Color(0xCCFFFFFF)     // Status bar time/icons (white 80%)
val DarkExpressionText = Color(0xFFFFFFFF)       // Main expression text, e.g. "45x8"
val DarkResultText = Color(0xCCFFFFFF)           // Result preview text, e.g. "360" (white 80%)
val DarkMenuDotActive = Color(0xFFD9D9D9)        // Overflow menu: solid dot
val DarkMenuDotInactive = Color(0x33D9D9D9)      // Overflow menu: faded dots (20%)
val DarkDragHandle = Color(0xFFD9D9D9)           // Drag handle pill under display
val DarkScientificSymbol = Color(0xFFFFFFFF)     // Scientific row: √ π ^ !
val DarkExpandButtonBg = Color(0xCC2C2E33)       // Expand/collapse chevron circle background (80%)
val DarkExpandButtonIcon = Color(0xFFFFFFFF)     // Expand/collapse chevron stroke
val DarkNumberButton = Color(0x802C2E33)         // Number buttons 0–9 and "." (50%)
val DarkOperatorButton = Color(0xCC084276)       // Operator buttons ( ) % ÷ × − + = (80%)
val DarkClearButton = Color(0xCC006600)          // AC button (80%)
val DarkButtonText = Color(0xFFFFFFFF)           // Labels on all keypad buttons

// LIGHT THEME
val LightBackground = Color(0xFFFFFFFF)          // Screen background (keypad area)
val LightDisplayBackground = Color(0xFFA6A6A6)   // Top display panel + status bar background
val LightStatusBarContent = Color(0xFF0D0D0D)    // Status bar time/icons
val LightExpressionText = Color(0xFF0D0D0D)      // Main expression text, e.g. "45x8"
val LightResultText = Color(0xFF0D0D0D)          // Result preview text, e.g. "360"
val LightMenuDot = Color(0xFF0D0D0D)             // Overflow menu dots
val LightDragHandle = Color(0xE60D0D0D)          // Drag handle pill under display (90%)
val LightScientificSymbol = Color(0xE60D0D0D)    // Scientific row: √ π ^ ! (90%)
val LightExpandButtonBg = Color(0xCCE6E7EB)      // Expand/collapse chevron circle background (80%)
val LightExpandButtonIcon = Color(0xE60D0D0D)    // Expand/collapse chevron stroke (90%)
val LightNumberButton = Color(0xFFE6E7EB)        // Number buttons 0–9 and "."
val LightOperatorButton = Color(0x80084276)      // Operator buttons ( ) % ÷ × − + = (50%)
val LightClearButton = Color(0x80006600)         // AC button (50%)
val LightButtonText = Color(0xE60D0D0D)          // Labels on all keypad buttons (90%)