package com.example.fitnessapp.profile_feature.presentation.profile

import com.example.fitnessapp.profile_feature.domain.model.CalculatedCalories
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel

data class ProfileState(
    val userName: String = "",
    val caloriesGoal: String = "",
    val age: String = "",
    val gender: Gender = Gender.NONE,
    val weight: String = "",
    val height: String = "",
    val activityLevel: ActivityLevel = ActivityLevel.LEVEL_0,
    val genderExpanded: Boolean = false,
    val calculatedCalories: List<CalculatedCalories> = emptyList(),
    val activityLevelExpanded: Boolean = false,
    val isSaveUserActionVisible: Boolean = false,
    val isUserUpdateDialogVisible: Boolean = false,
    val isAgeValid: Boolean = true,
    val isHeightValid: Boolean = true,
    val isWeightValid: Boolean = true,
    val isCaloriesGoalValid: Boolean = true,
    val shouldUseValidators: Boolean = false
)
