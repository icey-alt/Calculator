package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.data.buttons
import com.example.calculator.ui.screens.CalculatorScreen
import com.example.calculator.ui.screens.UiViewModel

import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                val uiViewModel: UiViewModel = viewModel()
                CalculatorScreen(
                    uiViewModel = uiViewModel,
                    modifier = Modifier,
                    buttons = buttons
                )
            }
        }
    }
}

