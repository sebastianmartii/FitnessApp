package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem

sealed interface NutritionCalculatorEvent {
    data class OnFoodItemSelectedChange(val foodItem: FoodItem) : NutritionCalculatorEvent
    data class OnFoodItemDelete(val foodItem: FoodItem) : NutritionCalculatorEvent
}