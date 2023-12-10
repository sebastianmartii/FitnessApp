package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe

sealed interface RecipeSearchEvent {

    data class OnQueryChange(val query: String) : RecipeSearchEvent
    data class OnIsSearchBarActiveChange(val isActive: Boolean) : RecipeSearchEvent
    data class OnNavigateToRecipeDetails(val recipe: Recipe) : RecipeSearchEvent
    object OnRecipeSearch : RecipeSearchEvent
    object OnQueryClear : RecipeSearchEvent
}