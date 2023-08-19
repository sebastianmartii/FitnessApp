package com.example.fitnessapp.nutrition_calculator_feature.data.mappers

import androidx.core.text.isDigitsOnly
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.nutrition.dto.Item
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import com.example.fitnessapp.nutrition_calculator_feature.presentation.TabRowItem

fun TabRowItem.toTabTitle(): String {
    return when(this) {
        TabRowItem.CALCULATOR -> "Calculator"
        TabRowItem.RECIPES -> "Recipes"
        TabRowItem.MEAL_PLAN -> "Meal Plan"
    }
}

fun Item.toFoodItemEntity(): FoodItemEntity {
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