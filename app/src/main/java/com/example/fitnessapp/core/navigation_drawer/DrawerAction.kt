package com.example.fitnessapp.core.navigation_drawer

sealed class DrawerAction {
    object OpenNavigationDrawer : DrawerAction()
    object CloseNavigationDrawer : DrawerAction()
    data class NavigateToDrawerDestination(val route: String) : DrawerAction()
}