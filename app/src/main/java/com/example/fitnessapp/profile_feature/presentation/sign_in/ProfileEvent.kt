package com.example.fitnessapp.profile_feature.presentation.sign_in

import com.example.fitnessapp.profile_feature.domain.model.Gender

sealed interface ProfileEvent {

    data class IntroductionDone(val name: String, val gender: Gender) : ProfileEvent

    data class MeasurementsProvided(val age: Int, val height: Float, val weight: Float) : ProfileEvent

    data class CaloriesGoalChosen(val caloriesGoal: Int) : ProfileEvent

    data class SignInCompleted(val activityLevel: ActivityLevel, val caloriesGoal: Int) : ProfileEvent

    object OnCalculateCalories : ProfileEvent

    object OnChooseProfile : ProfileEvent

    data class ProfileChosen(val profileName: String) : ProfileEvent

    data class OnGoBack(val currentProgress: SignInProgress) : ProfileEvent
}
