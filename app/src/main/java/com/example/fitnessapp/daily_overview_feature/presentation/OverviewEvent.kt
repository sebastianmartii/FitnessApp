package com.example.fitnessapp.daily_overview_feature.presentation

sealed interface OverviewEvent {
    object OnAddMeal : OverviewEvent
}