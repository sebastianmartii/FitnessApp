package com.example.fitnessapp.daily_overview_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyNutritionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
