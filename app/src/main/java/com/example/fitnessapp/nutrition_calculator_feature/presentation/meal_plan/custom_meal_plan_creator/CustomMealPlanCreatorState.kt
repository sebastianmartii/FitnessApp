package com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.custom_meal_plan_creator

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Meal

data class CustomMealPlanCreatorState(
    val planName: String = "Custom Meal Plan",
    val mealList: MutableList<Meal> = mutableListOf(Meal()),
    val numberOfMeals: Int = mealList.size,
    val isMealPlanNameEditable: Boolean = false,
    val isMealNameEditable: Boolean = false,
    val isDeleteMealPlanDialogVisible: Boolean = false
)
