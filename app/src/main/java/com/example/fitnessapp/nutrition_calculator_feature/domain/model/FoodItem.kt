package com.example.fitnessapp.nutrition_calculator_feature.domain.model

data class FoodItem(
    val name: String,
    val servingSize: Double,
    val calories: Double,
    val carbs: Double,
    val protein: Double,
    val totalFat: Double,
    val saturatedFat: Double,
    val sugar: Double,
    val fiber: Double,
)
