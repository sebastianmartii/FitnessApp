package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem

sealed interface NutritionCalculatorEvent {
    data class OnFoodItemSelectedChange(val foodItem: FoodItem, val itemIndex: Int) : NutritionCalculatorEvent
    data class OnFoodItemDelete(val foodItem: FoodItem) : NutritionCalculatorEvent
    data class OnFoodItemsAdd(val areMealsEmpty: Boolean) : NutritionCalculatorEvent
    data class OnMealSelectionDialogConfirm(val meal: String?) : NutritionCalculatorEvent
    data class OnMealSelectionDialogMealSelect(val meal: String) : NutritionCalculatorEvent
    object OnMealSelectionDialogDismiss : NutritionCalculatorEvent
}