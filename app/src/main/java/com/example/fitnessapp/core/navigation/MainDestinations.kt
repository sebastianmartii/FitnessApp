package com.example.fitnessapp.core.navigation

sealed class MainDestinations(val route: String) {
    object FoodItemCreator : MainDestinations(route = "food_item_creator")
    object FoodNutritionSearch : MainDestinations(route = "food_nutrition_search")
    object RecipeDetails : MainDestinations(route = "recipe_details")
    object ActivityCreator : MainDestinations(route = "activity_creator")
    object CaloriesGoalCalculator : MainDestinations(route = "calories_goal_calculator")
}