package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem

data class NutritionCalculatorState(
    val isMealSelectionDialogVisible: Boolean = false,
    val isFABVisible: Boolean = false,
    val selectedMeal: String? = null,
    val cachedProducts: List<FoodItem> = emptyList()
)
