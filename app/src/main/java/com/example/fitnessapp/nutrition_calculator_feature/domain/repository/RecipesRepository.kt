package com.example.fitnessapp.nutrition_calculator_feature.domain.repository

import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getRecipes(query: String): Flow<Resource<List<Recipe>>>
}