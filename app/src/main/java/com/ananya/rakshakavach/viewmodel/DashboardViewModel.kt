package com.ananya.rakshakavach.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ananya.rakshakavach.data.model.GearItem
import com.ananya.rakshakavach.repository.SafetyAiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(private val safetyAiRepository: SafetyAiRepository?) : ViewModel() {

    // PPE Gear
    private val _gearItems = mutableStateListOf(
        GearItem("Helmet", false),
        GearItem("Gloves", false),
        GearItem("Safety Harness", false),
        GearItem("Safety Shoes", false),
        GearItem("Goggles", false),
        GearItem("Welding Mask", false),
        GearItem("Reflective Jacket", false),
        GearItem("Ear Protection", false)
    )
    val gearItems: List<GearItem> get() = _gearItems

    // Risk Score
    private val _riskScore = MutableStateFlow(0f)
    val riskScore: StateFlow<Float> = _riskScore.asStateFlow()

    // Task Selection
    var selectedTask by mutableStateOf("WELDING AT HEIGHT")

    // AI Recommendations
    var aiRecommendation by mutableStateOf("")
    var isAiLoading by mutableStateOf(false)

    // User Profile
    var userName by mutableStateOf("Ananya Chaurasia")
    var workerId by mutableStateOf("RK-2025-101")
    var userPhone by mutableStateOf("+91 XXXXX XXXXX")
    var userDept by mutableStateOf("Industrial Safety")
    var isEditingProfile by mutableStateOf(false)

    init {
        updateRiskScore()
    }

    fun toggleGear(index: Int) {
        if (index in _gearItems.indices) {
            val item = _gearItems[index]
            _gearItems[index] = item.copy(isAvailable = !item.isAvailable)
            updateRiskScore()
        }
    }

    private fun updateRiskScore() {
        val availableCount = _gearItems.count { it.isAvailable }
        val totalCount = _gearItems.size
        _riskScore.value = (1f - (availableCount.toFloat() / totalCount.toFloat())) * 100f
    }

    fun riskLabel(): String {
        return when {
            _riskScore.value < 20 -> "SAFE"
            _riskScore.value < 50 -> "MODERATE RISK"
            else -> "HIGH RISK"
        }
    }

    fun riskWarningMessage(): String {
        val missing = _gearItems.filter { !it.isAvailable }.map { it.name.lowercase() }
        if (missing.isEmpty()) return "All systems go. Stay alert."
        return "Worker missing ${missing.joinToString(", ")}. Increased injury risk detected."
    }
    
    fun completionPercentage(): Int {
        val availableCount = _gearItems.count { it.isAvailable }
        return ((availableCount.toFloat() / _gearItems.size.toFloat()) * 100).toInt()
    }

    fun generateAiRecommendation() {
        val missing = _gearItems.filter { !it.isAvailable }.map { it.name }
        if (safetyAiRepository == null) {
            aiRecommendation = "AI Repository not initialized. Please check API Key."
            return
        }

        viewModelScope.launch {
            isAiLoading = true
            aiRecommendation = safetyAiRepository.getSafetyRecommendation(missing, selectedTask)
            isAiLoading = false
        }
    }

    fun updateProfile(name: String, phone: String, dept: String) {
        userName = name
        userPhone = phone
        userDept = dept
        isEditingProfile = false
    }
}
