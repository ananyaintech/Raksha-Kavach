package com.ananya.rakshakavach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ananya.rakshakavach.ui.theme.DarkBackground
import com.ananya.rakshakavach.ui.theme.SafetyYellow
import com.ananya.rakshakavach.viewmodel.DashboardViewModel

@Composable
fun ProfileScreen(vm: DashboardViewModel) {
    var showEditDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "WORKER PROFILE",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SafetyYellow
            )
            IconButton(onClick = { showEditDialog = true }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = SafetyYellow)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Profile Image
        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .background(Color(0xFF1E1E1E))
                .padding(4.dp)
                .background(Color.DarkGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(90.dp), tint = Color.LightGray)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = vm.userName, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        Text(text = "Worker ID: ${vm.workerId}", fontSize = 15.sp, color = SafetyYellow, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(40.dp))

        // Info Cards
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                ProfileInfoItem(Icons.Default.Business, "Department", vm.userDept)
                HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(vertical = 16.dp))
                ProfileInfoItem(Icons.Default.Phone, "Phone", vm.userPhone)
                HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(vertical = 16.dp))
                ProfileInfoItem(Icons.Default.Verified, "Certification", "Level 4 Safety Expert")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Stats
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            StatCard("DRILLS", "12", Modifier.weight(1f))
            StatCard("COMPLIANCE", "98%", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252).copy(alpha = 0.9f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(10.dp))
            Text("LOGOUT SESSION", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        }
    }

    if (showEditDialog) {
        EditProfileDialog(
            currentName = vm.userName,
            currentPhone = vm.userPhone,
            currentDept = vm.userDept,
            onDismiss = { showEditDialog = false },
            onSave = { name, phone, dept ->
                vm.updateProfile(name, phone, dept)
                showEditDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    currentName: String,
    currentPhone: String,
    currentDept: String,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var phone by remember { mutableStateOf(currentPhone) }
    var dept by remember { mutableStateOf(currentDept) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Worker Profile", fontWeight = FontWeight.Bold) },
        containerColor = Color(0xFF1E1E1E),
        titleContentColor = Color.White,
        textContentColor = Color.LightGray,
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SafetyYellow,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = SafetyYellow
                    )
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SafetyYellow,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = SafetyYellow
                    )
                )
                OutlinedTextField(
                    value = dept,
                    onValueChange = { dept = it },
                    label = { Text("Department") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SafetyYellow,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = SafetyYellow
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, phone, dept) },
                colors = ButtonDefaults.buttonColors(containerColor = SafetyYellow)
            ) {
                Text("SAVE", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCEL", color = Color.Gray)
            }
        }
    )
}

@Composable
fun ProfileInfoItem(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = SafetyYellow, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(text = label, fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Text(text = value, fontSize = 17.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = SafetyYellow)
            Text(text = label, fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        }
    }
}
