package com.example.fitnessapp.profile_feature.data.remote

import com.example.fitnessapp.profile_feature.data.remote.dto.CaloriesRequirementsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CaloriesGoalApi {

    @Headers(
        "X-RapidAPI-Key: 1217c0f6bamshdafb907a91cdb2cp173d5fjsnfb704dcd522b",
        "X-RapidAPI-Host: fitness-calculator.p.rapidapi.com"
    )
    @GET("/dailycalorie")
    suspend fun getCaloriesRequirements(
        @Query("age") age: Int,
        @Query("gender") gender: String,
        @Query("height") height: Float,
        @Query("weight") weight: Float,
        @Query("activitylevel") activityLevel: String,
    ): Response<CaloriesRequirementsDto>
}