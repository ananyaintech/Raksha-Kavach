package com.ananya.rakshakavach.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ananya.rakshakavach.data.local.Incident
import com.ananya.rakshakavach.data.local.IncidentDao
import com.ananya.rakshakavach.repository.SafetyAiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IncidentViewModel(
    private val incidentDao: IncidentDao,
    private val safetyAiRepository: SafetyAiRepository?
) : ViewModel() {

    val allIncidents: StateFlow<List<Incident>> = incidentDao.getAllIncidents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // AI Analysis State
    var aiAnalysisResult by mutableStateOf("")
    var isAiAnalyzing by mutableStateOf(false)

    fun addIncident(title: String, description: String, severity: String) {
        viewModelScope.launch {
            val incident = Incident(
                title = title,
                description = description,
                severity = severity
            )
            incidentDao.insertIncident(incident)
        }
    }

    fun analyzeIncidentWithAI(incidentId: Int, description: String) {
        if (safetyAiRepository == null) {
            aiAnalysisResult = "AI Repository not initialized."
            return
        }

        viewModelScope.launch {
            isAiAnalyzing = true
            val result = safetyAiRepository.analyzeIncident(description)
            aiAnalysisResult = "$incidentId:$result"
            isAiAnalyzing = false
        }
    }

    fun clearAiAnalysis() {
        aiAnalysisResult = ""
    }
}
