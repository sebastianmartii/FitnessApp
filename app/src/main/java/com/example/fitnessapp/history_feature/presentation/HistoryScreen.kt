package com.example.fitnessapp.history_feature.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DatePickerState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitnessapp.R
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.history_feature.data.mappers.mapMealToMealDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    state: HistoryState,
    datePickerState: DatePickerState,
    drawerState: DrawerState,
    drawerItemList: List<DrawerItem>,
    selectedDrawerItem: DrawerItem?,
    drawerEventFlow: Flow<DrawerAction>,
    onDrawerEvent: (DrawerEvent) -> Unit,
    onEvent: (HistoryEvent) -> Unit,
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
            HistoryScreenContent(
                state = state,
                datePickerState = datePickerState,
                onHistoryEvent = onEvent,
                onDrawerStateChange = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                }
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreenContent(
    state: HistoryState,
    datePickerState: DatePickerState,
    onHistoryEvent: (HistoryEvent) -> Unit,
    onDrawerStateChange: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.selectedDateString)
                },
                navigationIcon = {
                    IconButton(onClick = onDrawerStateChange) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.navigation_drawer_icon)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onHistoryEvent(HistoryEvent.OnDatePickerDialogShow)
                    }) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        HistoryDetailsScreen(
            performedActivities = state.activities.filter { it.day == state.selectedDay },
            nutrition = mapMealToMealDetails(state.nutrition, state.selectedDay),
            modifier = Modifier
                .padding(paddingValues)
        )
    }
    AnimatedVisibility(
        visible = state.isDatePickerDialogVisible,
        enter = slideInVertically(
            animationSpec = tween(
                durationMillis = 350
            )
        ) + fadeIn(),
        exit = slideOutVertically(
            animationSpec = tween(
                durationMillis = 350
            )
        ) + fadeOut()

    ) {
        DatePickerFullScreenDialog(
            datePickerState = datePickerState,
            onDialogDismiss = {
                onHistoryEvent(HistoryEvent.OnDatePickerDialogDismiss)
            },
            onDialogConfirm = { selectedDateMillis ->
                onHistoryEvent(HistoryEvent.OnDatePickerDialogDateConfirm(selectedDateMillis))
            }
        )
    }
}