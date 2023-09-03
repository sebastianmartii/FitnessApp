package com.example.fitnessapp.activities_feature.domain.repository

import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ActivitiesRepository {

    fun getActivitiesForIntensityLevel(intensityLevel: Int): Flow<Resource<List<Activity>>>

    suspend fun getCaloriesBurnedForActivity(activity: Activity, duration: Double)

    fun getSavedActivities(): Flow<List<SavedActivitiesEntity>>
    suspend fun saveActivity(name: String, description: String?, duration: Double, burnedCalories: String)
}