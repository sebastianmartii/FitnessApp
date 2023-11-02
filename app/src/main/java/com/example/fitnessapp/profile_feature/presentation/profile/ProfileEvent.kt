package com.example.fitnessapp.profile_feature.presentation.profile

import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel

sealed interface ProfileEvent {
    data class OnUserNameChange(val newUserName: String) : ProfileEvent
    data class OnAgeChange(val newAge: String, val isAgeValid: Boolean) : ProfileEvent
    data class OnWeightChange(val newWeight: String, val isWeightValid: Boolean) : ProfileEvent
    data class OnHeightChange(val newHeight: String, val isHeightValid: Boolean) : ProfileEvent
    data class OnGenderChange(val newGender: Gender) : ProfileEvent
    data class OnActivityLevelChange(val newActivityLevel: ActivityLevel) : ProfileEvent
    data class OnCaloriesGoalChange(val newCaloriesGoal: String, val isCaloriesGoalValid: Boolean) : ProfileEvent
    object OnCaloriesGoalCalculate : ProfileEvent
    data class OnGenderExpandedChange(val expanded: Boolean) : ProfileEvent
    data class OnActivityLevelExpandedChange(val expanded: Boolean) : ProfileEvent
    object OnUserUpdateDialogDismiss : ProfileEvent
    object OnUserUpdateDialogDecline : ProfileEvent
    data class OnUserUpdateDialogShow(val pendingNavigationRoute: String) : ProfileEvent
    data class OnUserUpdate(
        val state: ProfileState,
        val isAgeValid: Boolean,
        val isHeightValid: Boolean,
        val isWeightValid: Boolean,
        val isCaloriesGoalValid: Boolean,
    ) : ProfileEvent
}