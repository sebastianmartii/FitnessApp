package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemCreatorScreen(
    state: FoodItemCreatorState,
    onEvent: (FoodItemCreatorEvent) -> Unit,
    snackbarFlow: Flow<FoodItemCreatorViewModel.UiEvent>,
    snackbarHostState: SnackbarHostState,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = true) {
        snackbarFlow.collectLatest { event ->
            when(event) {
                FoodItemCreatorViewModel.UiEvent.NavigateBack -> onNavigateBack()
                is FoodItemCreatorViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.name)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                onEvent(FoodItemCreatorEvent.OnFoodItemCreated(FoodItemCreatorValidators.isFoodItemValid(state)))
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.add_food_item)
                )
                Text(text = stringResource(id = R.string.save_text))
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            IconTextField(
                componentValue = state.name,
                label = stringResource(id = R.string.name_label),
                leadingIcon = Icons.Default.RestaurantMenu,
                leadingIconDescription = stringResource(id = R.string.food_name_desc),
                imeAction = ImeAction.Next,
                isError = !FoodItemCreatorValidators.isFoodNameValid(state.name) && state.name.isNotEmpty(),
                errorText = stringResource(id = R.string.name_error_text),
                onComponentValueChange = {
                    onEvent(FoodItemCreatorEvent.OnNameChange(it))
                },
                onFocusMove = onFocusMove,
                onKeyboardHide = onKeyboardHide
            )
            OutlinedTextField(
                value = state.servingSize,
                onValueChange = {
                    onEvent(FoodItemCreatorEvent.OnServingSizeChange(it))
                },
                label = {
                    Text(text = stringResource(id = R.string.serving_label))
                },
                suffix = {
                    Text(text = stringResource(id = R.string.suffix_grams))
                },
                isError = !FoodItemCreatorValidators.isServingSizeValid(state.servingSize) && state.servingSize.isNotEmpty(),
                supportingText = {
                    if (!FoodItemCreatorValidators.isServingSizeValid(state.servingSize) && state.servingSize.isNotEmpty()) {
                        Text(text = stringResource(id = R.string.serving_size_error_text))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        onFocusMove()
                    },
                    onDone = {
                        onKeyboardHide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth(0.850f)
                    .padding(end = 16.dp)
            )
            IconTextField(
                componentValue = state.calories,
                label = stringResource(id = R.string.calories_label),
                leadingIcon = Icons.Default.Scale,
                leadingIconDescription = stringResource(id = R.string.calories_label),
                imeAction = ImeAction.Next,
                suffix = stringResource(id = R.string.suffix_calories),
                isError = !FoodItemCreatorValidators.areCaloriesValid(state.calories) && state.calories.isNotEmpty(),
                errorText = stringResource(id = R.string.serving_calories_error_text),
                isKeyboardDecimal = true,
                onComponentValueChange = {
                    onEvent(FoodItemCreatorEvent.OnCaloriesChange(it))
                },
                onFocusMove = onFocusMove,
                onKeyboardHide = onKeyboardHide
            )
            OutlinedTextField(
                value = state.carbs,
                onValueChange = {
                    onEvent(FoodItemCreatorEvent.OnCarbsChange(it))
                },
                label = {
                    Text(text = stringResource(id = R.string.carbs_label))
                },
                suffix = {
                    Text(text = stringResource(id = R.string.suffix_grams))
                },
                isError = !FoodItemCreatorValidators.areCarbsValid(state.carbs) && state.carbs.isNotEmpty(),
                supportingText = {
                    if (!FoodItemCreatorValidators.areCarbsValid(state.carbs) && state.carbs.isNotEmpty()) {
                        Text(text = stringResource(id = R.string.carbs_error_text))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        onFocusMove()
                    },
                    onDone = {
                        onKeyboardHide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth(0.850f)
                    .padding(end = 16.dp)
            )
            OutlinedTextField(
                value = state.protein,
                onValueChange = {
                    onEvent(FoodItemCreatorEvent.OnProteinChange(it))
                },
                label = {
                    Text(text = stringResource(id = R.string.protein_label))
                },
                suffix = {
                    Text(text = stringResource(id = R.string.suffix_grams))
                },
                isError = !FoodItemCreatorValidators.isProteinValid(state.protein) && state.protein.isNotEmpty(),
                supportingText = {
                    if (!FoodItemCreatorValidators.isProteinValid(state.protein) && state.protein.isNotEmpty()) {
                        Text(text = stringResource(id = R.string.protein_error_text))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        onFocusMove()
                    },
                    onDone = {
                        onKeyboardHide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth(0.850f)
                    .padding(end = 16.dp)
            )
            DoubleTextFieldRow(
                firstValue = state.totalFat,
                secondValue = state.saturatedFat,
                firstLabel = stringResource(id = R.string.total_fat_label),
                secondLabel = stringResource(id = R.string.saturated_fat_label),
                isFirstError = !FoodItemCreatorValidators.isTotalFatValid(state.totalFat) && state.totalFat.isNotEmpty(),
                isSecondError = !FoodItemCreatorValidators.isSaturatedFatValid(state.saturatedFat) && state.saturatedFat.isNotEmpty(),
                firstErrorText = stringResource(id = R.string.fat_error_text),
                secondErrorText = stringResource(id = R.string.saturated_fat_error_text),
                onFirstValueChange = {
                    onEvent(FoodItemCreatorEvent.OnTotalFatChange(it))
                },
                onSecondValueChange = {
                    onEvent(FoodItemCreatorEvent.OnSaturatedFatChange(it))
                },
                onFocusMove = onFocusMove,
                onKeyboardHide = onKeyboardHide,
                modifier = Modifier.fillMaxWidth(0.850f)
            )
            DoubleTextFieldRow(
                firstValue = state.fiber,
                secondValue = state.sugar,
                firstLabel = stringResource(id = R.string.fiber_label),
                secondLabel = stringResource(id = R.string.sugar_label),
                secondTextFieldImeAction = ImeAction.Done,
                isFirstError = !FoodItemCreatorValidators.isFiberValid(state.fiber) && state.fiber.isNotEmpty(),
                isSecondError = !FoodItemCreatorValidators.isSugarValid(state.sugar) && state.sugar.isNotEmpty(),
                firstErrorText = stringResource(id = R.string.fiber_error_text),
                secondErrorText = stringResource(id = R.string.sugar_error_text),
                onFirstValueChange = {
                    onEvent(FoodItemCreatorEvent.OnFiberChange(it))
                },
                onSecondValueChange = {
                    onEvent(FoodItemCreatorEvent.OnSugarChange(it))
                },
                onFocusMove = onFocusMove,
                onKeyboardHide = onKeyboardHide,
                modifier = Modifier.fillMaxWidth(0.850f)
            )
        }
    }
}

@Composable
private fun IconTextField(
    componentValue: String,
    label: String,
    leadingIcon: ImageVector,
    leadingIconDescription: String?,
    imeAction: ImeAction,
    isError: Boolean,
    errorText: String,
    onComponentValueChange: (String) -> Unit,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit,
    modifier: Modifier = Modifier,
    suffix: String? = null,
    isKeyboardDecimal: Boolean = false
) {
    Row(
        modifier = modifier.padding(end = 16.dp)
    ) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = leadingIconDescription,
            modifier = Modifier.weight(0.150f)
        )
        OutlinedTextField(
            value = componentValue,
            onValueChange = {
                onComponentValueChange(it)
            },
            label = {
                Text(text = label)
            },
            suffix = {
                if (!suffix.isNullOrBlank()) {
                    Text(text = suffix)
                }
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = errorText)
                }
            },
            keyboardOptions = if (isKeyboardDecimal) {
                KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = imeAction
                )
            } else {
                KeyboardOptions(
                    imeAction = imeAction
                )
            },
            keyboardActions = KeyboardActions(
                onNext = {
                    onFocusMove()
                },
                onDone = {
                    onKeyboardHide()
                }
            ),
            modifier = Modifier.weight(0.850f)
        )
    }
}

