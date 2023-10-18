package com.example.fitnessapp.profile_feature.presentation.sign_in

import com.example.fitnessapp.profile_feature.domain.model.Gender

sealed interface SignInEvent {

    data class OnNameChange(val name: String) : SignInEvent
    data class OnGenderChange(val gender: Gender) : SignInEvent
    data class OnAgeChange(val age: String) : SignInEvent
    data class OnHeightChange(val height: String) : SignInEvent
    data class OnWeightChange(val weight: String) : SignInEvent
    data class OnCaloriesGoalChange(val caloriesGoal: String) : SignInEvent
    data class OnActivityLevelChange(val activityLevel: ActivityLevel) : SignInEvent
    data class OnSignInChosen(val userID: Int) : SignInEvent
    object OnSignInComplete : SignInEvent
    object OnSignInSelect : SignInEvent
}
