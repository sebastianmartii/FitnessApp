package com.example.fitnessapp.core.navigation

sealed class SignInDestinations(val route: String) {

    object Introduction : SignInDestinations(route = "introduction")
    object Measurements : SignInDestinations(route = "measurements")
    object ActivityLevelAndCaloriesGoal : SignInDestinations(route = "activity_level_and_caloriesGoal")
    object ProfileList : SignInDestinations(route = "profile_list")
    object CalculatedCalories : SignInDestinations(route = "calculated_calories")
}
