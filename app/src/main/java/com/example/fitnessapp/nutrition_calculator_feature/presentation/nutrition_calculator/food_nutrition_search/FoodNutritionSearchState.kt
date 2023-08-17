package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_nutrition_search

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem

data class FoodNutritionSearchState(
    val query: String = "",
    val foodItems: List<FoodItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
