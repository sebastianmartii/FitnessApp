package com.example.fitnessapp.history_feature.domain.repository

import com.example.fitnessapp.history_feature.data.local.entity.ActivityHistoryEntity
import com.example.fitnessapp.history_feature.data.local.entity.NutritionHistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getMonthActivities(month: Int): Flow<List<ActivityHistoryEntity>>
    fun getMonthNutrition(month: Int): Flow<List<NutritionHistoryEntity>>
}