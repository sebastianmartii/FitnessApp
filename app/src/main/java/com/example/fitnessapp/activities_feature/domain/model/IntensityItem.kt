package com.example.fitnessapp.activities_feature.domain.model

import com.example.fitnessapp.activities_feature.presentation.IntensityLevel

data class IntensityItem(
    val intensityLevel: IntensityLevel,
    val isExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val activities: List<Activity> = emptyList()
)
