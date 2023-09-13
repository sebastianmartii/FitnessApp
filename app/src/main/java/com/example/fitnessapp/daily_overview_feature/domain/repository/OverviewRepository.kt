package com.example.fitnessapp.daily_overview_feature.domain.repository

import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyNutritionEntity
import kotlinx.coroutines.flow.Flow

interface OverviewRepository {

    fun getCurrentUserCaloriesRequirements(): Flow<Int>
    fun getMeals(): Flow<List<String>>
    fun getMealDetails(): Flow<List<DailyNutritionEntity>>
}