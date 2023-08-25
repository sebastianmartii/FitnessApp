package com.example.fitnessapp.activities_feature.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
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
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.IntensityItem
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.core.util.drawerItemList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ActivityListScreen(
    drawerState: DrawerState,
    selectedDrawerItem: DrawerItem?,
    drawerEventFlow: Flow<DrawerAction>,
    onDrawerEvent: (DrawerEvent) -> Unit,
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

        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActivityListScreenContent(
    state: ActivitiesState,
    onDrawerStateChange: () -> Unit,
    onIntensityLevelExpandedChange: (isExpanded: Boolean, index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
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
                    Text(text = stringResource(id = R.string.activities_title))
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            state.intensityLevels.onEachIndexed { index, item ->
                IntensitySection(
                    intensityItem = item,
                    intensityActivities = item.activities,
                    onSectionExpand = { expanded ->
                        onIntensityLevelExpandedChange(expanded, index)
                    }
                )
            }
        }
    }
}

@Composable
private fun IntensitySection(
    intensityItem: IntensityItem,
    intensityActivities: List<Activity>,
    onSectionExpand: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = intensityItem.intensityLevel.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(0.8f)
            )
            IconButton(onClick = {
                onSectionExpand(!intensityItem.isExpanded)
            }) {
                Icon(
                    imageVector = if (intensityItem.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = stringResource(id = R.string.expand)
                )
            }
        }
        if (intensityItem.isExpanded) {
            LazyColumn {
                items(intensityActivities) { activity ->
                    ListItem(
                        headlineContent = {
                            Text(text = activity.name)
                        },
                        supportingContent = {
                            Text(text = activity.description)
                        }
                    )
                }
            }
        }
    }
}