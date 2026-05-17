package com.ananya.rakshakavach.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ananya.rakshakavach.data.local.Incident
import com.ananya.rakshakavach.ui.theme.AlertRed
import com.ananya.rakshakavach.ui.theme.DarkBackground
import com.ananya.rakshakavach.ui.theme.SafetyYellow
import com.ananya.rakshakavach.viewmodel.IncidentViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun IncidentScreen(vm: IncidentViewModel) {
    val incidents by vm.allIncidents.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = SafetyYellow,
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Add, contentDescription = "Report Incident")
            }
        },
        containerColor = DarkBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "INCIDENT LOG",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = SafetyYellow
            )
            Text(
                text = "AI-powered safety report analysis",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(incidents) { incident ->
                    IncidentItem(
                        incident = incident,
                        isAnalyzing = vm.isAiAnalyzing,
                        analysisResult = if (vm.aiAnalysisResult.startsWith(incident.id.toString() + ":")) 
                            vm.aiAnalysisResult.removePrefix(incident.id.toString() + ":") else "",
                        onAnalyze = { vm.analyzeIncidentWithAI(incident.id, incident.description) }
                    )
                }
            }
        }
    }

    if (showDialog) {
        AddIncidentDialog(
            onDismiss = { showDialog = false },
            onConfirm = { title, desc, severity ->
                vm.addIncident(title, desc, severity)
                showDialog = false
            }
        )
    }
}

@Composable
fun IncidentItem(
    incident: Incident,
    isAnalyzing: Boolean,
    analysisResult: String,
    onAnalyze: () -> Unit
) {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    val dateString = sdf.format(Date(incident.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    tint = when (incident.severity) {
                        "High" -> AlertRed
                        "Medium" -> SafetyYellow
                        else -> Color.Gray
                    },
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = incident.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = dateString,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                
                AssistChip(
                    onClick = onAnalyze,
                    label = { Text("AI ANALYZE", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                    leadingIcon = { 
                        if (isAnalyzing && analysisResult.isEmpty()) {
                            CircularProgressIndicator(modifier = Modifier.size(12.dp), strokeWidth = 2.dp, color = SafetyYellow)
                        } else {
                            Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(14.dp))
                        }
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = SafetyYellow,
                        leadingIconContentColor = SafetyYellow
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = SafetyYellow.copy(alpha = 0.5f)
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = incident.description,
                color = Color.LightGray,
                fontSize = 15.sp,
                lineHeight = 20.sp
            )

            AnimatedVisibility(
                visible = analysisResult.isNotEmpty(),
                enter = fadeIn() + expandVertically()
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "GEMINI AI ANALYSIS",
                        color = SafetyYellow,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = analysisResult,
                        color = Color(0xFFB0BEC5),
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    color = when (incident.severity) {
                        "High" -> AlertRed.copy(alpha = 0.1f)
                        "Medium" -> SafetyYellow.copy(alpha = 0.1f)
                        else -> Color.Gray.copy(alpha = 0.1f)
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = incident.severity.uppercase(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = when (incident.severity) {
                            "High" -> AlertRed
                            "Medium" -> SafetyYellow
                            else -> Color.Gray
                        },
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIncidentDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var severity by remember { mutableStateOf("Medium") }
    val severities = listOf("Low", "Medium", "High")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Report Incident", fontWeight = FontWeight.Bold) },
        containerColor = Color(0xFF1E1E1E),
        titleContentColor = Color.White,
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SafetyYellow,
                        focusedLabelColor = SafetyYellow
                    )
                )
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description") },
                    modifier = Modifier.height(100.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SafetyYellow,
                        focusedLabelColor = SafetyYellow
                    )
                )
                Text("Severity Level", color = Color.Gray, fontSize = 14.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    severities.forEach { s ->
                        FilterChip(
                            selected = severity == s,
                            onClick = { severity = s },
                            label = { Text(s) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SafetyYellow,
                                selectedLabelColor = Color.Black
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(title, desc, severity) },
                colors = ButtonDefaults.buttonColors(containerColor = SafetyYellow)
            ) {
                Text("REPORT", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCEL", color = Color.Gray)
            }
        }
    )
}
