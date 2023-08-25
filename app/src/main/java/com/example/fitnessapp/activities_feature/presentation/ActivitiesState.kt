package com.example.fitnessapp.activities_feature.presentation

import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.IntensityItem

data class ActivitiesState(
    val intensityLevels: List<IntensityItem> = emptyList(),
)
