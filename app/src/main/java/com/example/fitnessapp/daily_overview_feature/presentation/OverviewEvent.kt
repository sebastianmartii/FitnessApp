package com.example.fitnessapp.daily_overview_feature.presentation

import com.example.fitnessapp.core.navigation_drawer.DrawerItem

sealed interface OverviewEvent {
    object OpenDrawer : OverviewEvent
    object CloseDrawer : OverviewEvent
    data class OnDrawerItemSelect(val selectedDrawerItem: DrawerItem) : OverviewEvent
    object AddMeal : OverviewEvent
}