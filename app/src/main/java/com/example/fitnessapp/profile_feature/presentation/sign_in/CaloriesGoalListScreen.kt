package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.South
import androidx.compose.material.icons.filled.SouthEast
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingFlat
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.profile_feature.data.mappers.toCaloriesString
import com.example.fitnessapp.profile_feature.domain.model.CalculatedCalories
import com.example.fitnessapp.profile_feature.domain.model.TypeOfGoal
import com.example.fitnessapp.ui.theme.FitnessAppTheme

@Composable
fun CaloriesGoalListScreen(
    calories: Double,
    calculatedCaloriesList: List<CalculatedCalories>,
    onCaloriesGoalChosen: (Double) -> Unit,
    calculate: () -> Unit,
    onGoBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = true) {
        calculate()
    }
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = onGoBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = stringResource(id = R.string.introduction_done),
                )
            }
        }
        Spacer(modifier = Modifier.height(128.dp))
        calculatedCaloriesList.onEach {
            CalculatedCaloriesItem(
                calories = calories,
                calculatedCaloriesItem = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        onCaloriesGoalChosen(it.calories)
                    }
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
private fun CalculatedCaloriesItem(
    calories: Double,
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
                imageVector = if (calories == calculatedCaloriesItem.calories) {
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
               Icons.Default.TrendingFlat
           }
           TypeOfGoal.MILD_WEIGHT_LOSE -> {
               Icons.Default.TrendingDown
           }
           TypeOfGoal.WEIGHT_LOSE -> {
               Icons.Default.SouthEast
           }
           TypeOfGoal.EXTREME_WEIGHT_LOSE -> {
               Icons.Default.South
           }
           TypeOfGoal.MILD_WEIGHT_GAIN -> {
               Icons.Default.TrendingUp
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

@Preview
@Composable
private fun CaloriesGoalListScreenPreview() {
    FitnessAppTheme {
        CaloriesGoalListScreen(
            calories = 0.0,
            calculatedCaloriesList = listOf(
                CalculatedCalories(TypeOfGoal.MAINTAIN_WEIGHT, 2106.0),
                CalculatedCalories(TypeOfGoal.MILD_WEIGHT_LOSE, 1796.0, weightLose = "0.25 kg"),
                CalculatedCalories(TypeOfGoal.WEIGHT_LOSE, 1546.0, weightLose = "0.5 kg"),
                CalculatedCalories(TypeOfGoal.EXTREME_WEIGHT_LOSE, 1046.0, weightLose = "1 kg"),
                CalculatedCalories(TypeOfGoal.MILD_WEIGHT_GAIN, 2296.0, weightGain = "0.25 kg"),
                CalculatedCalories(TypeOfGoal.WEIGHT_GAIN, 2546.0, weightGain = "0.5 kg"),
                CalculatedCalories(TypeOfGoal.EXTREME_WEIGHT_GAIN, 3046.0, weightGain = "1 kg"),
            ),
            onCaloriesGoalChosen = {
            },
            calculate = {},
            onGoBack = { /*TODO*/ })
    }
}