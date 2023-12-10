package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe

data class RecipeSearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val isSearchBarActive: Boolean = false,
    val inspectedRecipe: Recipe? = null
)
