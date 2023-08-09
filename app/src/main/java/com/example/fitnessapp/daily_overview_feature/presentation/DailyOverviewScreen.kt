package com.example.fitnessapp.daily_overview_feature.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.core.util.drawerItemList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DailyOverviewScreen(
    drawerState: DrawerState,
    state: OverviewState,
    selectedDrawerItem: DrawerItem?,
    eventFlow: Flow<DrawerAction>,
    onEvent: (OverviewEvent) -> Unit,
    onDrawerEvent: (DrawerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when(event) {
                DrawerAction.CloseNavigationDrawer -> {
                    drawerState.close()
                }
                DrawerAction.OpenNavigationDrawer -> {
                    drawerState.open()
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
                },
            )
        },
        content = {
            DailyOverviewContent(
                caloriesGoal = state.caloriesGoal,
                currentCaloriesCount = state.currentCaloriesCount,
                progress = state.progress,
                onDrawerOpen = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                },
                onMealAdd = {
                    onEvent(OverviewEvent.OnAddMeal)
                }
            )
        },
        modifier = modifier
    )
}


@Composable
private fun DailyOverviewContent(
    caloriesGoal: Int,
    currentCaloriesCount: Int,
    progress: Dp,
    onDrawerOpen: () -> Unit,
    onMealAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            OverviewTopBar(
                onDrawerStateChange = onDrawerOpen
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "$currentCaloriesCount/$caloriesGoal",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic
                )
            )
            CustomLinearCaloriesProgressBar(
                progress = progress
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = stringResource(id = R.string.meals_text),
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = onMealAdd) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_meal_button)
                    )
                }
            }
        }
    }
}

@Composable
private fun CustomLinearCaloriesProgressBar(
    progress: Dp,
    modifier: Modifier = Modifier
) {
    val animatedWidth by animateDpAsState(
        targetValue = progress,
        label = "",
        animationSpec = tween(
            durationMillis = 2000,
            delayMillis = 0
        )
    )
    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .height(30.dp)
                .width(330.dp)
                .background(MaterialTheme.colorScheme.onTertiary)
                .align(Alignment.Center)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.large
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.tertiary)
                    .width(animatedWidth)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OverviewTopBar(
    onDrawerStateChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onDrawerStateChange) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.navigation_drawer_icon)
                )
            }
        },
        title = {
            Text(text = stringResource(id = R.string.overview_title))
        },
        modifier = modifier
    )
}