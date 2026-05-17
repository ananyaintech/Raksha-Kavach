package com.ananya.rakshakavach.repository

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SafetyAiRepository(apiKey: String) {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )

    suspend fun getSafetyRecommendation(missingGear: List<String>, task: String): String = withContext(Dispatchers.IO) {
        val prompt = """
            You are an Industrial Safety Expert AI.
            The worker is missing the following PPE gear: ${missingGear.joinToString(", ")}.
            The assigned task is: $task.
            
            Provide a concise, bold safety recommendation and action plan.
            Format: High-level warning first, then specific immediate actions.
            Keep it under 3 sentences.
        """.trimIndent()

        try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Unable to generate recommendation at this time."
        } catch (e: Exception) {
            "Error analyzing safety: ${e.localizedMessage}"
        }
    }

    suspend fun analyzeIncident(description: String): String = withContext(Dispatchers.IO) {
        val prompt = """
            Analyze the following industrial incident: "$description".
            Determine:
            1. Severity Level (LOW, MODERATE, HIGH)
            2. Recommended Immediate Actions
            
            Format:
            SEVERITY: [Level]
            ACTIONS:
            - [Action 1]
            - [Action 2]
        """.trimIndent()

        try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Unable to analyze incident."
        } catch (e: Exception) {
            "Error analyzing incident: ${e.localizedMessage}"
        }
    }
}
