package com.example.fitnessapp.nutrition_calculator_feature.data.repository

import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.RecipesDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.RecipesEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toRecipe
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toRecipeEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.RecipesApi
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipesRepositoryImpl(
    private val recipesApi: RecipesApi,
    private val recipesDao: RecipesDao
) : RecipesRepository {

    override fun getRecipes(query: String): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading())

        try {
            val response = recipesApi.getRecipes(query = query)
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()?.hits?.map { it.toRecipe() }))
            } else {
                emit(Resource.Error(message = "${response.errorBody()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "$e"))
        }
    }

    override fun getIsRecipeSaved(recipe: Recipe): Flow<Boolean> = flow {
        val savedRecipes = recipesDao.getAllRecipes()
        emit(savedRecipes.contains(recipe.toRecipeEntity()))
    }

    override suspend fun saveRecipe(recipe: Recipe) {
        recipesDao.upsertRecipe(recipe.toRecipeEntity())
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipesDao.deleteRecipe(recipe.toRecipeEntity())
    }

    override fun getSavedRecipes(): Flow<List<RecipesEntity>> = recipesDao.getAllRecipesAsFlow()
}