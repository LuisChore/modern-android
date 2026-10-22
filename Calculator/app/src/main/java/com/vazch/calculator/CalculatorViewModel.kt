package com.vazch.calculator

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.mariuszgromada.math.mxparser.Expression


/** Command Pattern
 *
 * The Command Pattern is a behavioral design pattern that turns a request into a stand-alone object,
 * we use it to enhance Android Development by implementing programming logic without engaging
 * in UI layout.
 *
 * Sealed Interfaces are used to define a closed set of commands, each representing a specific user
 * action or event, restring commands sent from the View to the ViewModel, ensuring that only valid
 * commands are processed.
 *
 * CalculatorCommand sealed interface
 *  - Clear
 *  - Evaluate
 *  - Input
 *
 *  Making ViewModel reusable across different views, independent of the UI elements
 *
 *
 *
 * Symbol enum class:
 * Introduce to define a strict set of acceptable user input symbols, reducing errors related to
 * invalid inputs.
 *
 *
 */


/** Stateflow and ViewModel
 *
 * Reusable ViewModels:
 * Create ViewModels that are independent of Jetpack Compose, it overcomes the limitation of
 * MutableState, which is tied to Compose.
 *
 * Introduction of StateFlow:
 * MutableStateFlow is a better alternative, allowing state management regardless of the UI framework,
 * Utilize an instance of the UI data class for default values.
 *
 * UI Update Handling:
 * Use of cllectAsState() in the Composable function to observe changes in the StateFlow, ensuring that
 * the UI is updated whenever the state changes.
 *
 * Encapsulation and Backing Property Pattern:
 * Avoid exposing mutable StateFlow outside the ViewModel, which can lead to encapsulation issues.
 * The Backing Property Pattern is used to expose an immutable StateFlow to the UI, while keeping
 * the mutable StateFlow private within the ViewModel.
 *
 * ViewModel Instantiation:
 * Avoid Creating ViewModels at the file level due to potential memory leaks, because the lifecycle
 * of the ViewModel would be tied to the file (singleton), rather than the UI component that uses it.
 * Keeping it in memory even if the UI component is destroyed.
 *
 * - ViewModels Must Survive Recomposition and Configuration Changes
 * - Inheriting from ViewModel class and using viewModel() function to instantiate it, ensures that the
 *   ViewModel is scoped to the lifecycle of the UI component and survives configuration changes.
 *
 * Best Practices for Dependency Management:
 * It is advisable to pass ViewModels as parameters to Composable functions, rather than creating
 * them within the function, enhancing flexibility and testability.
 *
 * "A component (class, module, function) should not create its dependencies, but should receive
 * them from the outside."
 *
 *
 */

/** State Management and UI Updates
 *
 * We use a Sealed Interface to define our screen state, which allows us to represent different
 * states of the UI in a type-safe manner.
 *
 */

sealed interface CalculatorState
{
    data object Initial : CalculatorState

    data class Input(
        val expression: String,
        val result: String,
    ) : CalculatorState

    data class Success(val result: String) : CalculatorState

    data class Error(val expression: String) : CalculatorState
}
enum class Symbol(val value: String) {
    DIGIT_0("0"),
    DIGIT_1("1"),
    DIGIT_2("2"),
    DIGIT_3("3"),
    DIGIT_4("4"),
    DIGIT_5("5"),
    DIGIT_6("6"),
    DIGIT_7("7"),
    DIGIT_8("8"),
    DIGIT_9("9"),
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("x"),
    DIVIDE("÷"),
    PERCENT("%"),
    POWER("^"),
    FACTORIAL("!"),
    SQRT("√"),
    PI("π"),
    DOT("."),
    PARENTHESES("()"),
}
sealed interface CalculatorCommand {
    data object Clear : CalculatorCommand
    data object Evaluate : CalculatorCommand
    // data keyword in order to make the class a singleton and override toString, equals,
    // and hashCode methods automatically
    data class Input(val symbol: Symbol) : CalculatorCommand
}

class CalculatorViewModel: ViewModel() {

    // Backing Property Pattern: Exposing an immutable StateFlow to the UI,
    // while keeping the mutable StateFlow private within the ViewModel.
    val _state: MutableStateFlow<CalculatorState> = MutableStateFlow(CalculatorState.Initial)

    val state: StateFlow<CalculatorState> = _state

    private var expression = ""

    //  A function that requires to see its implementation in order to understand what it does.
    //  It is not a clean architecture approach.
    fun processUserInput(input: String){
        if(input == "AC"){
            _state.value = CalculatorState.Initial
        }
    }

    fun processCommand(command: CalculatorCommand) {
        Log.d("CalculatorViewModel", "Processing command: $command")
        when(command){
            CalculatorCommand.Clear -> {
                expression = ""
                _state.value = CalculatorState.Initial
            }
            CalculatorCommand.Evaluate -> {
                val result = evaluateExpression(expression)
                _state.value = if (result != null) {
                    CalculatorState.Success(result)
                } else {
                    CalculatorState.Error(expression)
                }
                Log.d("CalculatorViewModel", "Evaluate command received")
            }
            is CalculatorCommand.Input -> {
                val nextSymbol = if (command.symbol == Symbol.PARENTHESES) {
                    getCorrectParentheses(expression)
                } else {
                    command.symbol.value
                }
                expression += nextSymbol
               _state.value = CalculatorState.Input(
                   expression,
                   evaluateExpression(expression) ?: ""
               )
            }
        }
    }
    private fun getCorrectParentheses(expression: String): String {
        val openCount = expression.count { it == '(' }
        val closeCount = expression.count { it == ')' }

        return when {
            expression.isEmpty() -> "("
            expression.last().let { !it.isDigit() && it != ')' && it != Symbol.PI.value.first() } -> "("
            openCount > closeCount -> ")"
            else -> "("
        }
    }

    private fun evaluateExpression(expression: String): String? {
        return Expression(expression.replace("÷", "/").replace("x", "*"))
            .calculate().takeIf { it.isFinite() }?.toString()
    }
}
