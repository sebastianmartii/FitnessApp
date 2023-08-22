package com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan

import androidx.compose.ui.graphics.vector.ImageVector

data class MealPlan(
    val name: String = "",
    val type: MealPlanType = MealPlanType.CUSTOM,
    val leadingIcon: ImageVector? = null,
    val selectedLeadingIcon: ImageVector? = null,
    val isExpanded: Boolean = false,
    val editedMealNameIndex: Int? = null,
    val meals: MutableList<String> = mutableListOf()
)
