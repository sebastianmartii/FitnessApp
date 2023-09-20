package com.example.fitnessapp.activities_feature.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.fitnessapp.R
import com.example.fitnessapp.activities_feature.data.mappers.toTabTitle
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.IntensityItem
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity
import com.example.fitnessapp.core.navigation.BottomNavBar
import com.example.fitnessapp.core.navigation.NavigationBarItem
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.core.util.drawerItemList
import com.example.fitnessapp.core.util.duration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ActivitiesScreen(
    state: ActivitiesState,
    activitiesTabRowItems: List<ActivitiesTabRowItem>,
    bottomNavBarItems: List<NavigationBarItem>,
    drawerState: DrawerState,
    pagerState: PagerState,
    selectedDrawerItem: DrawerItem?,
    drawerEventFlow: Flow<DrawerAction>,
    pagerFlow: Flow<Int>,
    onDrawerEvent: (DrawerEvent) -> Unit,
    onEvent: (ActivitiesEvent) -> Unit,
    onBottomBarNavigate: (NavigationBarItem) -> Unit,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit,
    onNavigateToAddActivityScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = true) {
        drawerEventFlow.collectLatest { action ->
            when(action) {
                DrawerAction.CloseNavigationDrawer -> {
                    drawerState.close()
                }
                DrawerAction.OpenNavigationDrawer -> {
                    drawerState.open()
                }
            }
        }
    }
    LaunchedEffect(key1 = true) {
        pagerFlow.collectLatest { page ->
            pagerState.animateScrollToPage(page)
        }
    }
    if (state.isBurnedCaloriesDialogVisible && pagerState.currentPage == 1) {
        BurnedCaloriesDialog(
            minutes = state.minutes,
            seconds = state.seconds,
            isConfirmButtonEnabled = isBurnedCaloriesConfirmEnabled(state.minutes, state.seconds),
            onMinutesChange = { minutes ->
                onEvent(ActivitiesEvent.OnMinutesChange(minutes))
            },
            onSecondsChange = { seconds ->
                onEvent(ActivitiesEvent.OnSecondsChange(seconds))
            },
            onBurnedCaloriesDialogDismiss = {
                onEvent(ActivitiesEvent.OnBurnedCaloriesDialogDismiss)
            },
            onConfirm = { minutes, seconds ->
                onEvent(ActivitiesEvent.OnBurnedCaloriesDialogConfirm(
                    state.chosenActivity!!,
                    duration(minutes, seconds),
                ))
            },
            onFocusMove = onFocusMove,
            onKeyboardHide = onKeyboardHide)
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
            ActivitiesContent(
                savedActivities = state.savedActivities,
                intensityLevels = state.intensityLevels,
                pagerState = pagerState,
                areActivitiesFiltered = state.areActivitiesFiltered,
                isSavedActivitiesFABVisible = state.isSavedActivitiesFABVisible,
                filterQuery = state.filterQuery,
                activitiesTabRowItems = activitiesTabRowItems,
                bottomNavBarItems = bottomNavBarItems,
                onDrawerStateChange = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                },
                onTabChange = { tabIndex ->
                    onEvent(ActivitiesEvent.OnActivitiesTabChange(tabIndex))
                },
                onIntensityLevelExpandedChange = { isExpanded, index ->
                    onEvent(ActivitiesEvent.OnIntensityLevelExpandedChange(isExpanded, index))
                },
                onIntensityLevelActivitiesFetch = { level, index ->
                    onEvent(ActivitiesEvent.OnIntensityLevelActivitiesFetch(level, index))
                },
                onBottomBarNavigate = onBottomBarNavigate,
                onActivityClick = { activity ->
                    onEvent(ActivitiesEvent.OnActivityClick(activity))
                },
                onSavedActivityClick = { savedActivityIndex, isSavedActivitySelected ->
                    onEvent(ActivitiesEvent.OnSavedActivityClick(savedActivityIndex, isSavedActivitySelected))
                },
                onNavigateToAddActivityScreen = onNavigateToAddActivityScreen,
                onFilterActivities = { areFiltered ->
                    onEvent(ActivitiesEvent.OnFilterActivities(areFiltered))
                },
                onFilterQueryChange = { filterQuery ->
                    onEvent(ActivitiesEvent.OnFilterQueryChange(filterQuery))
                },
                onFilterQueryClear = {
                    onEvent(ActivitiesEvent.OnFilterQueryClear)
                },
                onSavedActivitiesDelete = { deletedActivities ->
                    onEvent(ActivitiesEvent.OnSavedActivitiesDelete(deletedActivities))
                },
                onSavedActivitiesPerform = { performedActivities ->
                    onEvent(ActivitiesEvent.OnSavedActivitiesPerform(performedActivities))
                },
                onKeyboardHide = onKeyboardHide,
            )
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ActivitiesContent(
    savedActivities: List<SavedActivity>,
    intensityLevels: List<IntensityItem>,
    bottomNavBarItems: List<NavigationBarItem>,
    pagerState: PagerState,
    filterQuery: String,
    areActivitiesFiltered: Boolean,
    isSavedActivitiesFABVisible: Boolean,
    activitiesTabRowItems: List<ActivitiesTabRowItem>,
    onDrawerStateChange: () -> Unit,
    onTabChange: (tabIndex: Int) -> Unit,
    onIntensityLevelExpandedChange: (isExpanded: Boolean, index: Int) -> Unit,
    onIntensityLevelActivitiesFetch: (intensityLevel: IntensityLevel, index: Int) -> Unit,
    onBottomBarNavigate: (NavigationBarItem) -> Unit,
    onActivityClick: (Activity) -> Unit,
    onSavedActivityClick: (index: Int, isSelected: Boolean) -> Unit,
    onNavigateToAddActivityScreen: () -> Unit,
    onFilterActivities: (Boolean) -> Unit,
    onFilterQueryChange: (String) -> Unit,
    onFilterQueryClear: () -> Unit,
    onSavedActivitiesPerform: (List<SavedActivity>) -> Unit,
    onSavedActivitiesDelete: (List<SavedActivity>) -> Unit,
    onKeyboardHide: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (areActivitiesFiltered) {
                        IconButton(onClick = {
                            onFilterQueryClear()
                            onFilterActivities(false)
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.stop_filtering)
                            )
                        }
                    } else {
                        IconButton(onClick = onDrawerStateChange) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.navigation_drawer_icon)
                            )
                        }
                    }
                },
                title = {
                    if (areActivitiesFiltered) {
                        OutlinedTextField(
                            value = filterQuery,
                            onValueChange = { query ->
                                onFilterQueryChange(query)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledBorderColor = Color.Transparent,
                                errorBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            trailingIcon = {
                                IconButton(onClick = onFilterQueryClear) {
                                    Icon(
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = stringResource(id = R.string.clear_text_field)
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    onKeyboardHide()
                                }
                            )
                        )
                    } else {
                        Text(text = stringResource(id = R.string.activities_title))
                    }
                },
                actions = {
                    if (isSavedActivitiesFABVisible && pagerState.currentPage == 0) {
                        IconButton(onClick = {
                            onSavedActivitiesDelete(savedActivities.filter { it.isSelected })
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete_activities)
                            )
                        }
                    }
                    IconButton(onClick = {
                        onFilterActivities(!areActivitiesFiltered)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.filter_activities_by_key_words)
                        )
                    }
                    IconButton(onClick = onNavigateToAddActivityScreen) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_activity)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavBar(
                items = bottomNavBarItems,
                selectedItemIndex = 2,
                onNavigate = onBottomBarNavigate
            )
        },
        floatingActionButton = {
            if (isSavedActivitiesFABVisible && pagerState.currentPage == 0) {
                ExtendedFloatingActionButton(onClick = {
                    onSavedActivitiesPerform(savedActivities.filter { it.isSelected })
                }) {
                    Icon(
                        imageVector = Icons.Default.PostAdd,
                        contentDescription = stringResource(id = R.string.save_button)
                    )
                    Text(text = stringResource(id = R.string.add))
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                activitiesTabRowItems.onEachIndexed { index, tabRowItem ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        text = {
                            Text(text = tabRowItem.toTabTitle())
                        },
                        onClick = {
                            onTabChange(index)
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                pageContent = { page ->
                    when (page) {
                        0 -> {
                            SavedActivitiesScreen(
                                savedActivities = savedActivities,
                                onSavedActivityClick = onSavedActivityClick
                            )
                        }
                        1 -> {
                            SearchActivityScreen(
                                intensityLevels = intensityLevels,
                                onIntensityLevelExpandedChange = onIntensityLevelExpandedChange,
                                onIntensityLevelActivitiesFetch = onIntensityLevelActivitiesFetch,
                                onActivityClick = onActivityClick
                            )
                        }
                    }
                }
            )
        }
    }
}