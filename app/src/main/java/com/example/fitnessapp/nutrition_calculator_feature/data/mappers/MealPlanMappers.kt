package com.example.fitnessapp.nutrition_calculator_feature.data.mappers

import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlan

fun MealPlan.toMealPlanEntity(): MealPlanEntity {
    return MealPlanEntity(
        id = 1,
        planName = this.name,
        mealPlanType = this.type,
        meals = this.meals
    )
}