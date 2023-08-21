package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe

sealed interface RecipeSearchEvent {

    data class OnQueryChange(val query: String) : RecipeSearchEvent
    data class OnIsSearchBarActiveChange(val isActive: Boolean) : RecipeSearchEvent
    data class OnNavigateToRecipeDetails(val recipeIndex: Int) : RecipeSearchEvent
    data class OnIsRecipeSavedChange(val recipe: Recipe, val isSaved: Boolean) : RecipeSearchEvent
    object OnRecipeSearch : RecipeSearchEvent
    object OnTextFieldClear : RecipeSearchEvent
}