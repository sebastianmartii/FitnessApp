package com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan

data class MealPlanState(
    val fiveMealPlan: MealPlan = MealPlan(),
    val fourMealPlan: MealPlan = MealPlan(),
    val threeMealPlan: MealPlan = MealPlan(),
    val customMealPlan: MealPlan = MealPlan(),
    val selectedMealPlan: MealPlanType? = null
)
