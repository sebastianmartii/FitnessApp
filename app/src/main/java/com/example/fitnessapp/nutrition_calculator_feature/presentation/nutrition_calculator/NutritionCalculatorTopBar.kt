package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitnessapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionCalculatorTopBar(
    onDrawerStateChange: () -> Unit,
    onNavigateToFoodItemCreator: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onDrawerStateChange) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.navigation_drawer_icon)
                )
            }
        },
        title = {},
        actions = {
            IconButton(onClick = onNavigateToSearchScreen) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_food)
                )
            }
            IconButton(onClick = onNavigateToFoodItemCreator) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_food_item)
                )
            }
        },
        modifier = modifier
    )
}