package com.example.fitnessapp.nutrition_calculator_feature.data.mappers

import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.dto.Item
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import com.example.fitnessapp.nutrition_calculator_feature.presentation.TabRowItem
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator.FoodComponentType

fun TabRowItem.toTabTitle(): String {
    return when(this) {
        TabRowItem.NUTRITION_CALCULATOR -> "Nutrition Calculator"
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

fun FoodComponentType.toLabel(): String {
    return when(this) {
        FoodComponentType.NAME -> "Name"
        FoodComponentType.SERVING_SIZE -> "Serving Size"
        FoodComponentType.CARBS -> "Carbohydrates"
        FoodComponentType.PROTEIN -> "Protein"
        FoodComponentType.FAT -> "Total Fat"
        FoodComponentType.SATURATED_FAT -> "Saturated Fat"
        FoodComponentType.FIBER -> "Fiber"
        FoodComponentType.SUGAR -> "Sugar"
        FoodComponentType.CALORIES -> "Calories Per Serving"
    }
}