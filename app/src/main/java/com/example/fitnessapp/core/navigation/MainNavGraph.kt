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
                onDrawerEvent = viewModel::onDrawerEvent
            )
        }
        composable(
            route = MainDestinations.Nutrition.route
        ) {
            val viewModel = hiltViewModel<NutritionViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val selectedDrawerItem by viewModel.selectedDrawerItem.collectAsStateWithLifecycle()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            NutritionScreen(
                state = state,
                tabRowItems = listOf(
                    TabRowItem.NUTRITION_CALCULATOR,
                    TabRowItem.RECIPES,
                    TabRowItem.MEAL_PLAN
                ),
                selectedDrawerItem = selectedDrawerItem,
                drawerState = drawerState,
                onEvent = viewModel::onEvent,
                onDrawerEvent = viewModel::onDrawerEvent
            )
        }
    }
}