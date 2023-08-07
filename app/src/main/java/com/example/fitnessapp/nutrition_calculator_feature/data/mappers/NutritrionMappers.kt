package com.example.fitnessapp.nutrition_calculator_feature.data.mappers

import com.example.fitnessapp.nutrition_calculator_feature.presentation.TabRowItem

fun TabRowItem.toTabTitle(): String {
    return when(this) {
        TabRowItem.NUTRITION_CALCULATOR -> "Nutrition Calculator"
        TabRowItem.RECIPES -> "Recipes"
        TabRowItem.MEAL_PLAN -> "Meal Plan"
    }
}