package com.example.fitnessapp.daily_overview_feature.presentation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.daily_overview_feature.domain.model.MealDetails

data class OverviewState(
    val caloriesGoal: Int = 0,
    val currentCaloriesCount: Int = 0,
    val progress: Dp = 0.dp,
    val mealPlan: List<String> = emptyList(),
    val mealDetails: List<MealDetails> = emptyList()
)
