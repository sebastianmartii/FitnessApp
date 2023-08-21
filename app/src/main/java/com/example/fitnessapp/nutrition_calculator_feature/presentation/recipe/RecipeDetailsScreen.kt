package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fitnessapp.R
import com.example.fitnessapp.core.util.DOT_SEPARATOR_TEXT
import com.example.fitnessapp.core.util.DotSeparator
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe
import com.example.fitnessapp.ui.theme.FitnessAppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RecipeDetailsScreen(
    recipe: Recipe,
    isRecipeSaved: Boolean,
    onNavigateBack: () -> Unit,
    onIsRecipeSavedChange: (recipe: Recipe, isSaved: Boolean) -> Unit,
    onExternalUrlOpen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                },
                title = {
                    Text(text = recipe.label)
                },
                actions = {
                    IconButton(onClick = {
                        onIsRecipeSavedChange(recipe, isRecipeSaved)
                    }) {
                        Icon(
                            imageVector = if (isRecipeSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = stringResource(id = R.string.save_text)
                        )
                    }
                    IconButton(onClick = {
                        onExternalUrlOpen(recipe.externalUrl)
                    }) {
                        Icon(
                            imageVector = Icons.Default.OpenInBrowser,
                            contentDescription = stringResource(id = R.string.open_browser)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = recipe.bigImage,
                contentDescription = recipe.label,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(horizontal = 32.dp, vertical = 16.dp)
                    .aspectRatio(1 / 1f)
                    .clip(MaterialTheme.shapes.medium)
            )
            if (recipe.dietLabels.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier
                        .padding(start = 48.dp, end = 48.dp, bottom = 48.dp, top = 32.dp)
                        .fillMaxWidth()
                ) {
                    recipe.dietLabels.onEach { dietLabel ->
                        Text(
                            text = "$DOT_SEPARATOR_TEXT $dietLabel "

                        )
                    }
                }
            }
            Text(
                text = stringResource(
                    id = R.string.food_item_supporting_text_expanded,
                    recipe.servingSize,
                    recipe.calories,
                    recipe.carbs,
                    recipe.fat,
                    recipe.protein,
                    recipe.saturatedFat,
                    recipe.sugar,
                    recipe.fiber
                ),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(start = 48.dp, end = 48.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(id = R.string.ingredients_headline),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 16.dp)
            )
            recipe.ingredients.onEach { ingredient ->
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 48.dp, end = 16.dp, bottom = 8.dp)
                        .fillMaxWidth()
                ) {
                    DotSeparator()
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = ingredient,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(256.dp))
        }
    }
}

@Preview
@Composable
private fun RecipeDetailsScreenPreview() {
    FitnessAppTheme {
        RecipeDetailsScreen(
            recipe = Recipe(
                label= "Protein Oats",
                smallImage = "",
                bigImage = "",
                dietLabels = listOf(
                    "low calorie",
                    "high protein",
                    "diary free",
                    "high fiber",
                    "low fat",
                    "low sugar",
                    "healthy",
                    "sweet",
                    "filling",
                ),
                servingSize = 350.0,
                calories = 700.0,
                carbs = 50.0,
                protein = 40.0,
                fat = 4.0,
                saturatedFat = 0.0,
                fiber = 12.0,
                sugar = 5.0,
                externalUrl = "",
                ingredients = listOf(
                    "200ml milk",
                    "45g rolled oats",
                    "10g chia seeds",
                    "30g vanilla protein powder",
                    "30g of different nuts",
                    "15g cereals of choice",
                    "1 cut banana",
                    "10g peanut butter",
                    "3g ground cinnamon",
                ),
            ),
            isRecipeSaved = false,
            onIsRecipeSavedChange = { recipe, isSaved ->

            },
            onNavigateBack = {},
            onExternalUrlOpen = {}
        )
    }
}