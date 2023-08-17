package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem

data class NutritionCalculatorState(
    val cachedProducts: List<FoodItem> = emptyList(),
)
