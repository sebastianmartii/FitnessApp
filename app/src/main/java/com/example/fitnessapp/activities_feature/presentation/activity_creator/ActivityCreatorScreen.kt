package com.example.fitnessapp.activities_feature.presentation.activity_creator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.core.util.duration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityCreatorScreen(
    state: ActivityCreatorState,
    snackbarFlow: Flow<ActivityCreatorViewModel.UiEvent>,
    snackbarHostState: SnackbarHostState,
    onEvent: (ActivityCreatorEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        snackbarFlow.collectLatest { event ->
            when(event) {
                ActivityCreatorViewModel.UiEvent.NavigateBack -> {
                    onNavigateBack()
                }
                is ActivityCreatorViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                },
                title = {
                    Text(text = state.name)
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onEvent(ActivityCreatorEvent.OnActivitySave(
                        state.name,
                        state.description,
                        duration(state.minutes, state.seconds),
                        state.burnedCalories,
                        ActivityCreatorValidators.isCreatedActivityValid(state.name, state.burnedCalories, state.minutes, state.seconds)
                    ))
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = stringResource(id = R.string.save_button)
                    )
                    Text(text = stringResource(id = R.string.save_text))
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = stringResource(id = R.string.activity_name_headline_icon),
                    modifier = Modifier
                        .weight(0.155f)
                )
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { name ->
                        onEvent(ActivityCreatorEvent.OnActivityNameChange(name))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.name_label))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.activity_name_placeholder),
                            modifier = Modifier.alpha(0.8f)
                        )
                    },
                    isError = !ActivityCreatorValidators.isNameValid(state.name) && state.name.isNotEmpty(),
                    supportingText = {
                        if (!ActivityCreatorValidators.isNameValid(state.name) && state.name.isNotEmpty()) {
                            Text(text = stringResource(id = R.string.activity_name_error_text))
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            onFocusMove()
                        }
                    ),
                    modifier = Modifier.weight(0.845f)
                )
            }
            OutlinedTextField(
                value = state.description,
                onValueChange = { description ->
                    onEvent(ActivityCreatorEvent.OnActivityDescriptionChange(description))
                },
                label = {
                    Text(text = stringResource(id = R.string.description_label))
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.activity_description_placeholder),
                        modifier = Modifier.alpha(0.8f)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        onFocusMove()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth(0.850f)
                    .padding(end = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Sports,
                    contentDescription = stringResource(id = R.string.activity_details_headline_icon),
                    modifier = Modifier.weight(0.155f)
                )
                OutlinedTextField(
                    value = state.burnedCalories,
                    onValueChange = { calories ->
                        onEvent(ActivityCreatorEvent.OnActivityBurnedCaloriesChange(calories))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.burned_calories_label))
                    },
                    suffix = {
                        Text(text = stringResource(id = R.string.suffix_calories))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.activity_calories_burned_placeholder),
                            modifier = Modifier.alpha(0.8f)
                        )
                    },
                    isError = !ActivityCreatorValidators.areCaloriesBurnedValid(state.burnedCalories) && state.burnedCalories.isNotEmpty(),
                    supportingText = {
                        if (!ActivityCreatorValidators.areCaloriesBurnedValid(state.burnedCalories) && state.burnedCalories.isNotEmpty()) {
                            Text(text = stringResource(id = R.string.activity_calories_burned_error_text))
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
                    modifier = Modifier.weight(0.845f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.850f)
                    .padding(end = 16.dp)
            ) {
                OutlinedTextField(
                    value = state.minutes,
                    onValueChange = { minutes ->
                        onEvent(ActivityCreatorEvent.OnActivityMinutesChange(minutes))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.minutes_label))
                    },
                    suffix = {
                        Text(text = stringResource(id = R.string.suffix_minutes))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.minutes_seconds_placeholder),
                            modifier = Modifier.alpha(0.8f)
                        )
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
                    modifier = Modifier.weight(0.5f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = state.seconds,
                    onValueChange = { seconds ->
                        onEvent(ActivityCreatorEvent.OnActivitySecondsChange(seconds))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.seconds_label))
                    },
                    suffix = {
                        Text(text = stringResource(id = R.string.suffix_seconds))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.minutes_seconds_placeholder),
                            modifier = Modifier.alpha(0.8f)
                        )
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
                    modifier = Modifier.weight(0.5f)
                )
            }
            if (!ActivityCreatorValidators.isDurationValid(state.minutes, state.seconds) && state.minutes.isNotEmpty() && state.seconds.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.activity_duration_error_text),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.850f)
                        .padding(start = 16.dp, end = 32.dp, top = 4.dp)
                )
            }
        }
    }
}