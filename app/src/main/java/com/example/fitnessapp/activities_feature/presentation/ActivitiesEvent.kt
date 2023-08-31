package com.example.fitnessapp.activities_feature.presentation

sealed interface ActivitiesEvent {

    data class OnIntensityLevelActivitiesFetch(val intensityLevel: IntensityLevel, val intensityLevelIndex: Int) : ActivitiesEvent
    data class OnIntensityLevelExpandedChange(val isExpanded: Boolean, val intensityLevelIndex: Int) : ActivitiesEvent
    data class OnActivityClick(val activityID: String) : ActivitiesEvent
    object OnBurnedCaloriesDialogDismiss : ActivitiesEvent
    data class OnActivitiesTabChange(val activitiesTabRowItem: ActivitiesTabRowItem, val tabIndex: Int) : ActivitiesEvent
    data class OnBurnedCaloriesDialogConfirm(val activityID: String, val duration: Double) : ActivitiesEvent
    data class OnMinutesChange(val minutes: String) : ActivitiesEvent
    data class OnSecondsChange(val seconds: String) : ActivitiesEvent
    data class OnFilterQueryChange(val query: String) : ActivitiesEvent
    data class OnFilterActivities(val areActivitiesFiltered: Boolean) : ActivitiesEvent
    object OnFilterQueryClear : ActivitiesEvent
}