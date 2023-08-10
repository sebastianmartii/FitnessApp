package com.example.fitnessapp.nutrition_calculator_feature.presentation

sealed interface MealPlan {
    data class FiveMealsPlan(
        val type: MealPlanType = MealPlanType.FIVE_MEALS,
        val breakfast: String,
        val secondBreakfast: String,
        val lunch: String,
        val snack: String,
        val dinner: String
    ) : MealPlan
    data class FourMealsPlan(
        val type: MealPlanType = MealPlanType.FOUR_MEALS,
        val breakfast: String,
        val secondBreakfast: String,
        val lunch: String,
        val dinner: String
    ) : MealPlan
    data class ThreeMealsPlan(
        val type: MealPlanType = MealPlanType.THREE_MEALS,
        val breakfast: String,
        val lunch: String,
        val dinner: String
    ) : MealPlan
    data class CustomMealsPlan(
        val name: String,
        val type: MealPlanType = MealPlanType.CUSTOM_PLAN,
        val customMealList: List<String>
    ) : MealPlan
}