package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator

sealed interface FoodItemCreatorEvent {
    data class OnFoodComponentChange(val value: String, val index: Int) : FoodItemCreatorEvent
    object OnFoodItemCreated : FoodItemCreatorEvent
}