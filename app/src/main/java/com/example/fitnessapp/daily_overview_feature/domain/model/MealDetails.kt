package com.example.fitnessapp.daily_overview_feature.domain.model

data class MealDetails(
    val meal: String = "",
    val areVisible: Boolean = false,
    val ingredients: List<String> = emptyList(),
    val servingSize: Double = 0.0,
    val calories: Double = 0.0,
    val carbs: Double = 0.0,
    val protein: Double = 0.0,
    val fat: Double = 0.0,
)
