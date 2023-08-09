package com.example.fitnessapp.nutrition_calculator_feature.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MealPlanScreen(
    mealPlanTypeList: List<MealPlanType>,
    onCustomMealPLan: () -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .padding(top = 16.dp)
    ) {
        mealPlanTypeList.onEach {
            if (it == MealPlanType.CUSTOM_PLAN) {
                Card(
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 200.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.custom_text),
                            style = MaterialTheme.typography.titleMedium
                        )
                        IconButton(onClick = onCustomMealPLan) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = stringResource(id = R.string.add_custom_meal_plan)
                            )
                        }
                    }
                }
            } else {
                val mealPlanType = when(it) {
                    MealPlanType.FIVE_MEALS -> R.string.five_meals_plan
                    MealPlanType.FOUR_MEALS -> R.string.four_meals_plan
                    MealPlanType.THREE_MEALS -> R.string.three_meals_plan
                    else -> R.string.five_meals_plan
                }
                MealPlanCard(
                    mealsList = splitStringToList(stringResource = mealPlanType),
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 200.dp)
                )
            }
        }
    }
}

@Composable
private fun MealPlanCard(
    mealsList: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
        ) {
            mealsList.onEachIndexed { index, meal ->
                Text(
                    text = "${index + 1}. $meal",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun splitStringToList(stringResource: Int): List<String> {
    val context = LocalContext.current
    val itemList = remember(stringResource) {
        context.resources.getStringArray(stringResource).toList()
    }
    return itemList
}