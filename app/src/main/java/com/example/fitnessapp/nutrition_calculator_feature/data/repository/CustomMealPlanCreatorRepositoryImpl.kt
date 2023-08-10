package com.example.fitnessapp.nutrition_calculator_feature.data.repository

import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.MealDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.CustomMealPlanCreatorRepository
import kotlinx.coroutines.flow.Flow

class CustomMealPlanCreatorRepositoryImpl(
    private val mealDao: MealDao
) : CustomMealPlanCreatorRepository {

    override suspend fun changeMealPlan(mealPlanEntity: MealPlanEntity) {
        mealDao.insertMealPLan(mealPlanEntity)
    }

    override suspend fun deleteMealPlan() {
        mealDao.deleteMealPlan()
    }

    override fun getMealPlan(): Flow<MealPlanEntity> = mealDao.getMealPLan()

    override fun getMealPlanName(): Flow<String> = mealDao.getMealPLanName()
}