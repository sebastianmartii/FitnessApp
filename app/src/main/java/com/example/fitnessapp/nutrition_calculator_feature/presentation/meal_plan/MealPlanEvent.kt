package com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan

sealed interface MealPlanEvent {

    data class OnMealPlanExpandedChange(val isExpanded: Boolean, val type: MealPlanType) : MealPlanEvent
    data class OnMealPlanSelectedChange(val type: MealPlanType, val plan: MealPlan) : MealPlanEvent
    data class OnDeleteMeal(val mealIndex: Int) : MealPlanEvent
    object OnAddMeal : MealPlanEvent
    object OnSheetOpen : MealPlanEvent
    object OnSheetClose : MealPlanEvent
    data class OnCustomMealPlanSave(val plan: MealPlan) : MealPlanEvent
    data class OnMealNameChange(val name: String, val index: Int) : MealPlanEvent
}