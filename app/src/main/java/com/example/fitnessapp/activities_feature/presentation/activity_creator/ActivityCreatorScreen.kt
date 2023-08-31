package com.example.fitnessapp.activities_feature.presentation.activity_creator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.core.util.duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityCreatorScreen(
    state: ActivityCreatorState,
    onEvent: (ActivityCreatorEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit
) {
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
                    onEvent(ActivityCreatorEvent.OnActivitySave(state.name, state.description, duration(state.minutes, state.seconds), state.burnedCalories))
                    onNavigateBack()
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = stringResource(id = R.string.save_button)
                    )
                    Text(text = stringResource(id = R.string.save_text))
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { name ->
                    onEvent(ActivityCreatorEvent.OnActivityNameChange(name))
                },
                label = {
                    Text(text = stringResource(id = R.string.name_label))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        onFocusMove()
                    }
                ),
                modifier = Modifier.padding(16.dp)
            )
            OutlinedTextField(
                value = state.description,
                onValueChange = { description ->
                    onEvent(ActivityCreatorEvent.OnActivityDescriptionChange(description))
                },
                label = {
                    Text(text = stringResource(id = R.string.description_label))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        onFocusMove()
                    }
                ),
                modifier = Modifier.padding(16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.burnedCalories,
                    onValueChange = { calories ->
                        onEvent(ActivityCreatorEvent.OnActivityBurnedCaloriesChange(calories))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.burned_calories_label))
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
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = state.minutes,
                    onValueChange = { minutes ->
                        onEvent(ActivityCreatorEvent.OnActivityMinutesChange(minutes))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.minutes_label))
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
                    modifier = Modifier.weight(0.2f)
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onKeyboardHide()
                        }
                    ),
                    modifier = Modifier.weight(0.2f)
                )
            }
        }
    }
}