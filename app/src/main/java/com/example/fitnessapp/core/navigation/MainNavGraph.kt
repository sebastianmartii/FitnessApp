package com.example.fitnessapp.core.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
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
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.NutritionCalculatorViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator.FoodItemCreatorScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator.FoodItemCreatorViewModel

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
            val nutritionCalculatorState by nutritionCalculatorViewModel.state.collectAsStateWithLifecycle()

            NutritionScreen(
                state = state,
                tabRowItems = listOf(
                    TabRowItem.NUTRITION_CALCULATOR,
                    TabRowItem.RECIPES,
                    TabRowItem.MEAL_PLAN
                ),
                selectedDrawerItem = selectedDrawerItem,
                drawerState = drawerState,
                nutritionCalculatorState = nutritionCalculatorState,
                onNutritionCalculatorEvent = nutritionCalculatorViewModel::onEvent,
                onEvent = viewModel::onEvent,
                onDrawerEvent = viewModel::onDrawerEvent
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

            FoodItemCreatorScreen(
                name = state.name,
                foodComponents = state.foodComponents,
                onEvent = viewModel::onEvent,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}