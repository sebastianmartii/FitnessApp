package com.example.fitnessapp.profile_feature.presentation.sign_in

import com.example.fitnessapp.profile_feature.domain.model.Gender

data class SignInState(
    val signInProgress: SignInProgress = SignInProgress.Introduction,
    val name: String = "",
    val gender: Gender = Gender.NONE,
    val age: Int = 0,
    val height: Float = 0.0f,
    val weight: Float = 0.0f,
    val activityLevel: ActivityLevel = ActivityLevel.LEVEL_1,
    val caloriesGoal: Int = 0
)