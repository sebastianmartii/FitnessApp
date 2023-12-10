package com.example.fitnessapp.nutrition_calculator_feature.data.repository

import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toRecipe
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.RecipesApi
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipesRepositoryImpl(
    private val recipesApi: RecipesApi,
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
}