package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fitnessapp.core.util.DotSeparator


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeSearchScreen(
    state: RecipeSearchState,
    onNavigateToRecipeDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        if (state.isLoading) {
            item {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
        itemsIndexed(state.recipes) { index, recipe ->
            ListItem(
                headlineContent = {
                    Text(text = recipe.label)
                },
                leadingContent = {
                    AsyncImage(
                        model = recipe.bigImage,
                        contentDescription = recipe.label,
                        modifier = Modifier
                            .height(80.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                    )
                },
                supportingContent = {
                    FlowRow {
                        Text(text = "${recipe.calories.toInt()} kcal; ")
                        recipe.dietLabels.onEach {
                            DotSeparator()
                            Text(text = it)
                        }
                    }
                },
                modifier = Modifier.clickable {
                    onNavigateToRecipeDetails(index)
                }
            )
        }
    }
}