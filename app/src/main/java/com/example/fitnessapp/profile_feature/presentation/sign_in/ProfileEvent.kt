package com.example.fitnessapp.profile_feature.presentation.sign_in

import com.example.fitnessapp.profile_feature.domain.model.Gender

sealed interface ProfileEvent {

    data class OnNameChange(val name: String) : ProfileEvent
    data class OnGenderChange(val gender: Gender) : ProfileEvent
    data class OnAgeChange(val age: String) : ProfileEvent
    data class OnHeightChange(val height: String) : ProfileEvent
    data class OnWeightChange(val weight: String) : ProfileEvent
    data class OnCaloriesGoalChange(val caloriesGoal: String) : ProfileEvent
    data class OnActivityLevelChange(val activityLevel: ActivityLevel) : ProfileEvent
    data class OnProfileChosen(val userID: Int) : ProfileEvent
    object OnSignInComplete : ProfileEvent
    object OnProfileSelect : ProfileEvent
}
