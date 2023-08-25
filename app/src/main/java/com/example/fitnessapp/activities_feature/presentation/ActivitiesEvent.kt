package com.example.fitnessapp.activities_feature.presentation

sealed interface ActivitiesEvent {

    data class OnIntensityLevelExpandedChange(val isExpanded: Boolean, val intensityLevelIndex: Int) : ActivitiesEvent
}