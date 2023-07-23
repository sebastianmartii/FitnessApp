package com.example.fitnessapp.profile_feature.data.remote

import com.example.fitnessapp.profile_feature.data.remote.dto.CaloriesRequirementsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CaloriesGoalApi {

    @GET("/dailycalorie")
    suspend fun getCaloriesRequirements(
        @Query("age") age: Int,
        @Query("gender") gender: String,
        @Query("height") height: Float,
        @Query("weight") weight: Float,
        @Query("activitylevel") activityLevel: String,
    ): Response<CaloriesRequirementsDto>
}