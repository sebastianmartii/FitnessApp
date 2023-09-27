package com.example.fitnessapp.history_feature.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.core.util.days
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HistoryScreen(
    state: HistoryState,
    drawerState: DrawerState,
    drawerItemList: List<DrawerItem>,
    selectedDrawerItem: DrawerItem?,
    drawerEventFlow: Flow<DrawerAction>,
    onDrawerEvent: (DrawerEvent) -> Unit,
    onNavigateToHistoryDetailsScreen: (Int) -> Unit,
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
                currentMont = state.currentMonth,
                currentMontDaysNumber = state.currentMonthDaysNumber,
                calendarDaysSuffix = state.calendarDaysSuffix,
                calendarDaysPrefix = state.calendarDaysPrefix,
                onDrawerStateChange = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                },
                onNavigateToHistoryDetailsScreen = onNavigateToHistoryDetailsScreen
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryScreenContent(
    currentMont: String,
    currentMontDaysNumber: Int,
    calendarDaysSuffix: Int,
    calendarDaysPrefix: Int,
    onDrawerStateChange: () -> Unit,
    onNavigateToHistoryDetailsScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = currentMont)
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
        },
        modifier = modifier
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(days) { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            if (calendarDaysPrefix > 0) {
                items(calendarDaysPrefix) {
                    Surface(
                        tonalElevation = 1.dp,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth(),
                        content = {}
                    )
                }
            }
            items(currentMontDaysNumber) {
                Surface(
                    tonalElevation = 4.dp,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .clickable {
                            onNavigateToHistoryDetailsScreen(it)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp, end = 4.dp)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = (it + 1).toString(),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                        )
                    }
                }
            }
            if (calendarDaysSuffix > 0) {
                items(calendarDaysSuffix) {
                    Surface(
                        tonalElevation = 1.dp,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth(),
                        content = {}
                    )
                }
            }
            /*FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                maxItemsInEachRow = 7,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth()
            ) {
                if (firstDayOfTheMonthOffset > 0) {
                    Spacer(
                        modifier = Modifier
                            .weight(firstDayOfTheMonthOffset.toFloat())
                            .padding(horizontal = 4.dp)
                    )
                }
                (1..31).onEach { day ->
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onNavigateToHistoryDetailsScreen(day)
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = day.toString(),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .padding(top = 24.dp, bottom = 4.dp, end = 4.dp)
                                    .align(Alignment.BottomEnd)
                            )
                        }
                    }
                }
            }*/
        }
    }
}