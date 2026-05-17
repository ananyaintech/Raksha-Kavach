package com.ananya.rakshakavach.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ananya.rakshakavach.ui.screens.*
import com.ananya.rakshakavach.viewmodel.DashboardViewModel
import com.ananya.rakshakavach.viewmodel.IncidentViewModel
import com.ananya.rakshakavach.viewmodel.QuizViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel,
    incidentViewModel: IncidentViewModel,
    quizViewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            DashboardScreen(dashboardViewModel)
        }
        composable(Screen.Checklist.route) {
            ChecklistScreen(dashboardViewModel)
        }
        composable(Screen.Incidents.route) {
            IncidentScreen(incidentViewModel)
        }
        composable(Screen.Quiz.route) {
            QuizScreen(quizViewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(dashboardViewModel)
        }
    }
}
