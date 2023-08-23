package com.example.fitnessapp.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationBarItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)