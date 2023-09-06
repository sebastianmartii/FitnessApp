package com.example.fitnessapp.activities_feature.presentation

import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity

sealed interface ActivitiesEvent {

    data class OnIntensityLevelActivitiesFetch(val intensityLevel: IntensityLevel, val intensityLevelIndex: Int) : ActivitiesEvent
    data class OnIntensityLevelExpandedChange(val isExpanded: Boolean, val intensityLevelIndex: Int) : ActivitiesEvent
    data class OnActivityClick(val activity: Activity) : ActivitiesEvent
    data class OnSavedActivityClick(val savedActivityIndex: Int, val isSelected: Boolean) : ActivitiesEvent
    object OnBurnedCaloriesDialogDismiss : ActivitiesEvent
    data class OnActivitiesTabChange(val index: Int) : ActivitiesEvent
    data class OnBurnedCaloriesDialogConfirm(val activity: Activity, val duration: Double) : ActivitiesEvent
    data class OnMinutesChange(val minutes: String) : ActivitiesEvent
    data class OnSecondsChange(val seconds: String) : ActivitiesEvent
    data class OnFilterQueryChange(val query: String) : ActivitiesEvent
    data class OnFilterActivities(val areActivitiesFiltered: Boolean) : ActivitiesEvent
    data class OnSavedActivitiesPerform(val performedActivities: List<SavedActivity>) : ActivitiesEvent
    data class OnSavedActivitiesDelete(val deletedActivities: List<SavedActivity>) : ActivitiesEvent
    object OnFilterQueryClear : ActivitiesEvent
}