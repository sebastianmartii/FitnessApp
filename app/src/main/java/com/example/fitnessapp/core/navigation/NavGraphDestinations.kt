package com.example.fitnessapp.core.navigation

sealed class NavGraphDestinations(val route: String) {

    object MainNavGraph : NavGraphDestinations(route = "main_nav_graph")
    object SignInNavGraph : NavGraphDestinations(route = "sign_in_nav_graph")
    object StartDestination : NavGraphDestinations(route = "start_destination")
}