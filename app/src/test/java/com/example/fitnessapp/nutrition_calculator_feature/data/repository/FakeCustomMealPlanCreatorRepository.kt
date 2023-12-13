package com.example.fitnessapp.nutrition_calculator_feature.data.repository

import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toMealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.CustomMealPlanCreatorRepository
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlan
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCustomMealPlanCreatorRepository : CustomMealPlanCreatorRepository {

    private var mealPlanEntity = MealPlanEntity(
        mealPlanType = MealPlanType.THREE,
        planName = "Three Meal Plan",
        meals = listOf(
            "Breakfast",
            "Lunch",
            "Dinner"
        )
    )

    override suspend fun changeMealPlan(mealPlan: MealPlan) {
        mealPlanEntity = mealPlan.toMealPlanEntity()
    }

    override fun getMealPlan(): Flow<MealPlanEntity> = flow {
        emit(mealPlanEntity)
    }
}