package com.example.fitnessapp.activities_feature.presentation

import com.example.fitnessapp.activities_feature.domain.model.IntensityItem
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity

data class ActivitiesState(
    val intensityLevels: List<IntensityItem> = emptyList(),
    val savedActivities: List<SavedActivity> = emptyList(),
    val currentSelectedActivitiesTabRowItem: ActivitiesTabRowItem = ActivitiesTabRowItem.SAVED,
    val selectedTabIndex: Int = 0,
    val isBurnedCaloriesDialogVisible: Boolean = false,
    val minutes: String = "",
    val seconds: String = "",
    val filterQuery: String = "",
    val areActivitiesFiltered: Boolean = false,
    val chosenActivityID: String = ""
)
