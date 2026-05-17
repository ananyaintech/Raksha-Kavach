package com.ananya.rakshakavach.data.model

data class SafetyTask(
    val id: Int,
    val title: String,
    val riskLevel: Int,
    val weather: String,
    val temperature: String
)