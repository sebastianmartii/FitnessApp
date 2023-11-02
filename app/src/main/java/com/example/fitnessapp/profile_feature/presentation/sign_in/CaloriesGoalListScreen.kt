package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingFlat
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.South
import androidx.compose.material.icons.filled.SouthEast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.profile_feature.data.mappers.toCaloriesString
import com.example.fitnessapp.profile_feature.domain.model.CalculatedCalories
import com.example.fitnessapp.profile_feature.domain.model.TypeOfGoal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriesGoalListScreen(
    calories: String,
    calculatedCaloriesList: List<CalculatedCalories>,
    onCaloriesGoalSelect: (CalculatedCalories) -> Unit,
    calculate: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = true) {
        calculate()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.back_button
                            )
                        )
                    }
                },
                title = {
                    Text(text = "")
                }
            )
        },
        modifier = modifier
            .fillMaxSize()
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            calculatedCaloriesList.onEach { calculatedCaloriesItem ->
                CalculatedCaloriesItem(
                    calories = calories,
                    calculatedCaloriesItem = calculatedCaloriesItem,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            onCaloriesGoalSelect(calculatedCaloriesItem)
                        }
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun CalculatedCaloriesItem(
    calories: String,
    calculatedCaloriesItem: CalculatedCalories,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (calories == calculatedCaloriesItem.calories.toCaloriesString()) {
                    Icons.Default.RadioButtonChecked
                } else {
                    Icons.Default.RadioButtonUnchecked
                },
                contentDescription = stringResource(id = R.string.select_calories)
            )
            Spacer(modifier = Modifier.width(4.dp))
            LeadingCaloriesItemIcon(typeOfGoal = calculatedCaloriesItem.typeOfGoal)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = calculatedCaloriesItem.toString(),
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(id = R.string.calories),
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = calculatedCaloriesItem.calories.toCaloriesString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            when {
                calculatedCaloriesItem.weightLose == null && calculatedCaloriesItem.weightGain != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(id = R.string.weekly),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "+${calculatedCaloriesItem.weightGain}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                calculatedCaloriesItem.weightLose != null && calculatedCaloriesItem.weightGain == null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(id = R.string.weekly),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "-${calculatedCaloriesItem.weightLose}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LeadingCaloriesItemIcon(
    typeOfGoal: TypeOfGoal,
    modifier: Modifier = Modifier
) {
   Icon(
       imageVector = when(typeOfGoal) {
           TypeOfGoal.MAINTAIN_WEIGHT -> {
               Icons.AutoMirrored.Default.TrendingFlat
           }
           TypeOfGoal.MILD_WEIGHT_LOSE -> {
               Icons.AutoMirrored.Default.TrendingDown
           }
           TypeOfGoal.WEIGHT_LOSE -> {
               Icons.Default.SouthEast
           }
           TypeOfGoal.EXTREME_WEIGHT_LOSE -> {
               Icons.Default.South
           }
           TypeOfGoal.MILD_WEIGHT_GAIN -> {
               Icons.AutoMirrored.Default.TrendingUp
           }
           TypeOfGoal.WEIGHT_GAIN -> {
               Icons.Default.NorthEast
           }
           TypeOfGoal.EXTREME_WEIGHT_GAIN -> {
               Icons.Default.North
           }
       },
       contentDescription = stringResource(id = R.string.calories_item_leading_icon),
       modifier = modifier
   )
}