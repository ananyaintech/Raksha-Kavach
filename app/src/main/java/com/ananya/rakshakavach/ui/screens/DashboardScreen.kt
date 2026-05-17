package com.ananya.rakshakavach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ananya.rakshakavach.ui.components.*
import com.ananya.rakshakavach.ui.theme.CardDark
import com.ananya.rakshakavach.ui.theme.DarkBackground
import com.ananya.rakshakavach.ui.theme.SafetyYellow
import com.ananya.rakshakavach.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    vm: DashboardViewModel
) {
    val riskScore by vm.riskScore.collectAsState()

    Scaffold(
        topBar = {
            TopBar()
        },
        containerColor = DarkBackground
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            
            RiskGaugeCard(
                riskScore = riskScore,
                riskLabel = vm.riskLabel(),
                warningMessage = vm.riskWarningMessage()
            )

            AISafetyRecommendationCard(
                recommendation = vm.aiRecommendation,
                isLoading = vm.isAiLoading,
                onGenerate = { vm.generateAiRecommendation() }
            )

            TaskCard()

            // Summary Info
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardSummaryCard("PPE STATUS", "${vm.completionPercentage()}%", Modifier.weight(1f))
                DashboardSummaryCard("SHIFT TIME", "04:22 hrs", Modifier.weight(1f))
            }

            GearChecklistCard(
                gearItems = vm.gearItems,
                onToggle = {
                    vm.toggleGear(it)
                }
            )

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SafetyYellow
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "START WORK-SHIFT",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun DashboardSummaryCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardDark),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = label, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = SafetyYellow)
        }
    }
}
