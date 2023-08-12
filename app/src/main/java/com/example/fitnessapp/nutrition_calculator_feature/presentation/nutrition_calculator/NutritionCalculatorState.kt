package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator

import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem

data class NutritionCalculatorState(
    val query: String = "",
    val isSearchBarActive: Boolean = false,
    val isCalculationLoading: Boolean = false,
    val cachedProducts: List<FoodItem> = emptyList(),
    val calculatedProducts: List<FoodItem> = emptyList(),
    val selectedProducts: MutableList<FoodItem> = mutableListOf(),
    val expandedFoodItems: MutableList<FoodItem> = mutableListOf()
)
