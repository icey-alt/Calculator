package com.example.calculator.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calculator.model.ButtonType
import com.example.calculator.model.CalcButton

@Composable
fun DisplayScreen(
    uiViewModel: UiViewModel,
    modifier: Modifier = Modifier
) {
    val expression by uiViewModel.expression.collectAsState()
    val result by uiViewModel.result.collectAsState()
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = expression,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = result,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Gray
        )
    }
}

@Composable
fun KeypadScreen(
    uiViewModel: UiViewModel,
    modifier: Modifier = Modifier,
    buttons: List<CalcButton>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        buttons.chunked(4).forEach { rowButtons ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowButtons.forEach { button ->
                    Button(
                        onClick = {
                            uiViewModel.onButtonClick(button.text)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = button.type.color
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = button.text,
                            color = if (button.type == ButtonType.PRIMARY) Color.White else Color.Black,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(
    uiViewModel: UiViewModel,
    modifier: Modifier,
    buttons: List<CalcButton>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        DisplayScreen(
            uiViewModel = uiViewModel,
            modifier = Modifier.weight(1f)
        )
        KeypadScreen(
            uiViewModel = uiViewModel,
            buttons = buttons
        )
    }
}