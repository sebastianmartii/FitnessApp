package com.example.fitnessapp.history_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActivityHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val caloriesBurned: String,
    val duration: Double,
    val year: Int,
    val month: Int,
    val day: Int
)
