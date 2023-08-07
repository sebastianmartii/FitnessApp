package com.example.fitnessapp.nutrition_calculator_feature.presentation

sealed interface MealPlan {
    data class ThreeMealPlan(
        val breakfast: String,
        val secondBreakfast: String,
        val lunch: String,
        val snack: String,
        val dinner: String
    ) : MealPlan
    data class FourDayMealPlan(
        val breakfast: String,
        val secondBreakfast: String,
        val lunch: String,
        val dinner: String
    ) : MealPlan
    data class FiveDayMealPlan(
        val breakfast: String,
        val lunch: String,
        val dinner: String
    ) : MealPlan
    data class CustomMealPlan(val customMealList: List<String>) : MealPlan
}