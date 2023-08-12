package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator

data class FoodItemCreatorState(
    val name: String = "",
    val foodComponents: List<FoodComponent> = emptyList()
)
