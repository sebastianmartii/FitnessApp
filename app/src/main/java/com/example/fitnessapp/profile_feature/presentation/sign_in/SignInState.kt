package com.example.fitnessapp.profile_feature.presentation.sign_in

import com.example.fitnessapp.profile_feature.domain.model.CalculatedCalories
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile

data class SignInState(
    val name: String = "",
    val gender: Gender = Gender.NONE,
    val age: String = "",
    val height: String = "",
    val weight: String = "",
    val activityLevel: ActivityLevel = ActivityLevel.LEVEL_0,
    val caloriesGoal: String = "",
    val calculatedCalories: List<CalculatedCalories> = emptyList(),
    val profileList: List<UserProfile> = emptyList(),
)