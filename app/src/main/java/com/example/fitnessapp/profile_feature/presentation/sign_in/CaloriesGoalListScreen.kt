package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.ui.theme.FitnessAppTheme

@Composable
fun CaloriesGoalListScreen(
    calculatedCalories: List<CalculatedCalories>,
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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
            ) {
                items(calculatedCalories) {
                    CalculatedCaloriesItem(
                        typeOfGoal = it.toString(),
                        calories = it.calories,
                        weightLose = it.weightLose,
                        weightGain = it.weightGain,
                        modifier = Modifier.clickable {
                            onCaloriesGoalChosen(it.calories)
                        }
                    )
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
    Surface(
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = modifier
            .padding(horizontal = 32.dp, vertical = 4.dp)
            .height(120.dp)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = typeOfGoal,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(text = calories.toString(), style = MaterialTheme.typography.headlineMedium)
                when {
                    weightLose != null && weightGain == null -> {
                        Text(
                            text = "-$weightLose",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    weightLose == null && weightGain != null -> {
                        Text(
                            text = "+$weightGain",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.error
                        )
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
            calculatedCalories = listOf(
                CalculatedCalories(TypeOfGoal.MAINTAIN_WEIGHT, 2046.0),
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