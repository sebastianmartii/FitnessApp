package com.example.fitnessapp.core.navigation

sealed class NavigationDrawerDestinations(val route: String) {

    object History : NavigationDrawerDestinations("history")
    object Profile : NavigationDrawerDestinations("profile")
}
