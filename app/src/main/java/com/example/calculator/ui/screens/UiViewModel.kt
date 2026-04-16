package com.example.calculator.ui.screens

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder

class UiViewModel : ViewModel() {
    private val _expression = MutableStateFlow("")
    val expression = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    fun onButtonClick(text: String) {
        viewModelScope.launch {
            when (text) {
                "AC" -> clearAll()
                "\u232B" -> backspace()
                "=" -> calculate()
                else -> appendToExpression(text)
            }
        }
    }

    private fun clearAll() {
        _expression.value = ""
        _result.value = ""
    }

    private fun backspace() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = _expression.value.dropLast(1)
            updateResult()
        }
    }

    private fun calculate() {
        try {
            val result = evaluateExpression(_expression.value)
            _result.value = formatResult(result)
        } catch (e: Exception) {
            _result.value = "Error"
        }
    }

    private fun appendToExpression(text: String) {
        _expression.value += text
        updateResult()
    }

    private fun updateResult() {
        if (_expression.value.isNotEmpty()) {
            try {
                val result = evaluateExpression(_expression.value)
                _result.value = formatResult(result)
            } catch (e: Exception) {
                _result.value = ""
            }
        } else {
            _result.value = ""
        }
    }

    private fun evaluateExpression(expr: String): Double {
        return try {
            val cleanExpr = expr.replace("×", "*").replace("÷", "/")
            ExpressionBuilder(cleanExpr)
                .build()
                .evaluate()
        } catch (e: Exception) {
            throw e
        }
    }

    @SuppressLint("DefaultLocale")
    private fun formatResult(value: Double): String {
        if (value == value.toLong().toDouble()) {
            return value.toLong().toString()
        }
        return String.format("%.8f", value)
                .trimEnd('0')
                .trimEnd('.')
    }
}