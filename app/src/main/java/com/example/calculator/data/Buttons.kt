package com.example.calculator.data

import com.example.calculator.model.ButtonType
import com.example.calculator.model.CalcButton

val buttons = listOf(
    CalcButton("AC", ButtonType.SECONDARY),
    CalcButton("%", ButtonType.SECONDARY),
    CalcButton("\u232B", ButtonType.SECONDARY),
    CalcButton("÷", ButtonType.SECONDARY),

    CalcButton("7", ButtonType.TERTIARY),
    CalcButton("8", ButtonType.TERTIARY),
    CalcButton("9", ButtonType.TERTIARY),
    CalcButton("×", ButtonType.SECONDARY),

    CalcButton("4", ButtonType.TERTIARY),
    CalcButton("5", ButtonType.TERTIARY),
    CalcButton("6", ButtonType.TERTIARY),
    CalcButton("−", ButtonType.SECONDARY),

    CalcButton("1", ButtonType.TERTIARY),
    CalcButton("2", ButtonType.TERTIARY),
    CalcButton("3", ButtonType.TERTIARY),
    CalcButton("+", ButtonType.SECONDARY),

    CalcButton("00", ButtonType.TERTIARY),
    CalcButton("0", ButtonType.TERTIARY),
    CalcButton(".", ButtonType.TERTIARY),
    CalcButton("=", ButtonType.PRIMARY)
)