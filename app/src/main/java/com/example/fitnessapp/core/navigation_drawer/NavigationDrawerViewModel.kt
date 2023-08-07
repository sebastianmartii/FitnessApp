package com.example.fitnessapp.core.navigation_drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class NavigationDrawerViewModel : ViewModel() {

    val selectedDrawerItem = MutableStateFlow<DrawerItem?>(null)

    private val channel = Channel<DrawerAction>()
    val drawerEventFlow = channel.receiveAsFlow()
    fun onDrawerEvent(drawerEvent: DrawerEvent) {
        when(drawerEvent) {
            DrawerEvent.CloseDrawer -> {
                viewModelScope.launch {
                    channel.send(DrawerAction.CloseNavigationDrawer)
                }
            }
            is DrawerEvent.OnDrawerItemSelect -> {
                selectedDrawerItem.value = drawerEvent.selectedDrawerItem
            }
            DrawerEvent.OpenDrawer -> {
                viewModelScope.launch {
                    channel.send(DrawerAction.OpenNavigationDrawer)
                }
            }
        }
    }
}