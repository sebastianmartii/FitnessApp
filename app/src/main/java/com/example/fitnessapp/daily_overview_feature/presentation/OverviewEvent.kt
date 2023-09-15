package com.example.fitnessapp.daily_overview_feature.presentation

import com.example.fitnessapp.daily_overview_feature.domain.model.Activity

sealed interface OverviewEvent {
    data class OnMealReset(val meal: String): OverviewEvent
    data class OnMealDetailsExpand(val meal: String, val areDetailsEmpty: Boolean) : OverviewEvent
    data class OnActivityDelete(val activity: Activity) : OverviewEvent
}