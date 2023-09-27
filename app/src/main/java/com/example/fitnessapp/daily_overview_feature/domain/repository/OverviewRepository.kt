package com.example.fitnessapp.daily_overview_feature.domain.repository

import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyActivitiesEntity
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyNutritionEntity
import com.example.fitnessapp.daily_overview_feature.domain.model.Activity
import kotlinx.coroutines.flow.Flow

interface OverviewRepository {

    fun getCurrentUserCaloriesRequirements(): Flow<Int?>
    fun getMeals(): Flow<List<String>>
    fun getMealDetails(): Flow<List<DailyNutritionEntity>>
    fun getActivities(): Flow<List<DailyActivitiesEntity>>
    suspend fun deleteActivity(activity: Activity)
    suspend fun resetMeal(meal: String)
}