package com.example.fitnessapp.history_feature.data.mappers

import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyActivitiesEntity
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyNutritionEntity
import com.example.fitnessapp.daily_overview_feature.domain.model.MealDetails
import com.example.fitnessapp.history_feature.data.local.entity.ActivityHistoryEntity
import com.example.fitnessapp.history_feature.data.local.entity.NutritionHistoryEntity
import com.example.fitnessapp.history_feature.domain.model.Activity
import com.example.fitnessapp.history_feature.domain.model.Meal

fun DailyNutritionEntity.toNutritionHistoryEntity(day: Int, month: Int, year: Int): NutritionHistoryEntity {
    return NutritionHistoryEntity(
        name = this.name,
        servingSize = this.servingSize,
        calories = this.calories,
        carbs = this.carbs,
        fat = this.fat,
        protein = this.protein,
        meal = this.meal,
        day = day,
        month = month,
        year = year
    )
}

fun DailyActivitiesEntity.toActivityHistoryEntity(day: Int, month: Int, year: Int): ActivityHistoryEntity {
    return ActivityHistoryEntity(
        name = this.name,
        caloriesBurned = this.caloriesBurned,
        duration = this.duration,
        day = day,
        month = month,
        year = year
    )
}

fun NutritionHistoryEntity.toMeal(): Meal {
    return Meal(
        meal = this.meal,
        name = this.name,
        servingSize = this.servingSize,
        calories = this.calories,
        carbs = this.carbs,
        protein = this.protein,
        fat = this.fat,
        day = this.day
    )
}

fun mapMealToMealDetails(meals: List<Meal>, day: Int): List<MealDetails> {
    val filteredMeals = meals.filter { it.day == day }
    val mealDetailsMap = filteredMeals.groupBy {
        it.meal
    }

    return mealDetailsMap.map {(meal, mealEntities) ->
        MealDetails(
            meal = meal,
            ingredients = mealEntities.map { it.name },
            servingSize = mealEntities.sumOf { it.servingSize },
            calories = mealEntities.sumOf { it.calories },
            carbs = mealEntities.sumOf { it.carbs },
            protein = mealEntities.sumOf { it.protein },
            fat = mealEntities.sumOf { it.fat },
        )
    }
}

fun ActivityHistoryEntity.toActivity(): Activity {
    return Activity(
        name = this.name,
        burnedCalories = this.caloriesBurned.toDouble(),
        performedActivitiesDuration = this.duration,
        day = this.day
    )
}
