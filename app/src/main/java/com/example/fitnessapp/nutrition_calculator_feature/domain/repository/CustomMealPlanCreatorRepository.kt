package com.example.fitnessapp.nutrition_calculator_feature.domain.repository

import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import kotlinx.coroutines.flow.Flow

interface CustomMealPlanCreatorRepository {

    suspend fun changeMealPlan(mealPlanEntity: MealPlanEntity)

    suspend fun deleteMealPlan()

    fun getMealPlan(): Flow<MealPlanEntity>

    fun getMealPlanName(): Flow<String>
}