@Composable
private fun DoubleTextFieldRow(
    firstValue: String,
    secondValue: String,
    firstLabel: String,
    secondLabel: String,
    isFirstError: Boolean,
    isSecondError: Boolean,
    firstErrorText: String,
    secondErrorText: String,
    onFirstValueChange: (String) -> Unit,
    onSecondValueChange: (String) -> Unit,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit,
    modifier: Modifier = Modifier,
    secondTextFieldImeAction: ImeAction = ImeAction.Next
) {
    Row(
        modifier = modifier.padding(end = 16.dp)
    ) {
        OutlinedTextField(
            value = firstValue,
            onValueChange = {
                onFirstValueChange(it)
            },
            label = {
                Text(text = firstLabel)
            },
            suffix = {
                Text(text = stringResource(id = R.string.suffix_grams))
            },
            isError = isFirstError,
            supportingText = {
                if (isFirstError) {
                    Text(text = firstErrorText)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onFocusMove()
                },
                onDone = {
                    onKeyboardHide()
                }
            ),
            modifier = Modifier.weight(0.5f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            value = secondValue,
            onValueChange = {
                onSecondValueChange(it)
            },
            label = {
                Text(text = secondLabel)
            },
            suffix = {
                Text(text = stringResource(id = R.string.suffix_grams))
            },
            isError = isSecondError,
            supportingText = {
                if (isSecondError) {
                    Text(text = secondErrorText)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = secondTextFieldImeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onFocusMove()
                },
                onDone = {
                    onKeyboardHide()
                }
            ),
            modifier = Modifier.weight(0.5f)
        )
    }
}