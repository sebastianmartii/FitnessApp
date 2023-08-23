package com.example.fitnessapp.core.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavBar(
    items: List<NavigationBarItem>,
    selectedItemIndex: Int,
    onNavigate: (NavigationBarItem) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        items.onEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    onNavigate(item)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == index) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}