package com.example.fitnessapp.history_feature.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.activities_feature.data.mappers.toDurationString
import com.example.fitnessapp.core.util.capitalizeEachWord
import com.example.fitnessapp.core.util.toDuration
import com.example.fitnessapp.daily_overview_feature.domain.model.MealDetails
import com.example.fitnessapp.history_feature.data.mappers.mapMealToMealDetails
import com.example.fitnessapp.history_feature.domain.model.Activity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryDetailsScreen(
    state: HistoryState,
    pagerState: PagerState,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "${pagerState.currentPage + 1} ${state.currentMonth}")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        VerticalPager(
            state = pagerState,
            pageContent = {page ->
                HistoryDetailsPageContent(
                    performedActivities = state.currentMonthActivities.filter { it.day == page },
                    nutrition = mapMealToMealDetails(state.currentMonthNutrition, page)
                )
            },
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@Composable
private fun HistoryDetailsPageContent(
    performedActivities: List<Activity>,
    nutrition: List<MealDetails>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        nutrition.onEach { mealDetails ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = mealDetails.meal,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.history_meal_calories, mealDetails.calories),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.ingredients_headline),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
            )
            Text(
                text = mealDetails.ingredients.joinToString(separator = ", "),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier
                    .padding(horizontal = 32.dp)

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.daily_nutrition_nutritional_value_headline),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
            )
            Text(
                text = stringResource(
                    id = R.string.daily_nutrition_details_nutritional_value,
                    mealDetails.servingSize,
                    mealDetails.carbs,
                    mealDetails.fat,
                    mealDetails.protein
                ),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = performedActivities
                .joinToString(separator = ", ") {
                    it.name
                }
                .capitalizeEachWord(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 32.dp)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.burned_calories_label),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontStyle = FontStyle.Italic
                    )
                )
                Text(
                    text = performedActivities.sumOf { it.burnedCalories.toInt() }.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = stringResource(id = R.string.duration_label),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontStyle = FontStyle.Italic
                    )
                )
                Text(
                    text = performedActivities.sumOf { it.performedActivitiesDuration.toDuration() }.toDurationString(true),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}