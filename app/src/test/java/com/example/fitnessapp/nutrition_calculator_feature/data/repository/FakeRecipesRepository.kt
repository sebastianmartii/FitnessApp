package com.example.fitnessapp.nutrition_calculator_feature.data.repository

import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRecipesRepository : RecipesRepository {

    private val recipes = listOf(
        Recipe(
            label = "dinner",
            dietLabels = emptyList(),
            ingredients = emptyList(),
            protein = 70.0,
            servingSize = 500.0,
            sugar = 4.0,
            saturatedFat = 0.0,
            smallImage = "",
            carbs = 50.0,
            calories = 400.0,
            fiber = 2.0,
            fat = 1.0,
            externalUrl = "",
            bigImage = "",
        )
    )

    override fun getRecipes(query: String): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Success(data = recipes.filter { it.label.contains(query) }))
    }
}