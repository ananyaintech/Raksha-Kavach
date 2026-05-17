package com.ananya.rakshakavach.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ananya.rakshakavach.ui.theme.CardDark
import com.ananya.rakshakavach.ui.theme.SafetyYellow

@Composable
fun QuizCard() {

    var selectedAnswer by remember {
        mutableStateOf("")
    }

    val options = listOf(
        "Check gas connection",
        "Wear harness",
        "Inspect leads"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "GEMINI SAFETY QUIZ",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Before starting welding, what is the most critical safety step?",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            options.forEach { option ->

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    RadioButton(
                        selected = selectedAnswer == option,
                        onClick = {
                            selectedAnswer = option
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = SafetyYellow
                        )
                    )

                    Text(
                        text = option,
                        color = Color.White,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }
    }
}