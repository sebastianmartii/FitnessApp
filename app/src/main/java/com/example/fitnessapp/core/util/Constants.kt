package com.example.fitnessapp.core.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import com.example.fitnessapp.core.navigation_drawer.DrawerItem

val drawerItemList = listOf(
    DrawerItem(label = "Profile", icon = Icons.Default.Person),
    DrawerItem(label = "Calendar", icon = Icons.Default.CalendarMonth),
    DrawerItem(label = "Sign Out", icon = Icons.Default.Logout),
)