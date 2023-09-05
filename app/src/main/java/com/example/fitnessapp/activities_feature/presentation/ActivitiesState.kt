package com.example.fitnessapp.activities_feature.presentation

import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.IntensityItem
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity

data class ActivitiesState(
    val intensityLevels: List<IntensityItem> = emptyList(),
    val savedActivities: List<SavedActivity> = emptyList(),
    val isBurnedCaloriesDialogVisible: Boolean = false,
    val minutes: String = "",
    val seconds: String = "",
    val filterQuery: String = "",
    val areActivitiesFiltered: Boolean = false,
    val chosenActivity: Activity? = null
)
