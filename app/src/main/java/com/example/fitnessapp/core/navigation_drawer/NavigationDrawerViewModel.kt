package com.example.fitnessapp.core.navigation_drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Feed
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.navigation.NavGraphDestinations
import com.example.fitnessapp.core.navigation.NavigationDrawerDestinations
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class NavigationDrawerViewModel(
    private val currentUserDao: CurrentUserDao
) : ViewModel() {

    val drawerItemList = listOf(
        DrawerItem(
            label = "Overview",
            icon = Icons.AutoMirrored.Filled.Feed,
            route = NavGraphDestinations.MainNavGraph.route
        ),
        DrawerItem(
            label = "Profile",
            icon = Icons.Default.Person,
            route = NavigationDrawerDestinations.Profile.route
        ),
        DrawerItem(
            label = "History",
            icon = Icons.Default.CalendarMonth,
            route = NavigationDrawerDestinations.History.route
        ),
        DrawerItem(
            label = "Sign Out",
            icon = Icons.AutoMirrored.Default.Logout,
            route = NavGraphDestinations.SignInNavGraph.route,
            action = {
                _selectedDrawerItem.value = null
                currentUserDao.signOut()
            }
        )
    )

    private val _selectedDrawerItem = MutableStateFlow<DrawerItem?>(null)
    val selectedDrawerItem = _selectedDrawerItem.asStateFlow()

    private val _drawerEventChannel = Channel<DrawerAction>()
    val drawerEventFlow = _drawerEventChannel.receiveAsFlow()

    fun onDrawerEvent(drawerEvent: DrawerEvent) {
        when(drawerEvent) {
            DrawerEvent.CloseDrawer -> {
                viewModelScope.launch {
                    _drawerEventChannel.send(DrawerAction.CloseNavigationDrawer)
                }
            }
            is DrawerEvent.OnDrawerItemSelect -> {
                viewModelScope.launch {
                    if (drawerEvent.selectedDrawerItem.route != null) {
                        _drawerEventChannel.send(
                            DrawerAction.NavigateToDrawerDestination(
                                drawerEvent.selectedDrawerItem.route
                            )
                        )
                    }
                    drawerEvent.selectedDrawerItem.action()
                }
                _selectedDrawerItem.value = drawerEvent.selectedDrawerItem
            }
            DrawerEvent.OpenDrawer -> {
                viewModelScope.launch {
                    _drawerEventChannel.send(DrawerAction.OpenNavigationDrawer)
                }
            }
        }
    }
}