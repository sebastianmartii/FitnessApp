package com.example.fitnessapp.nutrition_calculator_feature.presentation.custom_meal_plan_creator

sealed interface CustomMealPlanCreatorEvent {
    object OnDeleteMealPlan : CustomMealPlanCreatorEvent
    object OnSaveMealPlan : CustomMealPlanCreatorEvent
    object OnAddMeal : CustomMealPlanCreatorEvent
    data class OnDeleteMeal(val mealIndex: Int) : CustomMealPlanCreatorEvent
    object OnChangeMealPlanName : CustomMealPlanCreatorEvent
    object OnChangeMealName : CustomMealPlanCreatorEvent
    data class OnMealPlanNameChange(val newName: String) : CustomMealPlanCreatorEvent
    data class OnMealNameChange(val newName: String, val mealIndex: Int) : CustomMealPlanCreatorEvent
}