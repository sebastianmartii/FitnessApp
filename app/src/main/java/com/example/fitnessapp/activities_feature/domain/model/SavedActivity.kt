package com.example.fitnessapp.activities_feature.domain.model

data class SavedActivity(
    val name: String,
    val description: String?,
    val burnedCalories: String,
    val duration: Double,
)
