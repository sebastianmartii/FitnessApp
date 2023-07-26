package com.example.fitnessapp.profile_feature.presentation.sign_in

import com.example.fitnessapp.profile_feature.domain.model.Gender

sealed interface ProfileEvent {

    data class OnNameChange(val name: String) : ProfileEvent
    data class OnGenderChange(val gender: Gender) : ProfileEvent
    data class OnAgeChange(val age: String) : ProfileEvent
    data class OnHeightChange(val height: String) : ProfileEvent
    data class OnWeightChange(val weight: String) : ProfileEvent
    data class OnCaloriesGoalChange(val caloriesGoal: Int) : ProfileEvent
    data class OnActivityLevelChange(val activityLevel: ActivityLevel) : ProfileEvent
    object OnIntroductionDone : ProfileEvent
    object OnProfileSelect : ProfileEvent
    object OnMeasurementsTaken : ProfileEvent
    object OnSignInComplete : ProfileEvent
    object OnCalculateCalories : ProfileEvent
    data class OnGenderMenuExpandedChange(val expanded: Boolean) : ProfileEvent
    data class OnActivityLevelMenuExpandedChange(val expanded: Boolean) : ProfileEvent
    data class OnGoBack(val currentProgress: SignInProgress) : ProfileEvent
}
