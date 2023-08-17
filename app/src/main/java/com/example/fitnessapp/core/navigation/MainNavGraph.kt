package com.example.fitnessapp.core.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitnessapp.daily_overview_feature.presentation.DailyOverviewScreen
import com.example.fitnessapp.daily_overview_feature.presentation.DailyOverviewViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.NutritionScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.NutritionViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.TabRowItem
import com.example.fitnessapp.nutrition_calculator_feature.presentation.custom_meal_plan_creator.CustomMealPlanCreatorScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.custom_meal_plan_creator.CustomMealPlanCreatorViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.NutritionCalculatorState
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.NutritionCalculatorViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator.FoodItemCreatorScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator.FoodItemCreatorViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_nutrition_search.FoodNutritionSearchScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_nutrition_search.FoodNutritionSearchViewModel

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    navigation(
        route = NavGraphDestinations.MainNavGraph.route,
        startDestination = MainDestinations.Overview.route
    ) {
        composable(
            route = MainDestinations.Overview.route
        ) {
            val viewModel = hiltViewModel<DailyOverviewViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val selectedDrawerItem by viewModel.selectedDrawerItem.collectAsStateWithLifecycle()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            DailyOverviewScreen(
                drawerState = drawerState,
                state = state,
                selectedDrawerItem = selectedDrawerItem,
                eventFlow = viewModel.drawerEventFlow,
                onEvent = viewModel::onEvent,
                onDrawerEvent = viewModel::onDrawerEvent,
                onNavigateToNutritionScreen = {
                    navController.navigate(MainDestinations.Nutrition.route)
                }
            )
        }
        composable(
            route = MainDestinations.Nutrition.route
        ) {
            val viewModel = hiltViewModel<NutritionViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val selectedDrawerItem by viewModel.selectedDrawerItem.collectAsStateWithLifecycle()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            val nutritionCalculatorViewModel = hiltViewModel<NutritionCalculatorViewModel>()
            val nutritionCalculatorState by nutritionCalculatorViewModel.state.collectAsStateWithLifecycle(
                NutritionCalculatorState()
            )

            NutritionScreen(
                state = state,
                tabRowItems = listOf(
                    TabRowItem.CALCULATOR,
                    TabRowItem.RECIPES,
                    TabRowItem.MEAL_PLAN
                ),
                selectedDrawerItem = selectedDrawerItem,
                drawerState = drawerState,
                drawerEventFlow = viewModel.drawerEventFlow,
                nutritionCalculatorState = nutritionCalculatorState,
                onNutritionCalculatorEvent = nutritionCalculatorViewModel::onEvent,
                onEvent = viewModel::onEvent,
                onDrawerEvent = viewModel::onDrawerEvent,
                onNavigateToFoodItemCreator = {
                    navController.navigate(MainDestinations.FoodItemCreator.route)
                },
                onNavigateToSearchScreen = {
                    navController.navigate(MainDestinations.FoodNutritionSearch.route)
                }
            )
        }
        composable(
            route = MainDestinations.CustomMealPlanCreator.route
        ) {
            val viewModel = hiltViewModel<CustomMealPlanCreatorViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            CustomMealPlanCreatorScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onEvent = viewModel::onEvent
            )
        }
        composable(
            route = MainDestinations.FoodItemCreator.route
        ) {
            val viewModel = hiltViewModel<FoodItemCreatorViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val snackbarHostState = remember {
                SnackbarHostState()
            }

            FoodItemCreatorScreen(
                state = state,
                onEvent = viewModel::onEvent,
                snackbarFlow = viewModel.snackbarFlow,
                snackbarHostState = snackbarHostState,
                onFocusMove = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                onKeyboardHide = {
                    keyboardController?.hide()
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = MainDestinations.FoodNutritionSearch.route
        ) {
            val viewModel = hiltViewModel<FoodNutritionSearchViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val keyboardController = LocalSoftwareKeyboardController.current

            FoodNutritionSearchScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onKeyboardHide = {
                    keyboardController?.hide()
                },
                onNavigateToFoodItemCreator = {
                    navController.navigate(MainDestinations.FoodItemCreator.route)
                }
            )
        }
    }
}