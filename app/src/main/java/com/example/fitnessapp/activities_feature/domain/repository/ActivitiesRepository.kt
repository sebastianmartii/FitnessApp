package com.example.fitnessapp.activities_feature.domain.repository

import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ActivitiesRepository {

    fun getActivitiesForIntensityLevel(intensityLevel: Int): Flow<Resource<List<Activity>>>

    fun getCaloriesBurnedForActivity(activityId: String, weight: Double, duration: Double)
}