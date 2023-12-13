package com.example.fitnessapp.nutrition_calculator_feature.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.fitnessapp.R
import com.example.fitnessapp.core.navigation.BottomNavBar
import com.example.fitnessapp.core.navigation.NavigationBarItem
import com.example.fitnessapp.core.navigation_drawer.DrawerAction
import com.example.fitnessapp.core.navigation_drawer.DrawerContent
import com.example.fitnessapp.core.navigation_drawer.DrawerEvent
import com.example.fitnessapp.core.navigation_drawer.DrawerItem
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toTabTitle
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanEvent
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanState
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.CalculatorTopBarActions
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.MealSelectionDialog
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.NutritionCalculatorEvent
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.NutritionCalculatorScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.NutritionCalculatorState
import com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.NutritionCalculatorViewModel
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeSearchEvent
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeSearchScreen
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeSearchState
import com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe.RecipeTopBarActions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NutritionScreen(
    nutritionTabRowItems: List<NutritionTabRowItem>,
    bottomNavBarItems: List<NavigationBarItem>,
    mealSelectionDialogMealList: List<String>?,
    mealPlanState: MealPlanState,
    drawerItemList: List<DrawerItem>,
    selectedDrawerItem: DrawerItem?,
    mealPlanEventFlow: Flow<MealPlanViewModel.UiEvent>,
    mealPlanBottomSheetScaffoldState: BottomSheetScaffoldState,
    drawerState: DrawerState,
    pagerState: PagerState,
    pagerFlow: Flow<Int>,
    nutritionCalculatorEventFlow: Flow<NutritionCalculatorViewModel.UiEvent>,
    drawerEventFlow: Flow<DrawerAction>,
    nutritionCalculatorState: NutritionCalculatorState,
    recipeSearchState: RecipeSearchState,
    onPageChange: (Int) -> Unit,
    onRecipeSearchEvent: (RecipeSearchEvent) -> Unit,
    onNutritionCalculatorEvent: (NutritionCalculatorEvent) -> Unit,
    onMealPlanEvent: (MealPlanEvent) -> Unit,
    onDrawerEvent: (DrawerEvent) -> Unit,
    onNavigateToFoodItemCreator: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToRecipeDetails: () -> Unit,
    onNavigateToNavigationDrawerDestination: (String) -> Unit,
    onKeyboardHide: () -> Unit,
    onFocusMove: () -> Unit,
    onBottomBarNavigate: (NavigationBarItem) -> Unit,
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
                is DrawerAction.NavigateToDrawerDestination -> {
                    onNavigateToNavigationDrawerDestination(action.route)
                }
            }
        }
    }
    LaunchedEffect(key1 = true) {
        pagerFlow.collectLatest { page ->
            pagerState.animateScrollToPage(page)
        }
    }
    LaunchedEffect(key1 = true) {
        nutritionCalculatorEventFlow.collectLatest { event ->
            when(event) {
                NutritionCalculatorViewModel.UiEvent.NavigateToMealPlanScreen -> {
                    pagerState.animateScrollToPage(2)
                }
            }
        }
    }
    if (pagerState.currentPage == 0 && nutritionCalculatorState.isMealSelectionDialogVisible) {
        MealSelectionDialog(
            mealList = mealSelectionDialogMealList,
            selectedMeal = nutritionCalculatorState.selectedMeal,
            onMealSelectionDialogDismiss = {
                onNutritionCalculatorEvent(NutritionCalculatorEvent.OnMealSelectionDialogDismiss)
            },
            onMealSelectionDialogConfirm = { meal ->
                onNutritionCalculatorEvent(NutritionCalculatorEvent.OnMealSelectionDialogConfirm(meal))
            },
            onMealSelect = { meal ->
                onNutritionCalculatorEvent(NutritionCalculatorEvent.OnMealSelectionDialogMealSelect(meal))
            }
        )
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
                pagerState = pagerState,
                nutritionTabRowItems = nutritionTabRowItems,
                bottomNavBarItems = bottomNavBarItems,
                mealSelectionDialogMealList = mealSelectionDialogMealList,
                mealPlanState = mealPlanState,
                mealPlanEventFlow = mealPlanEventFlow,
                mealPlanBottomSheetScaffoldState = mealPlanBottomSheetScaffoldState,
                nutritionCalculatorState = nutritionCalculatorState,
                recipeSearchState = recipeSearchState,
                onNutritionCalculatorEvent = onNutritionCalculatorEvent,
                onMealPlanEvent = onMealPlanEvent,
                onNavigateToFoodItemCreator = onNavigateToFoodItemCreator,
                onNavigateToSearchScreen = onNavigateToSearchScreen,
                onPageChange = onPageChange,
                onDrawerStateChange = {
                    onDrawerEvent(DrawerEvent.OpenDrawer)
                },
                onRecipeSearchEvent = onRecipeSearchEvent,
                onKeyboardHide = onKeyboardHide,
                onFocusMove = onFocusMove,
                onNavigateToRecipeDetails = onNavigateToRecipeDetails,
                onBottomBarNavigate = onBottomBarNavigate
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun NutritionScreenContent(
    nutritionTabRowItems: List<NutritionTabRowItem>,
    bottomNavBarItems: List<NavigationBarItem>,
    mealSelectionDialogMealList: List<String>?,
    mealPlanEventFlow: Flow<MealPlanViewModel.UiEvent>,
    mealPlanBottomSheetScaffoldState: BottomSheetScaffoldState,
    mealPlanState: MealPlanState,
    pagerState: PagerState,
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
    onBottomBarNavigate: (NavigationBarItem) -> Unit,
    onDrawerStateChange: () -> Unit,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (pagerState.currentPage == 1 && recipeSearchState.isSearchBarActive) {
                        IconButton(onClick = {
                            onRecipeSearchEvent(RecipeSearchEvent.OnIsSearchBarActiveChange(false))
                            onKeyboardHide()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
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
                    if (pagerState.currentPage == 1 && recipeSearchState.isSearchBarActive) {
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
                                    onRecipeSearchEvent(RecipeSearchEvent.OnQueryClear)
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
                    AnimatedContent(targetState = pagerState.currentPage, label = "") { page ->
                        when(page) {
                            0 -> {
                                CalculatorTopBarActions(
                                    isDeleteFoodItemButtonVisible = pagerState.currentPage == 0 && nutritionCalculatorState.isFABVisible,
                                    onDeleteSelectedFoodItems = {
                                        onNutritionCalculatorEvent(NutritionCalculatorEvent.OnFoodItemDelete)
                                    },
                                    onNavigateToFoodItemCreator = onNavigateToFoodItemCreator,
                                    onNavigateToSearchScreen = onNavigateToSearchScreen
                                )
                            }
                            1 -> {
                                RecipeTopBarActions(
                                    onSearchActiveChange = {
                                        onRecipeSearchEvent(RecipeSearchEvent.OnIsSearchBarActiveChange(!recipeSearchState.isSearchBarActive))
                                    }
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomNavBar(
                items = bottomNavBarItems,
                selectedItemIndex = 1,
                onNavigate = onBottomBarNavigate
            )
        },
        floatingActionButton = {
            if (pagerState.currentPage == 0 && nutritionCalculatorState.isFABVisible) {
                ExtendedFloatingActionButton(onClick = {
                    onNutritionCalculatorEvent(NutritionCalculatorEvent.OnFoodItemsAdd(mealSelectionDialogMealList?.isEmpty() ?: true))
                }) {
                    Icon(
                        imageVector = Icons.Default.PostAdd,
                        contentDescription = stringResource(id = R.string.add)
                    )
                    Text(text = stringResource(id = R.string.add))
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
                nutritionTabRowItems.onEachIndexed { index, tabRowItem ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        text = {
                            Text(text = tabRowItem.toTabTitle())
                        },
                        onClick = {
                            onPageChange(index)
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                pageContent = {page ->
                    when(page) {
                        0 -> {
                            NutritionCalculatorScreen(
                                state = nutritionCalculatorState,
                                onEvent = onNutritionCalculatorEvent,
                            )
                        }
                        1 -> {
                            RecipeSearchScreen(
                                state = recipeSearchState,
                                onNavigateToRecipeDetails = { recipe ->
                                    onRecipeSearchEvent(RecipeSearchEvent.OnNavigateToRecipeDetails(recipe))
                                    onNavigateToRecipeDetails()
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                        2 -> {
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
                                onMealPlanSelect = { type, plan ->
                                    onMealPlanEvent(MealPlanEvent.OnMealPlanSelectedChange(type, plan))
                                },
                                onKeyboardHide = onKeyboardHide,
                                onMealNameChange = { name, index ->
                                    onMealPlanEvent(MealPlanEvent.OnMealNameChange(name, index))
                                },
                                onFocusMove = onFocusMove,
                                onSheetClose = {
                                    onMealPlanEvent(MealPlanEvent.OnSheetClose)
                                },
                                onCustomMealPlanSave = { plan ->
                                    onMealPlanEvent(MealPlanEvent.OnCustomMealPlanSave(plan))
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}