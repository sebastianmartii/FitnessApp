package com.example.fitnessapp.activities_feature.data.remote

import com.example.fitnessapp.activities_feature.data.remote.dto.ActivitiesDto
import com.example.fitnessapp.activities_feature.data.remote.dto.BurnedCaloriesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivitiesApi {

    @GET("/activities")
    suspend fun getActivitiesPerIntensity(
        @Query("intensitylevel") intensityLevel: Int
    ): Response<ActivitiesDto>

    @GET("/burnedcalorie")
    suspend fun getCaloriesFromActivity(
        @Query("activityid") activityID: String,
        @Query("activitymin") activityMin: Double,
        @Query("weight") weight: Double,
    ): Response<BurnedCaloriesDto>
}