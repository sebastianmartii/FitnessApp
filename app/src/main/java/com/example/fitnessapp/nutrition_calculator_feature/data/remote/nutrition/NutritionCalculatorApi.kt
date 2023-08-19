package com.example.fitnessapp.nutrition_calculator_feature.data.remote.nutrition

import com.example.fitnessapp.nutrition_calculator_feature.data.remote.nutrition.dto.NutritionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NutritionCalculatorApi {

    @GET("/v1/nutrition")
    suspend fun getFoodNutrition(
        @Query("query") query: String
    ): Response<NutritionDto>
}