package com.example.fitnessapp.daily_overview_feature.data.mappers

import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyNutritionEntity
import com.example.fitnessapp.daily_overview_feature.domain.model.MealDetails


fun mapDailyNutritionEntityToMealDetails(entities: List<DailyNutritionEntity>): List<MealDetails> {
    val mealDetailsMap = entities.groupBy {
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
            fat = mealEntities.sumOf { it.fat }
        )
    }
}

fun MealDetails?.toDailyNutritionCaloriesString(): String {
    if (this == null) {
        return ""
    }
    return "${this.calories.toInt()}kcal"
}