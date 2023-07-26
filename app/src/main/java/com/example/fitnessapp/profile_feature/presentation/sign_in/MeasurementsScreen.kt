package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.ui.theme.FitnessAppTheme

@Composable
fun MeasurementsScreen(
    age: String,
    height: String,
    weight: String,
    onAgeChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onNavigateToActivityLevelAndCaloriesGoalScreen: () -> Unit,
    onGoBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
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
                    contentDescription = stringResource(id = R.string.go_back),
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 48.dp, end = 48.dp, bottom = 128.dp)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.name_provide),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(id = R.string.your_measurements),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = height,
                onValueChange = {
                    onHeightChange(it)
                },
                label = {
                    Text(text = stringResource(id = R.string.height))
                },
                suffix = {
                    Text(text = "cm")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = weight,
                onValueChange = {
                    onWeightChange(it)
                },
                label = {
                    Text(text = stringResource(id = R.string.weight))
                },
                suffix = {
                    Text(text = "kg")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
            Text(
                text = stringResource(id = R.string.and_your_age),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = age,
                onValueChange = {
                    onAgeChange(it)
                },
                label = {
                    Text(text = stringResource(id = R.string.age))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        onNavigateToActivityLevelAndCaloriesGoalScreen()
                    },
                    enabled = Validators.isAgeValid(age) && Validators.isHeightValid(height) && Validators.isWeightValid(weight),
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = stringResource(id = R.string.measurements_done),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MeasurementsScreenPreview() {
    FitnessAppTheme {
        MeasurementsScreen(
            age = "",
            height = "",
            weight = "",
            onAgeChange = {},
            onHeightChange = {},
            onWeightChange = {},
            onNavigateToActivityLevelAndCaloriesGoalScreen = { /*TODO*/ },
            onGoBack = { /*TODO*/ })
    }
}