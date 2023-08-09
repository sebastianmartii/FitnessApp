package com.example.fitnessapp.nutrition_calculator_feature.presentation.custom_meal_plan_creator

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.ui.theme.FitnessAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomMealPlanCreatorScreen(
    state: CustomMealPlanCreatorState,
    onNavigateBack: () -> Unit,
    onEvent: (CustomMealPlanCreatorEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (state.isMealPlanNameEditable) {
                        OutlinedTextField(
                            value = state.planName,
                            onValueChange = {
                                onEvent(CustomMealPlanCreatorEvent.OnMealPlanNameChange(it))
                            },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledBorderColor = Color.Transparent,
                                errorBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            textStyle = MaterialTheme.typography.headlineMedium,
                        )
                    } else {
                        Text(
                            text = state.planName,
                            style = MaterialTheme.typography.headlineMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(CustomMealPlanCreatorEvent.OnChangeMealPlanName)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.rename_meal_plan)
                        )
                    }
                    IconButton(onClick = {
                        onEvent(CustomMealPlanCreatorEvent.OnSaveMealPlan)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(id = R.string.save_meal_plan)
                        )
                    }
                    IconButton(onClick = {
                        onEvent(CustomMealPlanCreatorEvent.OnDeleteMealPlan)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_meal_plan)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            state.mealList.onEachIndexed { index, meal ->
                MealCreatorItem(
                    mealName = meal.name,
                    isMealNameEditable = state.isMealNameEditable,
                    onMealNameChange = {
                        onEvent(CustomMealPlanCreatorEvent.OnMealNameChange(it, index))
                    },
                    onChangeMealName = {
                        onEvent(CustomMealPlanCreatorEvent.OnChangeMealName)
                    },
                    onDeleteMeal = {
                        onEvent(CustomMealPlanCreatorEvent.OnDeleteMeal(index))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.large
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = stringResource(id = R.string.add_meal_button)
                )
            }
        }
    }
}

@Composable
private fun MealCreatorItem(
    mealName: String,
    isMealNameEditable: Boolean,
    onMealNameChange: (String) -> Unit,
    onDeleteMeal: () -> Unit,
    onChangeMealName: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        if (isMealNameEditable) {
            OutlinedTextField(
                value = mealName,
                onValueChange = {
                    onMealNameChange(it)
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.headlineSmall
            )
        } else {
            Text(
                text = mealName,
                style = MaterialTheme.typography.headlineSmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }
        IconButton(onClick = onChangeMealName) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(id = R.string.edit_meal)
            )
        }
        IconButton(onClick = onDeleteMeal) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete_meal)
            )
        }
    }
}

@Preview
@Composable
private fun CustomMealPlanCreatorScreenPreview() {
    FitnessAppTheme {
        CustomMealPlanCreatorScreen(state = CustomMealPlanCreatorState(), onNavigateBack = { /*TODO*/ }, onEvent = {})
    }
}