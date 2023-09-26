package com.example.fitnessapp.history_feature.domain.model

data class Activity(
    val name: String = "",
    val burnedCalories: Double = 0.0,
    val performedActivitiesDuration: Double = 0.0,
    val day: Int
)
