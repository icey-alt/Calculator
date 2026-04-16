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
                val hasOperator = _expression.value.any { it in setOf('+', '−', '×', '÷', '%') }
                if (!hasOperator) {
                    _result.value = ""
                    return
                }
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
            // 1. 先处理百分比逻辑，把 % 转换为纯四则运算字符串
            val processedExpr = processPercentage(expr)

            // 2. 再替换符号给 exp4j 计算
            val cleanExpr = processedExpr
                .replace("×", "*")
                .replace("÷", "/")
                .replace("−", "-")

            ExpressionBuilder(cleanExpr)
                .build()
                .evaluate()
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * 核心方法：将表达式中的 % 转换为 exp4j 能理解的乘除法
     */
    private fun processPercentage(expr: String): String {
        // 匹配 "数字 运算符 数字%" 的情况 (例如 100+10%, 50×20%)
        val binaryPercentRegex = """([\d\.]+)([+\-×÷−])([\d\.]+)%""".toRegex()
        var result = expr

        // 循环处理，直到没有 "Y op X%" 模式 (防止出现 100+10%+5% 连续百分比的情况)
        while (binaryPercentRegex.containsMatchIn(result)) {
            result = binaryPercentRegex.replace(result) { matchResult ->
                val (yStr, opStr, xStr) = matchResult.destructured

                // 把自定义符号转为标准数学符号
                val op = when(opStr) {
                    "×" -> "*"
                    "÷" -> "/"
                    "−" -> "-"
                    else -> opStr
                }

                when (op) {
                    // 加减法：100 + 10% => 100 + (100 * (10 / 100.0))
                    "+", "-" -> "($yStr $op ($yStr * ($xStr / 100.0)))"
                    // 乘除法：100 × 10% => 100 * (10 / 100.0)
                    "*", "/" -> "($yStr $op ($xStr / 100.0))"
                    else -> "($xStr / 100.0)"
                }
            }
        }

        // 处理只剩下 "数字%" 的情况 (例如 50%)
        val singlePercentRegex = """([\d\.]+)%""".toRegex()
        result = singlePercentRegex.replace(result) { matchResult ->
            "(${matchResult.groupValues[1]} / 100.0)"
        }

        return result
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