package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator

sealed interface FoodItemCreatorEvent {
    data class OnNameChange(val name: String) : FoodItemCreatorEvent
    data class OnServingSizeChange(val servingSize: String) : FoodItemCreatorEvent
    data class OnCaloriesChange(val calories: String) : FoodItemCreatorEvent
    data class OnCarbsChange(val carbs: String) : FoodItemCreatorEvent
    data class OnProteinChange(val protein: String) : FoodItemCreatorEvent
    data class OnTotalFatChange(val totalFat: String) : FoodItemCreatorEvent
    data class OnSaturatedFatChange(val saturatedFat: String) : FoodItemCreatorEvent
    data class OnFiberChange(val fiber: String) : FoodItemCreatorEvent
    data class OnSugarChange(val sugar: String) : FoodItemCreatorEvent
    data class OnFoodItemCreated(val isFoodItemValid: Boolean) : FoodItemCreatorEvent
}