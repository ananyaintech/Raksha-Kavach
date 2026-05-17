package com.ananya.rakshakavach.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incidents")
data class Incident(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val severity: String, // High, Medium, Low
    val timestamp: Long = System.currentTimeMillis()
)
