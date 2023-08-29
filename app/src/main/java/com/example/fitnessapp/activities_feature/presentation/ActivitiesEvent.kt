package com.example.fitnessapp.activities_feature.presentation

sealed interface ActivitiesEvent {

    data class OnIntensityLevelActivitiesFetch(val intensityLevel: IntensityLevel, val intensityLevelIndex: Int) : ActivitiesEvent
    data class OnIntensityLevelExpandedChange(val isExpanded: Boolean, val intensityLevelIndex: Int) : ActivitiesEvent
    data class OnActivityClick(val activityID: String) : ActivitiesEvent
    object OnBurnedCaloriesDialogDismiss : ActivitiesEvent
    data class OnActivitiesTabChange(val activitiesTabRowItem: ActivitiesTabRowItem, val tabIndex: Int) : ActivitiesEvent
    data class OnBurnedCaloriesDialogConfirm(val minutes: String, val seconds: String) : ActivitiesEvent
    data class OnMinutesChange(val minutes: String) : ActivitiesEvent
    data class OnSecondsChange(val seconds: String) : ActivitiesEvent
}