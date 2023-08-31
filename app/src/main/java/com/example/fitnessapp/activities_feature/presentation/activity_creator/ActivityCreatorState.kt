package com.example.fitnessapp.activities_feature.presentation.activity_creator

data class ActivityCreatorState(
    val name: String = "",
    val description: String = "",
    val minutes: String = "",
    val seconds: String = "",
    val burnedCalories: String = "",
)
