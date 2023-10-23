package com.example.fitnessapp.core.navigation

import androidx.compose.runtime.getValue
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitnessapp.core.util.sharedViewModel
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevelAndCaloriesGoalScreen
import com.example.fitnessapp.profile_feature.presentation.sign_in.CaloriesGoalListScreen
import com.example.fitnessapp.profile_feature.presentation.sign_in.IntroductionScreen
import com.example.fitnessapp.profile_feature.presentation.sign_in.MeasurementsScreen
import com.example.fitnessapp.profile_feature.presentation.sign_in.ProfileListScreen
import com.example.fitnessapp.profile_feature.presentation.sign_in.SignInViewModel

fun NavGraphBuilder.signInNavGraph(navController: NavController) {
    navigation(
        route = NavGraphDestinations.SignInNavGraph.route,
        startDestination = SignInDestinations.Introduction.route
    ) {
        composable(route = SignInDestinations.Introduction.route) { entry ->
            val viewModel = entry.sharedViewModel<SignInViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()

            val keyboardController = LocalSoftwareKeyboardController.current

            IntroductionScreen(
                name = state.name,
                gender = state.gender,
                onEvent = viewModel::onEvent,
                onNavigateToMeasurementsScreen = {
                    navController.navigate(SignInDestinations.Measurements.route)
                },
                onNavigateToProfileListScreen = {
                    navController.navigate(SignInDestinations.ProfileList.route)
                },
                onKeyboardHide = {
                    keyboardController?.hide()
                }
            )
        }
        composable(route = SignInDestinations.Measurements.route) { entry -> 
            val viewModel = entry.sharedViewModel<SignInViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()

            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current

            MeasurementsScreen(
                age = state.age,
                height = state.height,
                weight = state.weight,
                onEvent = viewModel::onEvent,
                onNavigateToActivityLevelAndCaloriesGoalScreen = {
                    navController.navigate(SignInDestinations.ActivityLevelAndCaloriesGoal.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                onFocusMove = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                onKeyboardHide = {
                    keyboardController?.hide()
                }
            )
        }
        composable(route = SignInDestinations.ActivityLevelAndCaloriesGoal.route) { entry ->
            val viewModel = entry.sharedViewModel<SignInViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()

            ActivityLevelAndCaloriesGoalScreen(
                activityLevel = state.activityLevel,
                caloriesGoal = state.caloriesGoal,
                onEvent = viewModel::onEvent,
                onNavigateToOverviewScreen = {
                    navController.navigate(BottomNavBarDestinations.Overview.route)
                },
                onNavigateToCalculatedCaloriesScreen = {
                    navController.navigate(SignInDestinations.CalculatedCalories.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = SignInDestinations.CalculatedCalories.route) { entry ->
            val viewModel = entry.sharedViewModel<SignInViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()

            CaloriesGoalListScreen(
                calories = state.caloriesGoal,
                calculatedCaloriesList = state.calculatedCalories,
                onEvent = viewModel::onEvent,
                calculate = viewModel::calculateCalories,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = SignInDestinations.ProfileList.route) { entry ->
            val viewModel = entry.sharedViewModel<SignInViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()

            ProfileListScreen(
                profileList = state.profileList,
                onEvent = viewModel::onEvent,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToOverviewScreen = {
                    navController.navigate(NavGraphDestinations.MainNavGraph.route)
                }
            )
        }
    }
}