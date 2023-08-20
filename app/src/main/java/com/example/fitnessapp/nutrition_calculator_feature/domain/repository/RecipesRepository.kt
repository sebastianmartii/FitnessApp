package com.example.fitnessapp.nutrition_calculator_feature.domain.repository

import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.RecipesEntity
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getRecipes(query: String): Flow<Resource<List<Recipe>>>

    fun getSavedRecipes(): Flow<List<RecipesEntity>>

    fun getIsRecipeSaved(recipe: Recipe): Flow<Boolean>

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)
}