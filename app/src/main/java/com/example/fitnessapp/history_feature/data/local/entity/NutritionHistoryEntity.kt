package com.example.fitnessapp.history_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NutritionHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val servingSize: Double,
    val calories: Double,
    val carbs: Double,
    val protein: Double,
    val fat: Double,
    val meal: String,
    val year: Int,
    val month: Int,
    val day: Int
)
