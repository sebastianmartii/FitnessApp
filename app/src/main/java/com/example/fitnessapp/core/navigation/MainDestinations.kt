package com.example.fitnessapp.core.navigation

sealed class MainDestinations(val route: String) {
    object Overview : MainDestinations(route = "overview")
    object Nutrition : MainDestinations(route = "nutrition")
    object CustomMealPlanCreator : MainDestinations(route = "meal_plan_creator")
    object FoodItemCreator : MainDestinations(route = "food_item_creator")
}