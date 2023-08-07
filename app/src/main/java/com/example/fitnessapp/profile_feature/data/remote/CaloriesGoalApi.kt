package com.example.fitnessapp.profile_feature.data.remote

import com.example.fitnessapp.profile_feature.data.remote.dto.CaloriesRequirementsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CaloriesGoalApi {

    @GET("/dailycalorie")
    suspend fun getCaloriesRequirements(
        @Query("age") age: Int,
        @Query("gender") gender: String,
        @Query("height") height: Int,
        @Query("weight") weight: Int,
        @Query("activitylevel") activityLevel: String,
    ): Response<CaloriesRequirementsDto>
}