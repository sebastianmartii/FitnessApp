package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem

sealed interface NutritionCalculatorEvent {

    data class OnQueryChange(val query: String) : NutritionCalculatorEvent
    data class OnIsSearchBarActiveChange(val isActive: Boolean) : NutritionCalculatorEvent
    data class OnNutritionCalculate(val query: String) : NutritionCalculatorEvent
    data class OnFoodItemSelectedChange(val foodItem: FoodItem, val isSelected: Boolean) : NutritionCalculatorEvent
    data class OnFoodItemExpandedChange(val foodItem: FoodItem, val isExpanded: Boolean) : NutritionCalculatorEvent
}