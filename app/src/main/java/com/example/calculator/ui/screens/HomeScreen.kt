package com.example.calculator.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.data.buttons
import com.example.calculator.model.ButtonType
import com.example.calculator.model.CalcButton
import com.example.calculator.ui.theme.CalculatorTheme

@Composable
fun DisplayScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "7+5",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "12",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Gray
        )
    }
}

@Composable
fun KeypadScreen(
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
                        onClick = {},
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
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        DisplayScreen(
            modifier = Modifier.weight(1f)
        )
        KeypadScreen(
            buttons = buttons
        )
    }
}

@Preview
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorScreen(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun DisplayPreview() {
    CalculatorTheme {
        DisplayScreen(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun KeypadScreenPreview() {
    CalculatorTheme {
        KeypadScreen(
            buttons = buttons,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
