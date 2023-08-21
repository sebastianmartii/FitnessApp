package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.R
import com.example.fitnessapp.core.util.capitalizeEachWord

@Composable
fun NutritionCalculatorScreen(
    state: NutritionCalculatorState,
    onEvent: (NutritionCalculatorEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(state.cachedProducts) { foodItem ->
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
                trailingContent = {
                    IconButton(onClick = {
                        onEvent(NutritionCalculatorEvent.OnFoodItemDelete(foodItem))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_food_item)
                        )
                    }
                },
                modifier = Modifier.clickable {
                    onEvent(NutritionCalculatorEvent.OnFoodItemSelectedChange(foodItem))
                }
            )
        }
    }
}