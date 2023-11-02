package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementsScreen(
    age: String,
    height: String,
    weight: String,
    onEvent: (SignInEvent) -> Unit,
    onNavigateToActivityLevelAndCaloriesGoalScreen: () -> Unit,
    onNavigateBack: () -> Unit,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                },
                title = {
                    Text(text = "")
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .wrapContentSize()
        ) {
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
                        onEvent(SignInEvent.OnHeightChange(it))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.height))
                    },
                    suffix = {
                        Text(text = "cm")
                    },
                    isError = !Validators.isHeightValid(height) && height.isNotEmpty(),
                    supportingText = {
                        if (!Validators.isHeightValid(height) && height.isNotEmpty()) {
                            Text(
                                text = stringResource(id = R.string.height_error_text),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            onFocusMove()
                        }
                    ),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = weight,
                    onValueChange = {
                        onEvent(SignInEvent.OnWeightChange(it))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.weight))
                    },
                    suffix = {
                        Text(text = "kg")
                    },
                    isError = !Validators.isWeightValid(weight) && weight.isNotEmpty(),
                    supportingText = {
                        if (!Validators.isWeightValid(weight) && weight.isNotEmpty()) {
                            Text(
                                text = stringResource(id = R.string.weight_error_text),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            onFocusMove()
                        }
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
                        onEvent(SignInEvent.OnAgeChange(it))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.age))
                    },
                    isError = !Validators.isAgeValid(age) && age.isNotEmpty(),
                    supportingText = {
                        if (!Validators.isAgeValid(age) && age.isNotEmpty()) {
                            Text(
                                text = stringResource(id = R.string.age_error_text),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onKeyboardHide()
                        }
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
                        enabled = Validators.isAgeValid(age) && Validators.isHeightValid(height) && Validators.isWeightValid(
                            weight
                        ),
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
}