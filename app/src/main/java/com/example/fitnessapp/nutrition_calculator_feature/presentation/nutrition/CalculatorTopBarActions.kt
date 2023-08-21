package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.fitnessapp.R


@Composable
fun CalculatorTopBarActions(
    onNavigateToFoodItemCreator: () -> Unit,
    onNavigateToSearchScreen: () -> Unit
) {
    Row {
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
    }
}