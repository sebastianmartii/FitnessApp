package com.example.fitnessapp.nutrition_calculator_feature.data.repository

import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyNutritionDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.MealDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toMealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.CustomMealPlanCreatorRepository
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlan
import kotlinx.coroutines.flow.Flow

class CustomMealPlanCreatorRepositoryImpl(
    private val mealDao: MealDao,
    private val dailyNutritionDao: DailyNutritionDao
) : CustomMealPlanCreatorRepository {

    override suspend fun changeMealPlan(mealPlan: MealPlan) {
        mealDao.deleteMealPlan()
        dailyNutritionDao.resetDailyNutrition()
        mealDao.insertMealPlan(mealPlan.toMealPlanEntity())
    }

    override fun getMealPlan(): Flow<MealPlanEntity> = mealDao.getMealPlan()
}