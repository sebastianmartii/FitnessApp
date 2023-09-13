package com.example.fitnessapp.daily_overview_feature.data.repository

import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyActivitiesDao
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyNutritionDao
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyNutritionEntity
import com.example.fitnessapp.daily_overview_feature.domain.repository.OverviewRepository
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.MealDao
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OverviewRepositoryImpl(
    private val currentUserDao: CurrentUserDao,
    private val dailyNutritionDao: DailyNutritionDao,
    private val dailyActivitiesDao: DailyActivitiesDao,
    private val mealDao: MealDao
) : OverviewRepository {

    override fun getCurrentUserCaloriesRequirements(): Flow<Int> {
        return currentUserDao.getCurrentUserCaloriesRequirements()
    }

    override fun getMeals(): Flow<List<String>> = mealDao.getMeals().map { json ->
        val listType = object : TypeToken<List<String>>() {}.type
        Gson().fromJson(json, listType)
    }

    override fun getMealDetails(): Flow<List<DailyNutritionEntity>> = dailyNutritionDao.getDailyNutrition()
}