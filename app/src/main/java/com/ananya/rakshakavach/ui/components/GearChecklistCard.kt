package com.ananya.rakshakavach.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.ananya.rakshakavach.data.model.GearItem
import com.ananya.rakshakavach.ui.theme.AlertRed
import com.ananya.rakshakavach.ui.theme.CardDark
import com.ananya.rakshakavach.ui.theme.SuccessGreen

@Composable
fun GearChecklistCard(
    gearItems: List<GearItem>,
    onToggle: (Int) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        ),
        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "GEAR CHECKLIST",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Removed LazyColumn to avoid nesting issues with verticalScroll in DashboardScreen
            gearItems.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text = item.name,
                            color = Color.White
                        )

                        Text(
                            text = if (item.isAvailable) "Available" else "MISSING",
                            color = if (item.isAvailable) SuccessGreen else AlertRed,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            imageVector = if (item.isAvailable)
                                Icons.Default.CheckCircle
                            else
                                Icons.Default.Warning,
                            contentDescription = null,
                            tint = if (item.isAvailable)
                                SuccessGreen
                            else
                                AlertRed
                        )

                        Switch(
                            checked = item.isAvailable,
                            onCheckedChange = {
                                onToggle(index)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GearChecklistCardPreview() {
    GearChecklistCard(
        gearItems = listOf(
            GearItem("Helmet", true),
            GearItem("Gloves", false),
            GearItem("Safety Vest", true)
        ),
        onToggle = {}
    )
}
