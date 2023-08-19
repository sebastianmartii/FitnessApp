package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe_search

sealed interface RecipeSearchEvent {

    data class OnQueryChange(val query: String) : RecipeSearchEvent
    object OnRecipeSearch : RecipeSearchEvent
}