package com.example.fitnessapp.daily_overview_feature.data.repository

import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyActivitiesEntity
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyNutritionEntity
import com.example.fitnessapp.daily_overview_feature.domain.model.Activity
import com.example.fitnessapp.daily_overview_feature.domain.repository.OverviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeOverviewRepository : OverviewRepository {

    private val mealPlan = listOf(
        "Breakfast",
        "Lunch",
        "Dinner"
    )

    private var currentUserCaloriesGoal = 2600

    private val dailyNutritionEntityList = mutableListOf(
        DailyNutritionEntity(
            name = "Protein Oat Meal",
            servingSize = 500.0,
            calories = 600.0,
            carbs = 50.0,
            protein = 40.0,
            fat = 8.0,
            meal = "Breakfast"
        ),
        DailyNutritionEntity(
            name = "Dark Chocolate",
            servingSize = 16.5,
            calories = 70.0,
            carbs = 26.0,
            protein = 2.3,
            fat = 1.8,
            meal = "Breakfast"
        ),
        DailyNutritionEntity(
            name = "Protein Shake",
            servingSize = 500.0,
            calories = 800.0,
            carbs = 80.0,
            protein = 50.0,
            fat = 15.0,
            meal = "Lunch"
        ),
        DailyNutritionEntity(
            name = "Rice",
            servingSize = 150.0,
            calories = 300.0,
            carbs = 76.0,
            protein = 12.0,
            fat = 5.0,
            meal = "Dinner"
        ),
        DailyNutritionEntity(
            name = "Minced Beef",
            servingSize = 200.0,
            calories = 400.0,
            carbs = 10.0,
            protein = 45.0,
            fat = 7.0,
            meal = "Dinner"
        )
    )

    private val dailyActivitiesEntityList = mutableListOf(
        DailyActivitiesEntity(
            name = "Back Workout",
            caloriesBurned = "220",
            duration = 45.0
        ),
        DailyActivitiesEntity(
            name = "Biceps Workout",
            caloriesBurned = "50",
            duration = 20.0
        ),
        DailyActivitiesEntity(
            name = "Forearms Workout",
            caloriesBurned = "15",
            duration = 8.0
        )
    )

    override fun getCurrentUserCaloriesRequirements(): Flow<Int?> = flow {
        emit(currentUserCaloriesGoal)
    }

    override fun getMeals(): Flow<List<String>> = flow {
        emit(mealPlan)
    }

    override fun getMealDetails(): Flow<List<DailyNutritionEntity>> = flow {
        emit(dailyNutritionEntityList)
    }

    override fun getActivities(): Flow<List<DailyActivitiesEntity>> = flow {
        emit(dailyActivitiesEntityList)
    }

    override suspend fun deleteActivity(activity: Activity) {
        dailyActivitiesEntityList.removeIf {
            it.name == activity.name && it.caloriesBurned == activity.caloriesBurned.toInt().toString() && it.duration == activity.duration
        }
    }

    override suspend fun resetMeal(meal: String) {
        dailyNutritionEntityList.removeIf {
            it.meal == meal
        }
    }
}