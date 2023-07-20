package com.example.fitnessapp.profile_feature.presentation.sign_in

sealed interface SignInProgress {
    object Introduction : SignInProgress
    data class Measurements(val name: String) : SignInProgress
    data class ActivityLevelAndCaloriesGoal(val name: String) : SignInProgress
    object ProfileList : SignInProgress
}