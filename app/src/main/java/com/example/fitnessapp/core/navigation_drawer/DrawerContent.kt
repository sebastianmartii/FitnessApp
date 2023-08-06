package com.example.fitnessapp.core.navigation_drawer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    selectedDrawerItem: DrawerItem?,
    drawerItemList: List<DrawerItem>,
    onDrawerItemSelect: (DrawerItem) -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        drawerItemList.onEach { drawerItem ->
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = drawerItem.icon,
                        contentDescription = drawerItem.label
                    )
                },
                label = {
                    Text(text = drawerItem.label)
                },
                selected = drawerItem == selectedDrawerItem,
                onClick = {
                    onDrawerItemSelect(drawerItem)
                },
                modifier = Modifier
                    .padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}