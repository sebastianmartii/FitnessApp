package com.example.fitnessapp.daily_overview_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyActivitiesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val caloriesBurned: String,
    val duration: Double
)
