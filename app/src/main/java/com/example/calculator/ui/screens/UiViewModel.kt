package com.example.calculator.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.objecthunter.exp4j.ExpressionBuilder

class UiViewModel : ViewModel() {
    private val _expression = MutableStateFlow("")
    val expression = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    private val _showResult = MutableStateFlow(false)
    val showResult = _showResult.asStateFlow()

    fun onButtonClick(text: String) {
        when (text) {
            "AC" -> clearAll()
            "\u232B" -> backspace()
            "=" -> calculate()
            else -> appendToExpression(text)
        }
    }

    private fun clearAll() {
        _expression.value = ""
        _result.value = ""
        _showResult.value = false
    }

    private fun backspace() {
        if (_showResult.value) {
            clearAll()
            return
        }
        if (_expression.value.isNotEmpty()) {
            _expression.value = _expression.value.dropLast(1)
            updateResult()
        }
    }

    private fun calculate() {
        if (_expression.value.isEmpty()) return
        try {
            val result = evaluateExpression(_expression.value)
            _result.value = formatResult(result)
            _showResult.value = true
        } catch (e: Exception) {
            _result.value = "Error"
        }
    }

    private fun appendToExpression(text: String) {
        if (_showResult.value) {
            val isOperator = text in setOf("+", "−", "×", "÷", "%")
            if (isOperator) {
                _expression.value = _result.value + text
            } else {
                _expression.value = text
            }
            _result.value = ""
            _showResult.value = false
            return
        }
        _expression.value += text
        updateResult()
    }

    private fun updateResult() {
        val expr = _expression.value
        if (expr.isEmpty()) {
            _result.value = ""
            return
        }
        if (expr.none { it in setOf('+', '−', '×', '÷', '%') }) {
            _result.value = ""
            return
        }
        try {
            val result = evaluateExpression(expr)
            _result.value = formatResult(result)
        } catch (e: Exception) {
            _result.value = ""
        }
    }

    private fun evaluateExpression(expr: String): Double {
        val normalized = expr
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
        val withPercent = processPercentage(normalized)
        return ExpressionBuilder(withPercent)
            .build()
            .evaluate()
    }

    private fun processPercentage(expr: String): String {
        var result = expr

        val binaryPattern = """([\d\.]+)([+\-*/])([\d\.]+)%""".toRegex()
        while (binaryPattern.containsMatchIn(result)) {
            result = binaryPattern.replace(result) { match ->
                val (y, op, x) = match.destructured
                when (op) {
                    "+", "-" -> "($y $op ($y * ($x / 100.0)))"
                    "*", "/" -> "($y $op ($x / 100.0))"
                    else -> "($x / 100.0)"
                }
            }
        }

        result = """([\d\.]+)%""".toRegex().replace(result) { match ->
            val num = match.groupValues[1]
            "($num / 100.0)"
        }

        return result
    }

    private fun formatResult(value: Double): String {
        if (value == value.toLong().toDouble()) {
            return value.toLong().toString()
        }
        return String.format("%.8f", value)
            .trimEnd('0')
            .trimEnd('.')
    }
}
