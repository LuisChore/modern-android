package com.vazch.calculator

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


/**
 *  Weight Modifier: 1f one element and wrapContent the rest to make the first element take all
 *  the space and the rest wrap content
 *
 *  Modifier Clip: To round the corners of a composable.
 *
 *  Modifier Order: The order of modifiers matters. For example, if you apply a background color
 *  before clipping, the background will not be clipped. Always apply clipping before background
 *  or other visual modifiers.
 *  Modifiers are executed/chained top to bottom, but each modifier wraps everything below it.
 *
 *  Logging Levels:
 *  Log.v() - Verbose: Used for verbose logging, usually for development purposes.
 *  Log.d() - Debug: Used for debugging information.
 *  Log.i() - Info: Used for informational messages.
 *  Log.w() - Warning: Used for warning messages that indicate potential issues.
 *  Log.e() - Error: Used for error messages that indicate a failure in the application.
 *  Log.wtf() - What a Terrible Failure: Used for critical errors that should never happen.
 *
 *  In Logcat, when filtering by log level, you can see messages of that level and higher.
 *  For example, if you filter by "Info", you'll see Info, Warning, Error, and WTF messages,
 *  but not Debug or Verbose messages.
 *
 *
 *  Compose uses a Declarative approach: Each UI element is not a object that you manipulate,
 *  but a function that describes what the UI should look like for a given state, for an update
 *  in the UI, you call the function again with the new state, and Compose takes care of updating
 *  the UI efficiently.
 *
 *  mutableStateOf: A state holder that allows you to create a mutable state in Compose.
 *  When the value of a mutable state changes, Compose automatically re-composes any composables
 *  that read that state, updating the UI to reflect the new value.
 */


/**
 * View:
 *
 * Part of the program that is responsible for displaying data and reacting to user action
 * In our case is Composable: We display all the necessary UI elements, attach click listeners, etc.
 *
 * But all the logic related to calculations should live outside the responsability of the
 * composable function
 *
 *
 * ViewModel:
 *
 * Class, where we move part of the responsability away from the view
 */

/** State Management
 * The View subscribes to a state property from the ViewModel. It is recommended to store state in
 * a single variable, often encapsulated in a custom class that contains all necessary data for
 * rendering
 */


/** lineHeight: The lineHeight property in the Text composable is used to specify the height of
 * each line of text. It must match the fontSize to avoid clipping of the text.
 *
 */


@Preview
@Composable
fun Calculator(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel = viewModel()
){
    val state = viewModel.state.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DisplayScreen(
            modifier = Modifier
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    )
                )
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth(),
            state = state.value
        )

        KeyboardSection(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            viewModel = viewModel,
        )
    }
}


@Composable
private fun DisplayScreen(
    modifier: Modifier,
    state: CalculatorState
) {
    Box(
        modifier = modifier
            .padding(40.dp),
        contentAlignment = Alignment.BottomEnd
    ){
        Column(
            horizontalAlignment = Alignment.End
        ) {
            /**
             * We separate state.value as a variable to avoid possible side effects of recomposition.
             * If we use state.value directly in the Text composable, it may cause recomposition
             * issues if the state changes while the Text is being composed. By storing it in a
             * variable, we ensure that the value used in the Text composable remains consistent
             * during recomposition:
             *
             * "When the screen composable accesses the value of the state, it could potentially
             * be in a different state by the time the next line executes"
             */
            when(state){
                is CalculatorState.Error -> {
                    Text(
                        text = state.expression,
                        fontSize = 36.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.error,
                    )
                    Text(
                        text = "",
                        fontSize = 17.sp,
                        lineHeight = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                CalculatorState.Initial -> {}
                is CalculatorState.Input -> {
                    Text(
                        text = state.expression,
                        fontSize = 36.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Text(
                        text = state.result,
                        fontSize = 17.sp,
                        lineHeight = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                is CalculatorState.Success -> {
                    Text(
                        text = state.result,
                        fontSize = 36.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Text(
                        text = "",
                        fontSize = 17.sp,
                        lineHeight = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

        }
    }
}

@Composable
private fun KeyboardSection(
    modifier: Modifier,
    viewModel: CalculatorViewModel
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Extra Operators

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                modifier = Modifier
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.SQRT))
                    }
                    .weight(1f),
                text = "√",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                modifier = Modifier
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.PI))
                    }
                    .weight(1f),
                text = "π",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                modifier = Modifier
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.POWER))
                    }
                    .weight(1f),
                text = "^",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                modifier = Modifier
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.FACTORIAL))
                    }
                    .weight(1f),
                text = "!",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        Log.v("Calculator", "Verbose: AC button clicked")
                        Log.d("Calculator", "Debug: AC button clicked")
                        Log.i("Calculator", "Info: AC button clicked")
                        Log.w("Calculator", "Warning: AC button clicked")
                        Log.e("Calculator", "Error: AC button clicked")
                        Log.wtf("Calculator", "What a Terrible Failure: AC button clicked")
                        viewModel.processCommand(CalculatorCommand.Clear)
                    }
                    .background(MaterialTheme.colorScheme.secondary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "AC",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.PARENTHESES))
                    }
                    .background(MaterialTheme.colorScheme.tertiary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "( )",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.PERCENT))
                    }
                    .background(MaterialTheme.colorScheme.tertiary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "%",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIVIDE))
                    }
                    .background(MaterialTheme.colorScheme.tertiary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "÷",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIGIT_7))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "7",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIGIT_8))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "8",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIGIT_9))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "9",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.MULTIPLY))
                    }
                    .background(MaterialTheme.colorScheme.tertiary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "X",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIGIT_4))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "4",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIGIT_5))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "5",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIGIT_6))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "6",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.SUBTRACT))
                    }
                    .background(MaterialTheme.colorScheme.tertiary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "-",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIGIT_1))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "1",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIGIT_2))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "2",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIGIT_3))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "3",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.ADD))
                    }
                    .background(MaterialTheme.colorScheme.tertiary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "+",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DIGIT_0))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(2 / 1f)
                    .weight(2f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "0",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Input(Symbol.DOT))
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = ",",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        viewModel.processCommand(CalculatorCommand.Evaluate)
                    }
                    .background(MaterialTheme.colorScheme.tertiary)
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "=",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
