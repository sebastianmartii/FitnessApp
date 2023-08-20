package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe_search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.fitnessapp.R


@Composable
fun RecipeTopBarActions(
    onSearchActiveChange: () -> Unit
) {
    IconButton(onClick = onSearchActiveChange) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search_food)
        )
    }
}