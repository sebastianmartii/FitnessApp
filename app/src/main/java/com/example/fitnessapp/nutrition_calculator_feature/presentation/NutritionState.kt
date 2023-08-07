package com.example.fitnessapp.nutrition_calculator_feature.presentation

data class NutritionState(
    val selectedTabIndex: Int = 0,
    val currentTabRowItem: TabRowItem = TabRowItem.NUTRITION_CALCULATOR,
)
