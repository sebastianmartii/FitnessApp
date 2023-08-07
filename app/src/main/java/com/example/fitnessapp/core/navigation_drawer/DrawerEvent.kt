package com.example.fitnessapp.core.navigation_drawer

sealed interface DrawerEvent {

    object OpenDrawer : DrawerEvent
    object CloseDrawer : DrawerEvent
    data class OnDrawerItemSelect(val selectedDrawerItem: DrawerItem) : DrawerEvent
}