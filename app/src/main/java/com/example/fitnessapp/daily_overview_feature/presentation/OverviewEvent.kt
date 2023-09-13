package com.example.fitnessapp.daily_overview_feature.presentation

sealed interface OverviewEvent {
    data class OnMealReset(val mealIndex: Int): OverviewEvent
    data class OnMealDetailsExpand(val meal: String) : OverviewEvent
}