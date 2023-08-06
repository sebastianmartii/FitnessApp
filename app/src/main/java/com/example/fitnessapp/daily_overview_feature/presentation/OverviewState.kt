package com.example.fitnessapp.daily_overview_feature.presentation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.core.navigation_drawer.DrawerItem

data class OverviewState(
    val selectedDrawerItem: DrawerItem? = null,
    val caloriesGoal: Int = 0,
    val currentCaloriesCount: Int = 0,
    val progress: Dp = 0.dp
)
