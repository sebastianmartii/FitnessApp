package com.example.fitnessapp.nutrition_calculator_feature.data.repository

import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.MealDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toMealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.CustomMealPlanCreatorRepository
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlan
import kotlinx.coroutines.flow.Flow

class CustomMealPlanCreatorRepositoryImpl(
    private val mealDao: MealDao
) : CustomMealPlanCreatorRepository {

    override suspend fun changeMealPlan(mealPlan: MealPlan) {
        mealDao.deleteMealPlan()
        mealDao.insertMealPlan(mealPlan.toMealPlanEntity())
    }

    override fun getMealPlan(): Flow<MealPlanEntity> = mealDao.getMealPlan()
}