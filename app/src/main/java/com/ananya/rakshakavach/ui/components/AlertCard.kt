package com.ananya.rakshakavach.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ananya.rakshakavach.ui.theme.AlertRed
import com.ananya.rakshakavach.ui.theme.CardDark
import com.ananya.rakshakavach.ui.theme.SafetyYellow

@Composable
fun AlertCard() {

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
                text = "ALERTS & INCIDENTS",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            AlertItem("Crane Movement Near Zone B")
            AlertItem("High Wind Warning Zone A")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SafetyYellow
                )
            ) {

                Text(
                    text = "LOG NEAR MISS",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AlertItem(message: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = AlertRed
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = message,
            color = Color.White
        )
    }
}