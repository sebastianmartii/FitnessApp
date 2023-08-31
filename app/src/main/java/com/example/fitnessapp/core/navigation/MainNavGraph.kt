package com.example.fitnessapp.core.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitnessapp.activities_feature.presentation.ActivitiesScreen
import com.example.fitnessapp.activities_feature.presentation.ActivitiesTabRowItem
import com.example.fitnessapp.activities_feature.presentation.ActivitiesViewModel
import com.example.fitnessapp.activities_feature.presentation.activity_creator.ActivityCreatorScreen
import com.example.fitnessapp.activities_feature.presentation.activity_creator.ActivityCreatorViewModel
import com.example.fitnessapp.core.util.bottomNavBarItems
import com.example.fitnessapp.core.util.sharedViewModel
import com.example.fitnessapp.daily_overview_feature.presentation.DailyOverviewScreen
import com.example.fitnessapp.daily_overview_feature.presentation.DailyOverviewViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.NutritionScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.NutritionViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.NutritionTabRowItem
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.NutritionCalculatorState
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.NutritionCalculatorViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.food_item_creator.FoodItemCreatorScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.food_item_creator.FoodItemCreatorViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.food_nutrition_search.FoodNutritionSearchScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.food_nutrition_search.FoodNutritionSearchViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeDetailsScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeSearchEvent
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    navigation(
        route = NavGraphDestinations.MainNavGraph.route,
        startDestination = BottomNavBarDestinations.Overview.route
    ) {
        composable(
            route = BottomNavBarDestinations.Overview.route
        ) {
            val viewModel = hiltViewModel<DailyOverviewViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val selectedDrawerItem by viewModel.selectedDrawerItem.collectAsStateWithLifecycle()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            DailyOverviewScreen(
                drawerState = drawerState,
                state = state,
                selectedDrawerItem = selectedDrawerItem,
                bottomNavBarItems = bottomNavBarItems,
                eventFlow = viewModel.drawerEventFlow,
                onEvent = viewModel::onEvent,
                onDrawerEvent = viewModel::onDrawerEvent,
                onNavigateToNutritionScreen = {
                    navController.navigate(BottomNavBarDestinations.Nutrition.route)
                },
                onBottomBarNavigate = { navigationBarItem ->
                    navController.navigate(navigationBarItem.route)
                }
            )
        }
        composable(
            route = BottomNavBarDestinations.Nutrition.route
        ) { entry ->
            val viewModel = hiltViewModel<NutritionViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val selectedDrawerItem by viewModel.selectedDrawerItem.collectAsStateWithLifecycle()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            val nutritionCalculatorViewModel = hiltViewModel<NutritionCalculatorViewModel>()
            val nutritionCalculatorState by nutritionCalculatorViewModel.state.collectAsStateWithLifecycle(
                NutritionCalculatorState()
            )

            val recipeSearchViewModel = entry.sharedViewModel<RecipeSearchViewModel>(navController)
            val recipeSearchState by recipeSearchViewModel.state.collectAsStateWithLifecycle()

            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current

            val mealPlanViewModel = hiltViewModel<MealPlanViewModel>()
            val mealPlanState by mealPlanViewModel.state.collectAsStateWithLifecycle()

            val sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Hidden, skipHiddenState = false)
            val mealPlanBottomSheetScaffoldState = rememberBottomSheetScaffoldState(sheetState)


            NutritionScreen(
                state = state,
                nutritionTabRowItems = listOf(
                    NutritionTabRowItem.CALCULATOR,
                    NutritionTabRowItem.RECIPES,
                    NutritionTabRowItem.MEAL_PLAN
                ),
                bottomNavBarItems = bottomNavBarItems,
                mealPlanEventFlow = mealPlanViewModel.eventFlow,
                mealPlanBottomSheetScaffoldState = mealPlanBottomSheetScaffoldState,
                mealPlanState = mealPlanState,
                selectedDrawerItem = selectedDrawerItem,
                drawerState = drawerState,
                drawerEventFlow = viewModel.drawerEventFlow,
                nutritionCalculatorState = nutritionCalculatorState,
                recipeSearchState = recipeSearchState,
                onNutritionCalculatorEvent = nutritionCalculatorViewModel::onEvent,
                onEvent = viewModel::onEvent,
                onDrawerEvent = viewModel::onDrawerEvent,
                onMealPlanEvent = mealPlanViewModel::onEvent,
                onNavigateToFoodItemCreator = {
                    navController.navigate(MainDestinations.FoodItemCreator.route)
                },
                onNavigateToSearchScreen = {
                    navController.navigate(MainDestinations.FoodNutritionSearch.route)
                },
                onRecipeSearchEvent = recipeSearchViewModel::onEvent,
                onKeyboardHide = {
                    keyboardController?.hide()
                },
                onFocusMove = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                onNavigateToRecipeDetails = {
                    navController.navigate(MainDestinations.RecipeDetails.route)
                },
                onBottomBarNavigate = { navigationBarItem ->
                    navController.navigate(navigationBarItem.route)
                }
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
        composable(
            route = MainDestinations.RecipeDetails.route
        ) { entry ->
            val viewModel = entry.sharedViewModel<RecipeSearchViewModel>(navController)
            val inspectedRecipe by viewModel.inspectedRecipe.collectAsStateWithLifecycle()
            val isRecipeSaved by viewModel.isRecipeSaved.collectAsStateWithLifecycle()

            val uriHandler = LocalUriHandler.current

            RecipeDetailsScreen(
                recipe = inspectedRecipe!!,
                isRecipeSaved = isRecipeSaved,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onIsRecipeSavedChange = { recipe, isSaved ->
                    viewModel.onEvent(RecipeSearchEvent.OnIsRecipeSavedChange(recipe, isSaved))
                },
                onExternalUrlOpen = {
                    uriHandler.openUri(it)
                }
            )
        }
        composable(
            route = BottomNavBarDestinations.Activities.route
        ) {
            val viewModel = hiltViewModel<ActivitiesViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val selectedDrawerItem by viewModel.selectedDrawerItem.collectAsStateWithLifecycle()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current

            ActivitiesScreen(
                state = state,
                activitiesTabRowItems = listOf(
                    ActivitiesTabRowItem.SAVED,
                    ActivitiesTabRowItem.SEARCH
                ),
                bottomNavBarItems = bottomNavBarItems,
                drawerState = drawerState,
                selectedDrawerItem = selectedDrawerItem,
                drawerEventFlow = viewModel.drawerEventFlow,
                onDrawerEvent = viewModel::onDrawerEvent,
                onEvent = viewModel::onEvent,
                onBottomBarNavigate = { navigationBarItem ->
                    navController.navigate(navigationBarItem.route)
                },
                onFocusMove = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                onKeyboardHide = {
                    keyboardController?.hide()
                },
                onNavigateToAddActivityScreen = {
                    navController.navigate(MainDestinations.ActivityCreator.route)
                }
            )
        }
        composable(
            route = MainDestinations.ActivityCreator.route
        ) {
            val viewModel = hiltViewModel<ActivityCreatorViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current

            ActivityCreatorScreen(
                state = state,
                onEvent = viewModel::onEvent,
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
    }
}