package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator

import androidx.compose.ui.graphics.vector.ImageVector

data class FoodComponent(
    val type: FoodComponentType,
    var value: String,
    val leadingIcon: ImageVector? = null,
    val leadingIconDescription: String? = null,
    val trailingTexT: String? = null
)
