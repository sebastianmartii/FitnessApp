package com.example.fitnessapp.profile_feature.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.profile_feature.presentation.ProfileItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CurrentUserProfileScreen(
    state: ProfileState,
    drawerState: DrawerState,
    drawerItemList: List<DrawerItem>,
    selectedDrawerItem: DrawerItem?,
    drawerEventFlow: Flow<DrawerAction>,
    onDrawerEvent: (DrawerEvent) -> Unit,
    onEvent: (ProfileEvent) -> Unit,
    onNavigateToNavigationDrawerDestination: (String) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        drawerEventFlow.collectLatest {  action ->
            when(action) {
                DrawerAction.CloseNavigationDrawer -> {
                    drawerState.close()
                }
                DrawerAction.OpenNavigationDrawer -> {
                    drawerState.open()
                }
                is DrawerAction.NavigateToDrawerDestination -> {
                    onNavigateToNavigationDrawerDestination(action.route)
                }
            }
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                selectedDrawerItem = selectedDrawerItem,
                drawerItemList = drawerItemList,
                onDrawerItemSelect = { selectedDrawerItem ->
                    onDrawerEvent(DrawerEvent.OnDrawerItemSelect(selectedDrawerItem))
                    onDrawerEvent(DrawerEvent.CloseDrawer)
                }
            )
        },
        content = {
            CurrentUserProfileScreenContent(
                state = state,
                onDrawerStateChange = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                }
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CurrentUserProfileScreenContent(
    state: ProfileState,
    onDrawerStateChange: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.profile_screen_title, state.userName))
                },
                navigationIcon = {
                    IconButton(onClick = onDrawerStateChange) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.navigation_drawer_icon)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ProfileItem(
                name = state.userName,
                gender = state.gender
            )
            FlowRow(

                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {

            }
        }   
    }
}