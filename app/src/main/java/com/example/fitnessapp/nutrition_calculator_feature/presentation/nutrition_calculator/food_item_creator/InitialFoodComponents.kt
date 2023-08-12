package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.TakeoutDining

val initialFoodComponents = listOf(
    FoodComponent(
        type = FoodComponentType.NAME,
        value = "",
        leadingIcon = Icons.Default.RestaurantMenu,
        leadingIconDescription = FoodComponentType.NAME.toString(),
    ),
    FoodComponent(
        type = FoodComponentType.SERVING_SIZE,
        value = "",
        leadingIcon = Icons.Default.TakeoutDining,
        leadingIconDescription = FoodComponentType.SERVING_SIZE.toString(),
    ),
    FoodComponent(
        type = FoodComponentType.CALORIES,
        value = "",
        leadingIcon = Icons.Default.LunchDining,
        leadingIconDescription = FoodComponentType.CALORIES.toString(),
    ),
    FoodComponent(
        type = FoodComponentType.CARBS,
        value = "",
    ),
    FoodComponent(
        type = FoodComponentType.PROTEIN,
        value = "",
    ),
    FoodComponent(
        type = FoodComponentType.FAT,
        value = "",
    ),
    FoodComponent(
        type = FoodComponentType.SATURATED_FAT,
        value = "",
    ),
    FoodComponent(
        type = FoodComponentType.FIBER,
        value = "",
    ),
    FoodComponent(
        type = FoodComponentType.SUGAR,
        value = "",
    ),
)