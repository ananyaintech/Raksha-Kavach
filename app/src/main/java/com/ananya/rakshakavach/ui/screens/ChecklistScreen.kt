package com.ananya.rakshakavach.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ananya.rakshakavach.ui.components.GearChecklistCard
import com.ananya.rakshakavach.ui.theme.DarkBackground
import com.ananya.rakshakavach.ui.theme.SafetyYellow
import com.ananya.rakshakavach.viewmodel.DashboardViewModel

@Composable
fun ChecklistScreen(vm: DashboardViewModel) {
    val completion = vm.completionPercentage()
    val animatedProgress by animateFloatAsState(targetValue = completion / 100f, label = "Progress")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Text(
            text = "SAFETY CHECKLIST",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = SafetyYellow
        )
        Text(
            text = "Ensure all PPE is equipped before shift",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Progress Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Completion Status", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("$completion%", color = SafetyYellow, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = SafetyYellow,
                    trackColor = Color.DarkGray,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reuse the GearChecklistCard but it needs to be scrollable here if there are many items
        // Since GearChecklistCard was changed to Column, we can wrap it or just use its content
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn {
                item {
                    GearChecklistCard(
                        gearItems = vm.gearItems,
                        onToggle = { vm.toggleGear(it) }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = SafetyYellow),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("FINISH INSPECTION", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
