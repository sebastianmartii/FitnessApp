package com.example.fitnessapp.nutrition_calculator_feature.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.core.util.drawerItemList
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toTabTitle
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.NutritionCalculatorEvent
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.NutritionCalculatorScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.NutritionCalculatorState
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.NutritionCalculatorTopBar
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe_search.RecipeSearchScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe_search.RecipeSearchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NutritionScreen(
    state: NutritionState,
    tabRowItems: List<TabRowItem>,
    selectedDrawerItem: DrawerItem?,
    drawerState: DrawerState,
    drawerEventFlow: Flow<DrawerAction>,
    nutritionCalculatorState: NutritionCalculatorState,
    onNutritionCalculatorEvent: (NutritionCalculatorEvent) -> Unit,
    onEvent: (NutritionEvent) -> Unit,
    onDrawerEvent: (DrawerEvent) -> Unit,
    onNavigateToFoodItemCreator: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
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
                },
            )
        },
        content = {
            NutritionScreenContent(
                selectedTabIndex = state.selectedTabIndex,
                currentTabRowItem = state.currentTabRowItem,
                tabRowItems = tabRowItems,
                nutritionCalculatorState = nutritionCalculatorState,
                onNutritionCalculatorEvent = onNutritionCalculatorEvent,
                onNavigateToFoodItemCreator = onNavigateToFoodItemCreator,
                onNavigateToSearchScreen = onNavigateToSearchScreen,
                onTabChange = { tabRowItem, tabIndex ->
                    onEvent(NutritionEvent.OnTabChange(tabRowItem, tabIndex))
                },
                onDrawerStateChange = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                }
            )
        },
        modifier = modifier
    )
}

@Composable
private fun NutritionScreenContent(
    selectedTabIndex: Int,
    currentTabRowItem: TabRowItem,
    tabRowItems: List<TabRowItem>,
    nutritionCalculatorState: NutritionCalculatorState,
    onNutritionCalculatorEvent: (NutritionCalculatorEvent) -> Unit,
    onNavigateToFoodItemCreator: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onDrawerStateChange: () -> Unit,
    onTabChange: (item: TabRowItem, index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            NutritionCalculatorTopBar(
                onDrawerStateChange = onDrawerStateChange,
                onNavigateToFoodItemCreator = onNavigateToFoodItemCreator,
                onNavigateToSearchScreen = onNavigateToSearchScreen
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabRowItems.onEachIndexed { index, tabRowItem ->
                    Tab(
                        selected = selectedTabIndex == index,
                        text = {
                            Text(text = tabRowItem.toTabTitle())
                        },
                        onClick = {
                            onTabChange(tabRowItem, index)
                        }
                    )
                }
            }
            AnimatedContent(targetState = currentTabRowItem, label = "") { tabRowItem ->
                when(tabRowItem) {
                    TabRowItem.CALCULATOR -> {
                        NutritionCalculatorScreen(
                            state = nutritionCalculatorState,
                            onEvent = onNutritionCalculatorEvent,
                        )
                    }
                    TabRowItem.RECIPES -> {
                        RecipeSearchScreen(RecipeSearchState())
                    }
                    TabRowItem.MEAL_PLAN -> {
                        MealPlanScreen(mealPlanTypeList = emptyList(), onCustomMealPLan = {})
                    }
                }
            }
        }
    }
}