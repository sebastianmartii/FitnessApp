package com.example.fitnessapp.profile_feature.presentation.sign_in

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    weightGainGoals: List<CalculatedCalories>,
    weightLoseGoals: List<CalculatedCalories>,
    maintainWeightGoal: CalculatedCalories,
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
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = R.string.pick_calories_goal),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
            CalculatedCaloriesItem(typeOfGoal = maintainWeightGoal.toString(), calories = maintainWeightGoal.calories)
            Text(
                text = stringResource(id = R.string.weight_lose_goals),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
            ) {
                weightLoseGoals.onEach { goal ->
                    CalculatedCaloriesItem(typeOfGoal = goal.toString(), calories = goal.calories, weightLose = goal.weightLose)
                }
            }
            Text(
                text = stringResource(id = R.string.weight_gain_goals),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
            ) {
                weightGainGoals.onEach { goal ->
                    CalculatedCaloriesItem(typeOfGoal = goal.toString(), calories = goal.calories, weightGain = goal.weightGain)
                }
            }
        }
    }
}

@Composable
private fun CalculatedCaloriesItem(
    typeOfGoal: String,
    calories: Double,
    modifier: Modifier = Modifier,
    weightLose: String? = null,
    weightGain: String? = null
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        modifier = modifier
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(8.dp)
                .width(160.dp)
                .height(110.dp)
        ) {
            Text(
                text = typeOfGoal,
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.calories),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = calories.toCaloriesString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Column {
                    when {
                        weightLose == null && weightGain != null -> {
                            Text(
                                text = stringResource(id = R.string.weekly),
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                text = "+$weightGain",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        weightLose != null && weightGain == null -> {
                            Text(
                                text = stringResource(id = R.string.weekly),
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                text = "-$weightLose",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CaloriesGoalListScreenPreview() {
    FitnessAppTheme {
        CaloriesGoalListScreen(
            weightLoseGoals = listOf(
                CalculatedCalories(TypeOfGoal.MILD_WEIGHT_LOSE, 1796.0, weightLose = "0.25 kg"),
                CalculatedCalories(TypeOfGoal.WEIGHT_LOSE, 1546.0, weightLose = "0.5 kg"),
                CalculatedCalories(TypeOfGoal.EXTREME_WEIGHT_LOSE, 1046.0, weightLose = "1 kg"),
            ),
            weightGainGoals = listOf(
                CalculatedCalories(TypeOfGoal.MILD_WEIGHT_GAIN, 2296.0, weightGain = "0.25 kg"),
                CalculatedCalories(TypeOfGoal.WEIGHT_GAIN, 2546.0, weightGain = "0.5 kg"),
                CalculatedCalories(TypeOfGoal.EXTREME_WEIGHT_GAIN, 3046.0, weightGain = "1 kg"),
            ),
            maintainWeightGoal = CalculatedCalories(TypeOfGoal.MAINTAIN_WEIGHT, 2106.0),
            onCaloriesGoalChosen = {
            },
            calculate = {},
            onGoBack = { /*TODO*/ })
    }
}