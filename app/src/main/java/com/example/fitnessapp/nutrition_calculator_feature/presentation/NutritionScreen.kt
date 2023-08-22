package com.example.fitnessapp.nutrition_calculator_feature.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.fitnessapp.R
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.core.util.drawerItemList
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toTabTitle
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanEvent
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanState
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.CalculatorTopBarActions
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.NutritionCalculatorEvent
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.NutritionCalculatorScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.NutritionCalculatorState
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeSearchEvent
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeSearchScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeSearchState
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeTopBarActions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionScreen(
    state: NutritionState,
    tabRowItems: List<TabRowItem>,
    mealPlanState: MealPlanState,
    selectedDrawerItem: DrawerItem?,
    mealPlanEventFlow: Flow<MealPlanViewModel.UiEvent>,
    mealPlanBottomSheetScaffoldState: BottomSheetScaffoldState,
    drawerState: DrawerState,
    drawerEventFlow: Flow<DrawerAction>,
    nutritionCalculatorState: NutritionCalculatorState,
    recipeSearchState: RecipeSearchState,
    onRecipeSearchEvent: (RecipeSearchEvent) -> Unit,
    onNutritionCalculatorEvent: (NutritionCalculatorEvent) -> Unit,
    onMealPlanEvent: (MealPlanEvent) -> Unit,
    onEvent: (NutritionEvent) -> Unit,
    onDrawerEvent: (DrawerEvent) -> Unit,
    onNavigateToFoodItemCreator: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToRecipeDetails: () -> Unit,
    onKeyboardHide: () -> Unit,
    onFocusMove: () -> Unit,
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
                mealPlanState = mealPlanState,
                mealPlanEventFlow = mealPlanEventFlow,
                mealPlanBottomSheetScaffoldState = mealPlanBottomSheetScaffoldState,
                nutritionCalculatorState = nutritionCalculatorState,
                recipeSearchState = recipeSearchState,
                onNutritionCalculatorEvent = onNutritionCalculatorEvent,
                onMealPlanEvent = onMealPlanEvent,
                onNavigateToFoodItemCreator = onNavigateToFoodItemCreator,
                onNavigateToSearchScreen = onNavigateToSearchScreen,
                onTabChange = { tabRowItem, tabIndex ->
                    onEvent(NutritionEvent.OnTabChange(tabRowItem, tabIndex))
                },
                onDrawerStateChange = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                },
                onRecipeSearchEvent = onRecipeSearchEvent,
                onKeyboardHide = onKeyboardHide,
                onFocusMove = onFocusMove,
                onNavigateToRecipeDetails = onNavigateToRecipeDetails,
            )
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NutritionScreenContent(
    selectedTabIndex: Int,
    currentTabRowItem: TabRowItem,
    tabRowItems: List<TabRowItem>,
    mealPlanEventFlow: Flow<MealPlanViewModel.UiEvent>,
    mealPlanBottomSheetScaffoldState: BottomSheetScaffoldState,
    mealPlanState: MealPlanState,
    nutritionCalculatorState: NutritionCalculatorState,
    recipeSearchState: RecipeSearchState,
    onRecipeSearchEvent: (RecipeSearchEvent) -> Unit,
    onNutritionCalculatorEvent: (NutritionCalculatorEvent) -> Unit,
    onMealPlanEvent: (MealPlanEvent) -> Unit,
    onNavigateToFoodItemCreator: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToRecipeDetails: () -> Unit,
    onKeyboardHide: () -> Unit,
    onFocusMove: () -> Unit,
    onDrawerStateChange: () -> Unit,
    onTabChange: (item: TabRowItem, index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (currentTabRowItem == TabRowItem.RECIPES && recipeSearchState.isSearchBarActive) {
                        IconButton(onClick = {
                            onRecipeSearchEvent(RecipeSearchEvent.OnIsSearchBarActiveChange(false))
                            onKeyboardHide()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.stop_search_button)
                            )
                        }
                    } else {
                        IconButton(onClick = onDrawerStateChange) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.navigation_drawer_icon)
                            )
                        }
                    }
                },
                title = {
                    if (currentTabRowItem == TabRowItem.RECIPES && recipeSearchState.isSearchBarActive) {
                        OutlinedTextField(
                            value = recipeSearchState.query,
                            onValueChange = {
                                onRecipeSearchEvent(RecipeSearchEvent.OnQueryChange(it))
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledBorderColor = Color.Transparent,
                                errorBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            trailingIcon = {
                                IconButton(onClick = {
                                    onRecipeSearchEvent(RecipeSearchEvent.OnTextFieldClear)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = stringResource(id = R.string.clear_text_field)
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    onRecipeSearchEvent(RecipeSearchEvent.OnRecipeSearch)
                                    onKeyboardHide()
                                }
                            )
                        )
                    } else {
                        Text(text = stringResource(id = R.string.nutrition_title))
                    }
                },
                actions = {
                    AnimatedContent(targetState = currentTabRowItem, label = "") {tabRowItem ->
                        when(tabRowItem) {
                            TabRowItem.CALCULATOR -> {
                                CalculatorTopBarActions(
                                    onNavigateToFoodItemCreator = onNavigateToFoodItemCreator,
                                    onNavigateToSearchScreen = onNavigateToSearchScreen
                                )
                            }
                            TabRowItem.RECIPES -> {
                                RecipeTopBarActions(
                                    onSearchActiveChange = {
                                        onRecipeSearchEvent(RecipeSearchEvent.OnIsSearchBarActiveChange(!recipeSearchState.isSearchBarActive))
                                    }
                                )
                            }
                            TabRowItem.MEAL_PLAN -> {

                            }
                        }
                    }
                }
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
                        RecipeSearchScreen(
                            state = recipeSearchState,
                            onNavigateToRecipeDetails = {
                                onRecipeSearchEvent(RecipeSearchEvent.OnNavigateToRecipeDetails(it))
                                onNavigateToRecipeDetails()
                            },
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                    TabRowItem.MEAL_PLAN -> {
                        MealPlanScreen(
                            state = mealPlanState,
                            onDeleteMeal = {
                                onMealPlanEvent(MealPlanEvent.OnDeleteMeal(it))
                            },
                            scaffoldState = mealPlanBottomSheetScaffoldState ,
                            eventFlow = mealPlanEventFlow,
                            onAddMeal = {
                                onMealPlanEvent(MealPlanEvent.OnAddMeal)
                            },
                            onMealPlanExpand = { isExpanded, type ->
                                onMealPlanEvent(MealPlanEvent.OnMealPlanExpandedChange(isExpanded, type))
                            },
                            onMealPlanSelect = { type ->
                                onMealPlanEvent(MealPlanEvent.OnMealPlanSelectedChange(type))
                            },
                            onKeyboardHide = onKeyboardHide,
                            onMealNameChange = { name, index ->
                                onMealPlanEvent(MealPlanEvent.OnMealNameChange(name, index))
                            },
                            onFocusMove = onFocusMove,
                            onSheetClose = {
                                onMealPlanEvent(MealPlanEvent.OnSheetClose)
                            },
                            onSheetOpen = {
                                onMealPlanEvent(MealPlanEvent.OnSheetOpen)
                            }
                        )
                    }
                }
            }
        }
    }
}