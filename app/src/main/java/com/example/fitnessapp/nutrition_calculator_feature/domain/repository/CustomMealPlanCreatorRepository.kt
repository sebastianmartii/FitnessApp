package com.example.fitnessapp.nutrition_calculator_feature.domain.repository

import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlan
import kotlinx.coroutines.flow.Flow

interface CustomMealPlanCreatorRepository {

    suspend fun changeMealPlan(mealPlan: MealPlan)

    fun getMealPlan(): Flow<MealPlanEntity>
}