package com.example.fitnessapp.core.navigation

sealed class BottomNavBarDestinations(val route: String) {

    object Overview : BottomNavBarDestinations("overview")
    object Nutrition : BottomNavBarDestinations("nutrition")
    object Activities : BottomNavBarDestinations("activities")
}
