package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.core.util.activityLevels
import com.example.fitnessapp.profile_feature.data.mappers.toActivityLevelString
import com.example.fitnessapp.profile_feature.data.mappers.toActivityLevelToolTipHint
import com.example.fitnessapp.ui.theme.FitnessAppTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ActivityLevelAndCaloriesGoalScreen(
    activityLevel: ActivityLevel,
    caloriesGoal: String,
    onEvent: (SignInEvent) -> Unit,
    onNavigateToOverviewScreen: () -> Unit,
    onNavigateToCalculatedCaloriesScreen: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
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
    ) { paddingValues ->
        Box(
            modifier = modifier
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
                    text = stringResource(id = R.string.how_active),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(id = R.string.are_you),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(id = R.string.hint_icon),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.activity_level_selection_hint),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                FlowRow(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    activityLevels.onEach {
                        TooltipBox(
                            positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                            tooltip = {
                                RichTooltip(
                                    title = {
                                        Text(text = it.toActivityLevelString())
                                    },
                                    text = {
                                        Text(text = it.toActivityLevelToolTipHint())
                                    }
                                )
                            },
                            state = rememberTooltipState()
                        ) {
                            FilterChip(
                                selected = activityLevel == it,
                                onClick = {
                                    onEvent(SignInEvent.OnActivityLevelChange(it))
                                },
                                label = {
                                    Text(text = it.toActivityLevelString())
                                },
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(id = R.string.whats_your),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = stringResource(id = R.string.calories_goal),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = caloriesGoal,
                    onValueChange = {
                        onEvent(SignInEvent.OnCaloriesGoalChange(it))
                    },
                    trailingIcon = {
                        TextButton(onClick = onNavigateToCalculatedCaloriesScreen) {
                            Text(text = stringResource(id = R.string.calculate))
                        }
                    },
                    isError = !Validators.areCaloriesValid(caloriesGoal) && caloriesGoal.isNotEmpty(),
                    supportingText = {
                        if (!Validators.areCaloriesValid(caloriesGoal) && caloriesGoal.isNotEmpty()) {
                            Text(text = stringResource(id = R.string.calories_error_text))
                        }
                    },
                    suffix = {
                        Text(text = "kcal")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = stringResource(id = R.string.calculate_hint),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth()
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            onEvent(SignInEvent.OnSignInComplete)
                            onNavigateToOverviewScreen()
                        },
                        enabled = Validators.areCaloriesValid(caloriesGoal) && activityLevel != ActivityLevel.LEVEL_0,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = stringResource(id = R.string.activity_calories_done),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ActivityLevelAndCaloriesGoalScreenPreview() {
    FitnessAppTheme {
        ActivityLevelAndCaloriesGoalScreen(
            activityLevel = ActivityLevel.LEVEL_4,
            caloriesGoal = "2300",
            onEvent = {},
            onNavigateToOverviewScreen = {},
            onNavigateToCalculatedCaloriesScreen = {},
            onNavigateBack = {})
    }
}