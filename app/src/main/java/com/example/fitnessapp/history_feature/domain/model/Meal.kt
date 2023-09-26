package com.example.fitnessapp.history_feature.domain.model

data class Meal(
    val meal: String,
    val name: String,
    val servingSize: Double,
    val calories: Double,
    val carbs: Double,
    val protein: Double,
    val fat: Double,
    val day: Int
)
