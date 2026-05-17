package com.ananya.rakshakavach.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Checklist : Screen("checklist", "Checklist", Icons.Default.Assignment)
    object Incidents : Screen("incidents", "Incidents", Icons.Default.Warning)
    object Quiz : Screen("quiz", "Quiz", Icons.Default.Quiz)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)

    companion object {
        val items = listOf(Home, Checklist, Incidents, Quiz, Profile)
    }
}
