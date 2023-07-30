package com.example.fitnessapp.profile_feature.presentation.sign_in

import com.example.fitnessapp.profile_feature.domain.model.CalculatedCalories
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile

data class SignInState(
    val signInProgress: SignInProgress = SignInProgress.Introduction,
    val name: String = "",
    val gender: Gender = Gender.NONE,
    val age: String = "",
    val height: String = "",
    val weight: String = "",
    val activityLevel: ActivityLevel = ActivityLevel.LEVEL_1,
    val caloriesGoal: Double = 0.0,
    val calculatedCalories: List<CalculatedCalories> = emptyList(),
    val profileList: List<UserProfile> = emptyList(),
    val genderMenuExpanded: Boolean = false,
    val activityLevelMenuExpanded: Boolean = false,
)