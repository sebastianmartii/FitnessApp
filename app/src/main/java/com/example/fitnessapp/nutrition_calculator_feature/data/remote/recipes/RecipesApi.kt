package com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes

import com.example.fitnessapp.core.util.ApiKeys
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.dto.RecipesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {

    @GET("/api/recipes/v2")
    suspend fun getRecipes(
        @Query("type") type: String = "any",
        @Query("app_id") apID: String = ApiKeys.RECIPES_AP_ID,
        @Query("app_key") apiKey: String = ApiKeys.RECIPES_API_KEY,
        @Query("q") query: String
    ): Response<RecipesDto>
}