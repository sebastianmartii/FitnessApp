package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionCalculatorScreen(
    state: NutritionCalculatorState,
    onEvent: (NutritionCalculatorEvent) -> Unit,
    onNavigateToAddProductScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics {
                isTraversalGroup = true
            }
    ) {
        SearchBar(
            query = state.query,
            onQueryChange = {
                onEvent(NutritionCalculatorEvent.OnQueryChange(it))
            },
            onSearch = {
                onEvent(NutritionCalculatorEvent.OnNutritionCalculate(it))
            },
            active = state.isSearchBarActive,
            onActiveChange = {
                onEvent(NutritionCalculatorEvent.OnIsSearchBarActiveChange(it))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.calculate_nutrition)
                )
            },
            trailingIcon = {
                IconButton(onClick = onNavigateToAddProductScreen) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_product)
                    )
                }
            },
            content = {
                state.calculatedProducts.onEach { foodItem ->
                    ListItem(
                        headlineContent = {
                            Text(text = foodItem.name)
                        },
                        supportingContent = {
                            if (state.expandedFoodItems.contains(foodItem)) {
                                Text(
                                    text = stringResource(
                                        id = R.string.food_item_supporting_text_expanded,
                                        foodItem.servingSize,
                                        foodItem.calories,
                                        foodItem.carbs,
                                        foodItem.totalFat,
                                        foodItem.protein,
                                        foodItem.saturatedFat,
                                        foodItem.sugar,
                                        foodItem.fiber
                                    )
                                )
                            } else {
                                Text(
                                    text = stringResource(
                                        id = R.string.food_item_supporting_text,
                                        foodItem.servingSize,
                                        foodItem.calories,
                                        foodItem.carbs,
                                        foodItem.totalFat,
                                        foodItem.protein
                                    )
                                )
                            }
                        },
                        leadingContent = {
                            Checkbox(
                                checked = state.selectedProducts.contains(foodItem),
                                onCheckedChange = {
                                    onEvent(NutritionCalculatorEvent.OnFoodItemSelectedChange(foodItem, state.selectedProducts.contains(foodItem)))
                                }
                            )
                        },
                        trailingContent = {
                            IconButton(onClick = {
                                onEvent(NutritionCalculatorEvent.OnFoodItemExpandedChange(foodItem, state.expandedFoodItems.contains(foodItem)))
                            }) {
                                if (state.expandedFoodItems.contains(foodItem)) {
                                    Icon(
                                        imageVector = Icons.Default.ExpandLess,
                                        contentDescription = stringResource(id = R.string.collapse)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.ExpandMore,
                                        contentDescription = stringResource(id = R.string.expand)
                                    )
                                }
                            }
                        }
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics {
                    traversalIndex = -1f
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            items(state.cachedProducts) { foodItem ->
                ListItem(
                    headlineContent = {
                        Text(text = foodItem.name)
                    },
                    supportingContent = {
                        if (state.expandedFoodItems.contains(foodItem)) {
                            Text(
                                text = stringResource(
                                    id = R.string.food_item_supporting_text_expanded,
                                    foodItem.servingSize,
                                    foodItem.calories,
                                    foodItem.carbs,
                                    foodItem.totalFat,
                                    foodItem.protein,
                                    foodItem.saturatedFat,
                                    foodItem.sugar,
                                    foodItem.fiber
                                )
                            )
                        } else {
                            Text(
                                text = stringResource(
                                    id = R.string.food_item_supporting_text,
                                    foodItem.servingSize,
                                    foodItem.calories,
                                    foodItem.carbs,
                                    foodItem.totalFat,
                                    foodItem.protein
                                )
                            )
                        }
                    },
                    leadingContent = {
                        Checkbox(
                            checked = state.selectedProducts.contains(foodItem),
                            onCheckedChange = {
                                onEvent(
                                    NutritionCalculatorEvent.OnFoodItemSelectedChange(
                                        foodItem,
                                        state.selectedProducts.contains(foodItem)
                                    )
                                )
                            }
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = {
                            onEvent(
                                NutritionCalculatorEvent.OnFoodItemExpandedChange(
                                    foodItem,
                                    state.expandedFoodItems.contains(foodItem)
                                )
                            )
                        }) {
                            if (state.expandedFoodItems.contains(foodItem)) {
                                Icon(
                                    imageVector = Icons.Default.ExpandLess,
                                    contentDescription = stringResource(id = R.string.collapse)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.ExpandMore,
                                    contentDescription = stringResource(id = R.string.expand)
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}