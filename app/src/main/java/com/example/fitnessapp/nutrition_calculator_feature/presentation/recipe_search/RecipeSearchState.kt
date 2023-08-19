package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe_search

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe

data class RecipeSearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList()
)
