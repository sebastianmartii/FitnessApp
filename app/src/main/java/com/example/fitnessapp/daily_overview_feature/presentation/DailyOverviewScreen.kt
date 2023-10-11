package com.example.fitnessapp.daily_overview_feature.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.example.fitnessapp.activities_feature.data.mappers.toDurationString
import com.example.fitnessapp.core.navigation.BottomNavBar
import com.example.fitnessapp.core.navigation.NavigationBarItem
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.core.util.capitalizeEachWord
import com.example.fitnessapp.daily_overview_feature.data.mappers.toDailyNutritionCaloriesString
import com.example.fitnessapp.daily_overview_feature.domain.model.Activity
import com.example.fitnessapp.daily_overview_feature.domain.model.MealDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DailyOverviewScreen(
    drawerState: DrawerState,
    state: OverviewState,
    bottomNavBarItems: List<NavigationBarItem>,
    selectedDrawerItem: DrawerItem?,
    drawerItemList: List<DrawerItem>,
    eventFlow: Flow<DrawerAction>,
    snackbarFlow: Flow<String>,
    snackbarHostState: SnackbarHostState,
    onEvent: (OverviewEvent) -> Unit,
    onDrawerEvent: (DrawerEvent) -> Unit,
    onNavigateToNutritionScreen: () -> Unit,
    onNavigateToActivitiesScreen: () -> Unit,
    onNavigateToNavigationDrawerDestination: (String) -> Unit,
    onBottomBarNavigate: (NavigationBarItem) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { action ->
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
    LaunchedEffect(key1 = true) {
        snackbarFlow.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
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
                snackbarHostState = snackbarHostState,
                mealPlan = state.mealPlan,
                activities = state.activities,
                mealDetails = state.mealDetails,
                bottomNavBarItems = bottomNavBarItems,
                onDrawerOpen = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                },
                onMealAdd = onNavigateToNutritionScreen,
                onMealReset = { meal ->
                    onEvent(OverviewEvent.OnMealReset(meal))
                },
                onMealExpand = { meal, areDetailsEmpty->
                    onEvent(OverviewEvent.OnMealDetailsExpand(meal, areDetailsEmpty))
                },
                onActivityAdd = onNavigateToActivitiesScreen,
                onActivityDelete = { activity ->
                    onEvent(OverviewEvent.OnActivityDelete(activity))
                },
                onBottomBarNavigate = onBottomBarNavigate
            )
        }
    )
}


@Composable
private fun DailyOverviewContent(
    caloriesGoal: Int,
    currentCaloriesCount: Int,
    mealPlan: List<String>?,
    activities: List<Activity>,
    mealDetails: List<MealDetails>,
    bottomNavBarItems: List<NavigationBarItem>,
    progress: Dp,
    snackbarHostState: SnackbarHostState,
    onDrawerOpen: () -> Unit,
    onMealAdd: () -> Unit,
    onMealExpand: (meal: String, areDetailsEmpty: Boolean) -> Unit,
    onMealReset: (String) -> Unit,
    onActivityAdd: () -> Unit,
    onActivityDelete: (Activity) -> Unit,
    onBottomBarNavigate: (NavigationBarItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            OverviewTopBar(
                onDrawerStateChange = onDrawerOpen
            )
        },
        bottomBar = {
            BottomNavBar(
                items = bottomNavBarItems,
                selectedItemIndex = 0,
                onNavigate = onBottomBarNavigate
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
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
            Spacer(modifier = Modifier.height(64.dp))
            DailyNutritionSection(
                mealPlan = mealPlan,
                mealDetails = mealDetails,
                onMealAdd = onMealAdd,
                onMealReset = onMealReset,
                onMealExpand = onMealExpand
            )
            Spacer(modifier = Modifier.height(16.dp))
            DailyActivitiesSection(
                activities = activities,
                onActivityAdd = onActivityAdd,
                onActivityDelete = onActivityDelete
            )
        }
    }
}

@Composable
private fun DailyNutritionSection(
    mealPlan: List<String>?,
    mealDetails: List<MealDetails>,
    onMealAdd: () -> Unit,
    onMealExpand: (meal: String, areDetailsEmpty: Boolean) -> Unit,
    onMealReset: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.meals_text),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onMealAdd) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_meal_button)
                )
            }
        }
        mealPlan?.onEach { meal ->
            val details = mealDetails.find { it.meal == meal }
            Column(
                modifier = Modifier
                    .animateContentSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .clickable {
                            onMealExpand(meal, details == null)
                        }
                ) {
                    Text(
                        text = meal,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = details.toDailyNutritionCaloriesString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    AnimatedVisibility(visible = details?.areVisible == true) {
                        IconButton(
                            onClick = {
                                onMealReset(meal)
                            },
                            modifier = Modifier
                                .size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete_meal)
                            )
                        }
                    }
                }
                if (details?.areVisible == true) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.ingredients_headline),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                    )
                    Text(
                        text = details.ingredients.joinToString(separator = ", "),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic
                        ),
                        modifier = Modifier
                            .padding(horizontal = 32.dp)

                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.daily_nutrition_nutritional_value_headline),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.daily_nutrition_details_nutritional_value,
                            details.servingSize,
                            details.carbs,
                            details.fat,
                            details.protein
                        ),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic
                        ),
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun DailyActivitiesSection(
    activities: List<Activity>,
    onActivityAdd: () -> Unit,
    onActivityDelete: (Activity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.activities_title),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onActivityAdd) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_activity)
                )
            }
        }
        activities.onEach { activity ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = activity.name.capitalizeEachWord(),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(
                            id = R.string.daily_activities_activities_supporting_text,
                            activity.duration.toDurationString(true),
                            activity.caloriesBurned
                        ),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        onActivityDelete(activity)
                    },
                    modifier = Modifier
                        .size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete_daily_activity)
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