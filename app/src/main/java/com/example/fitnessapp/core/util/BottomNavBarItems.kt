package com.example.fitnessapp.core.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Feed
import androidx.compose.material.icons.automirrored.outlined.Feed
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material.icons.outlined.Timer
import com.example.fitnessapp.core.navigation.BottomNavBarDestinations
import com.example.fitnessapp.core.navigation.NavigationBarItem


val bottomNavBarItems = listOf(
    NavigationBarItem(
        title = "Overview",
        route = BottomNavBarDestinations.Overview.route,
        selectedIcon = Icons.AutoMirrored.Filled.Feed,
        unselectedIcon = Icons.AutoMirrored.Outlined.Feed
    ),
    NavigationBarItem(
        title = "Nutrition",
        route = BottomNavBarDestinations.Nutrition.route,
        selectedIcon = Icons.Filled.FoodBank,
        unselectedIcon = Icons.Outlined.FoodBank
    ),
    NavigationBarItem(
        title = "Activities",
        route = BottomNavBarDestinations.Activities.route,
        selectedIcon = Icons.Filled.Timer,
        unselectedIcon = Icons.Outlined.Timer
    )
)