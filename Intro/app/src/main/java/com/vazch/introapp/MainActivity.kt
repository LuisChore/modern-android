package com.vazch.introapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vazch.introapp.ui.theme.Gray
import com.vazch.introapp.ui.theme.Green
import com.vazch.introapp.ui.theme.IntroAppTheme
import com.vazch.introapp.ui.theme.Yellow
import kotlin.random.Random
import kotlin.text.onEach
import kotlin.uuid.Uuid.Companion.random

/**
 * # 2 Introduction to Android Studio
 *
 * ## Project Structure
 *
 * * manifests: Stores the main information about the application and its settings.
 * * res: Stores app resources (images, music, strings, etc.).
 * * Gradle Scripts: Stores the Gradle configuration files.
 * * kotlin + java: Contains all the project source code.
 *
 * # 3 Analyzing a Program
 *
 * ## MainActivity Class
 *
 * It is responsible for one screen in the app. If it is defined as LAUNCHER in the manifest,
 * that screen is launched when the app starts.
 * The onCreate method is where the actions related to opening the screen are defined.
 *
 * ## setContent
 *
 * It takes a lambda expression as its last parameter. It allows adding UI elements through
 * composable functions.
 *
 * ## Jetpack Compose
 *
 * A way to create UI elements using functions rather than traditional object-oriented methods.
 * Those functions represent UI components.
 *
 * ## TextUnit internal constructor
 *
 * We cannot use px as a unit because it does not look the same on low and high resolution devices.
 * * Screen Density: Number of pixels per inch (dots per inch), unique to each device.
 * * DP: Density-independent pixels, a standard unit that keeps the same size on devices with
 *   different resolutions.
 *
 * ## Fonts and UI Elements Use Different Units
 *
 * * SP: Scalable pixels. If the user increases the font scale in the system settings,
 *   the text scales accordingly.
 *
 * # 4 Modifier
 *
 * It allows us to configure the appearance of an object by applying different settings to it.
 * * Spacing: Parameter that defines the distance from the edge of an element to its content.
 * * Padding: Controls the space inside the element.
 * * Margin: Controls the space outside the element.
 *
 * Modifier interface: Used to add common properties to every UI component.
 * Working with modifiers is similar to working with the Builder pattern, but here the order of
 * the methods matters. Earlier modifiers are the outer layers, while later modifiers are the
 * inner layers.
 *
 * # 5 Row, Column and Alignment
 *
 * * Containers: Composable functions that allow you to place UI elements inside them.
 * * Row and Column: Position elements horizontally and vertically.
 * * Alignment and Arrangement: Help align elements vertically and horizontally.
 *
 * # 6 Combining Containers
 *
 * * weight: Makes all the elements of a Row or Column fill the available space and
 *   distributes it evenly.
 * * spacedBy: Adds space between elements.
 * * aspectRatio: Makes sure that an element keeps the same width and height.
 * * Box: Container that allows flexible positioning of elements inside it. It allows us to
 *   position elements relative to each other and to the container itself.
 *
 * # 8 Scaffold and Custom Components
 *
 * Any composable function can be called inside other composable functions.
 * Whenever possible, every composable function should accept a modifier parameter,
 * so that the caller can customize the appearance of the component.
 *
 * * Scaffold: A composable function that provides a basic structure for the app, including a
 *   top bar, a bottom bar, and a content area.
 *
 * * UI Architecture Design:
 *   1. Status Bar
 *   2. Action Bar
 *   3. Content Area
 *   4. Floating Action Button
 *
 * * PaddingValues: A class that represents the padding values for the content area of the
 *   Scaffold. The padding values are calculated based on the size of the top bar and the
 *   bottom bar, and they are passed to the content area as a parameter.
 *
 *   Example of use:
 *
 *       Scaffold { paddingValues ->
 *           // Content area
 *       }
 *
 *   Here, the paddingValues parameter is passed to the content area, and it can be used to
 *   add padding to it.
 *
 * * Insets: Values that represent the space occupied by the system UI elements, such as
 *   the status bar and the navigation bar.
 *
 * # 9 Material Theme
 *
 * * Define colors in a separate file, for better organization and reusability.
 * * Use themes instead of hardcoding colors in the code. It allows us to change the
 *   appearance of the app easily by changing the theme.
 * * Material Design: A design system developed by Google that provides guidelines for designing.
 * * Dynamic Color: A feature that allows the app to change its color scheme based on the
 *   user's device settings. It is available on Android 12 and above.
 * * Edge to Edge: A feature that allows the app to draw behind the system bars,
 *   such as the status bar and the navigation bar.
 */
class MainActivity : ComponentActivity() {

    // Exercise:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntroAppTheme {
                Scaffold{ paddingValues ->
                    Greeting(
                        modifier = Modifier.padding(paddingValues),
                        text ="Hello World! Hello World! Hello World!"
                    )
                }
            }

        }

    }

    @Preview
    @Composable
    private fun GreetingPreview() {
        IntroAppTheme{
            Greeting(
                text = "Hello World! Hello World! Hello World My App!"
            )

        }
    }

    /**
     * Composable functions:
     * They can only be called from other composable functions, and they can only be used
     * to create UI elements.
     */
    @Composable
    private fun Greeting(
        modifier: Modifier = Modifier,
        text: String,
    ) {
        val words = text.split(" ")
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.DarkGray)
                .padding(8.dp)

        ) {
            words.forEachIndexed { index, it ->
                val word = it.filter { it.isLetter() }.map { it.uppercaseChar() }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (char in word) {
                        val random = Random.nextInt(0, 2)
                        val randomColor = if (random == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        val letterBackground = if (index % 2 != 0) MaterialTheme.colorScheme.tertiary  else randomColor
                        Text(
                            text = char.toString(),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .background(letterBackground)
                                .weight(1f)
                                .aspectRatio(1f)
                                .wrapContentHeight(),

                            )
                    }
                }
            }
        }
    }

    private fun firstExercise() {
        setContent {
            val helloWorld = "Hello World!"
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.DarkGray)
                    .padding(16.dp)
                    .fillMaxSize(),

                ) {
                helloWorld.onEach {
                    if (it.isLetter()) {
                        Text(
                            text = it.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.Gray)


                        )
                    }
                }
            }
        }
    }


    private fun secondExercise() {
        val characters = "Hello World!".filter { it.isLetter() }
        val firstRow = characters.take(5)
        val secondRow = characters.takeLast(5)

        setContent {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.DarkGray)
                    .padding(8.dp)

            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    firstRow.onEach {
                        Text(
                            text = it.toString(),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray)
                                .weight(1f)
                                .aspectRatio(1f)
                                .wrapContentHeight(),

                            )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()

                ) {
                    secondRow.onEach {
                        Text(
                            text = it.toString(),

                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray)
                                .weight(1f)
                                .aspectRatio(1f),
                        )
                    }
                }
            }
        }
    }
}

