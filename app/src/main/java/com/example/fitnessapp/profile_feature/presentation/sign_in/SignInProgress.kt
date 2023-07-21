package com.example.fitnessapp.profile_feature.presentation.sign_in

sealed interface SignInProgress {
    object Introduction : SignInProgress
    object Measurements : SignInProgress
    object ActivityLevelAndCaloriesGoal : SignInProgress
    object ProfileList : SignInProgress
    object CaloriesGoalList : SignInProgress
}