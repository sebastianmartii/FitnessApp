package com.example.fitnessapp.nutrition_calculator_feature.presentation

sealed interface NutritionEvent {

    data class OnTabChange(val nutritionTabRowItem: NutritionTabRowItem, val tabIndex: Int) : NutritionEvent
}