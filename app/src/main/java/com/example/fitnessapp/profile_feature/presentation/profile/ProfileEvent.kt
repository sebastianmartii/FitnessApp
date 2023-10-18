package com.example.fitnessapp.profile_feature.presentation.profile

import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel

sealed interface ProfileEvent {
    data class OnUserNameChange(val newUserName: String) : ProfileEvent
    data class OnAgeChange(val newAge: String) : ProfileEvent
    data class OnWeightChange(val newWeight: String) : ProfileEvent
    data class OnHeightChange(val newHeight: String) : ProfileEvent
    data class OnGenderChange(val newGender: Gender) : ProfileEvent
    data class OnActivityLevelChange(val newActivityLevel: ActivityLevel) : ProfileEvent
    data class OnCaloriesGoalChange(val newCaloriesGoal: String) : ProfileEvent
    object OnCaloriesGoalCalculate : ProfileEvent
    data class OnCaloriesGoalSelect(val selectedCaloriesGoal: String) : ProfileEvent
}