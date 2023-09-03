package com.example.fitnessapp.activities_feature.presentation.activity_creator

sealed interface ActivityCreatorEvent {

    data class OnActivityNameChange(val name: String) : ActivityCreatorEvent
    data class OnActivityDescriptionChange(val description: String) : ActivityCreatorEvent
    data class OnActivityMinutesChange(val minutes: String) : ActivityCreatorEvent
    data class OnActivitySecondsChange(val seconds: String) : ActivityCreatorEvent
    data class OnActivityBurnedCaloriesChange(val burnedCalories: String) : ActivityCreatorEvent
    data class OnActivitySave(
        val name: String,
        val description: String,
        val duration: Double,
        val burnedCalories: String,
        val isActivityValid: Boolean
    ) : ActivityCreatorEvent
}