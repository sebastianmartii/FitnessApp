package com.example.fitnessapp.history_feature.data.repository

import com.example.fitnessapp.history_feature.data.local.dao.ActivityHistoryDao
import com.example.fitnessapp.history_feature.data.local.dao.NutritionHistoryDao
import com.example.fitnessapp.history_feature.data.local.entity.ActivityHistoryEntity
import com.example.fitnessapp.history_feature.data.local.entity.NutritionHistoryEntity
import com.example.fitnessapp.history_feature.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryRepositoryImpl(
    private val activityHistoryDao: ActivityHistoryDao,
    private val nutritionHistoryDao: NutritionHistoryDao
) : HistoryRepository {

    override fun getMonthActivities(month: Int): Flow<List<ActivityHistoryEntity>> = activityHistoryDao.getMonthActivities(month)

    override fun getMonthNutrition(month: Int): Flow<List<NutritionHistoryEntity>> = nutritionHistoryDao.getMonthNutrition(month)
}