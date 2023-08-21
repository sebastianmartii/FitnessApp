package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.food_nutrition_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.R
import com.example.fitnessapp.core.util.capitalizeEachWord

@Composable
fun FoodNutritionSearchScreen(
    state: FoodNutritionSearchState,
    onEvent: (FoodNutritionSearchEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onKeyboardHide: () -> Unit,
    onNavigateToFoodItemCreator: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onNavigateBack()
                    onEvent(FoodNutritionSearchEvent.OnFoodItemsSave)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(id = R.string.save_button)
                )
                Text(text = stringResource(id = R.string.save_text))
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = { query ->
                    onEvent(FoodNutritionSearchEvent.OnQueryChange(query))
                },
                leadingIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = onNavigateToFoodItemCreator) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_food_item)
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.food_nutrition_search_placeholder),
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .alpha(0.7f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onEvent(FoodNutritionSearchEvent.OnNutritionSearch)
                        onKeyboardHide()
                    }
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            state.foodItems.onEach { foodItem ->
                ListItem(
                    tonalElevation = if (foodItem.isSelected) 4.dp else 0.dp,
                    headlineContent = {
                        Text(
                            text = foodItem.name.capitalizeEachWord(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp
                            )
                        )
                    },
                    supportingContent = {
                        Text(
                            text = stringResource(
                                R.string.food_item_supporting_text_expanded,
                                foodItem.servingSize,
                                foodItem.calories,
                                foodItem.carbs,
                                foodItem.totalFat,
                                foodItem.protein,
                                foodItem.saturatedFat,
                                foodItem.sugar,
                                foodItem.fiber
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    modifier = Modifier.clickable {
                        onEvent(FoodNutritionSearchEvent.OnFoodItemSelect(foodItem))
                    }
                )
            }
        }
    }
}

