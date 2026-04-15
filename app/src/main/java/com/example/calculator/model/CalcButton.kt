package com.example.calculator.model

import androidx.compose.ui.graphics.Color


data class CalcButton(
    val text: String,
    val type: ButtonType,
    val span: Int = 1
)

enum class ButtonType(val color: Color) {
    PRIMARY(Color(0xFFFF9500)),
    SECONDARY(Color(0xFFD4D4D2)),
    TERTIARY(Color(0xFFE5E5E5))
}
