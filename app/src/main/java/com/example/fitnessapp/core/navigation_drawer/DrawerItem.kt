package com.example.fitnessapp.core.navigation_drawer

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    val label: String,
    val icon: ImageVector,
    val route: String? = null,
    val action: suspend () -> Unit = {}
)