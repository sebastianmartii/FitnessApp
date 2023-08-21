package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.food_item_creator

data class FoodItemCreatorState(
    val name: String = "",
    val servingSize: String = "",
    val calories: String = "",
    val carbs: String = "",
    val protein: String = "",
    val totalFat: String = "",
    val saturatedFat: String = "",
    val fiber: String = "",
    val sugar: String = "",
)
