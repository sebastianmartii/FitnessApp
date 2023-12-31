package com.example.fitnessapp.nutrition_calculator_feature.data.mappers

import androidx.core.text.isDigitsOnly
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyNutritionEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.nutrition.dto.Item
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import com.example.fitnessapp.nutrition_calculator_feature.presentation.NutritionTabRowItem

fun NutritionTabRowItem.toTabTitle(): String {
    return when(this) {
        NutritionTabRowItem.CALCULATOR -> "Calculator"
        NutritionTabRowItem.RECIPES -> "Recipes"
        NutritionTabRowItem.MEAL_PLAN -> "Meal Plan"
    }
}

fun Item.toFoodItem(): FoodItem {
    return FoodItem(
        name = name,
        servingSize = servingSize,
        calories = calories,
        carbs = carbs,
        protein = protein,
        saturatedFat = saturatedFat,
        totalFat = totalFat,
        sugar = sugar,
        fiber = fiber
    )
}
fun FoodItem.toFoodItemEntity(): FoodItemEntity {
    return FoodItemEntity(
        name = name,
        servingSize = servingSize,
        calories = calories,
        carbs = carbs,
        protein = protein,
        saturatedFat = saturatedFat,
        fat = totalFat,
        sugar = sugar,
        fiber = fiber
    )
}

fun FoodItemEntity.toFoodItem(): FoodItem {
    return FoodItem(
        name = name,
        servingSize = servingSize,
        calories = calories,
        carbs = carbs,
        protein = protein,
        totalFat = fat,
        saturatedFat = saturatedFat,
        sugar = sugar,
        fiber = fiber
    )
}

fun String.toGrams(): Double {
    if (this.isBlank() || !this.isDigitsOnly()) {
        return 0.0
    }
    return this.toDouble()
}

fun String.toCalories(): Double {
    if (this.isBlank() || !this.isDigitsOnly()) {
        return 0.0
    }
    return this.toDouble()
}

fun FoodItem.toDailyNutrition(meal: String): DailyNutritionEntity {
    return DailyNutritionEntity(
        name = this.name,
        servingSize = this.servingSize,
        calories = this.calories,
        carbs = this.carbs,
        protein = this.protein,
        fat = this.totalFat,
        meal = meal
    )
}