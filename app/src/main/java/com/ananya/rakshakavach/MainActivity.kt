package com.ananya.rakshakavach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.ananya.rakshakavach.data.local.AppDatabase
import com.ananya.rakshakavach.repository.SafetyAiRepository
import com.ananya.rakshakavach.ui.components.BottomNavBar
import com.ananya.rakshakavach.ui.navigation.NavGraph
import com.ananya.rakshakavach.ui.theme.RakshaKavachTheme
import com.ananya.rakshakavach.viewmodel.DashboardViewModel
import com.ananya.rakshakavach.viewmodel.IncidentViewModel
import com.ananya.rakshakavach.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {
    
    // Replace with your actual Gemini API Key
    private val GEMINI_API_KEY = "AIzaSyAT-xkIOSDuq3EKa6lJONuJPnykXTECgZ0"
    
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val safetyAiRepository by lazy { 
        if (GEMINI_API_KEY.isNotBlank() && !GEMINI_API_KEY.contains("YOUR_API_KEY_HERE")) {
            SafetyAiRepository(GEMINI_API_KEY)
        } else {
            null
        }
    }
    
    private val dashboardViewModel by viewModels<DashboardViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DashboardViewModel(safetyAiRepository) as T
                }
            }
        }
    )
    
    private val quizViewModel by viewModels<QuizViewModel>()
    
    private val incidentViewModel by viewModels<IncidentViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return IncidentViewModel(db.incidentDao(), safetyAiRepository) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RakshaKavachTheme {
                MainScreen(
                    dashboardViewModel,
                    incidentViewModel,
                    quizViewModel
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    dashboardViewModel: DashboardViewModel,
    incidentViewModel: IncidentViewModel,
    quizViewModel: QuizViewModel
) {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { padding ->
        NavGraph(
            navController = navController,
            dashboardViewModel = dashboardViewModel,
            incidentViewModel = incidentViewModel,
            quizViewModel = quizViewModel,
            modifier = Modifier.padding(padding)
        )
    }
}
