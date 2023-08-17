package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_nutrition_search

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem

sealed interface FoodNutritionSearchEvent {
    data class OnQueryChange(val query: String) : FoodNutritionSearchEvent
    data class OnFoodItemSelect(val foodItem: FoodItem) : FoodNutritionSearchEvent
    object OnFoodItemsSave : FoodNutritionSearchEvent
}