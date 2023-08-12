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
import androidx.compose.ui.Modifier
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.core.util.drawerItemList
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toTabTitle
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.NutritionCalculatorEvent
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.NutritionCalculatorScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.NutritionCalculatorState

@Composable
fun NutritionScreen(
    state: NutritionState,
    tabRowItems: List<TabRowItem>,
    selectedDrawerItem: DrawerItem?,
    drawerState: DrawerState,
    nutritionCalculatorState: NutritionCalculatorState,
    onNutritionCalculatorEvent: (NutritionCalculatorEvent) -> Unit,
    onEvent: (NutritionEvent) -> Unit,
    onDrawerEvent: (DrawerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
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
                onTabChange = { tabRowItem ->
                    onEvent(NutritionEvent.OnTabChange(tabRowItem))
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
    onTabChange: (TabRowItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
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
                            onTabChange(tabRowItem)
                        }
                    )
                }
            }
            AnimatedContent(targetState = currentTabRowItem, label = "") { tabRowItem ->
                when(tabRowItem) {
                    TabRowItem.NUTRITION_CALCULATOR -> {
                        NutritionCalculatorScreen(
                            state = nutritionCalculatorState,
                            onEvent = onNutritionCalculatorEvent,
                            onNavigateToAddProductScreen = {

                            }
                        )
                    }
                    TabRowItem.RECIPES -> {
                        RecipesScreen()
                    }
                    TabRowItem.MEAL_PLAN -> {
                        MealPlanScreen(mealPlanTypeList = emptyList(), onCustomMealPLan = {})
                    }
                }
            }
        }
    }
}