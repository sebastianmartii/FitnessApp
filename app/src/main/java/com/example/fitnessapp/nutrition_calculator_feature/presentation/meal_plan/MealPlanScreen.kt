package com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanScreen(
    state: MealPlanState,
    scaffoldState: BottomSheetScaffoldState,
    eventFlow: Flow<MealPlanViewModel.UiEvent>,
    onDeleteMeal: (Int) -> Unit,
    onAddMeal: () -> Unit,
    onMealPlanExpand: (isExpanded: Boolean, type: MealPlanType) -> Unit,
    onMealPlanSelect: (type: MealPlanType, plan: MealPlan) -> Unit,
    onCustomMealPlanSave: (MealPlan) -> Unit,
    onKeyboardHide: () -> Unit,
    onFocusMove: () -> Unit,
    onSheetClose: () -> Unit,
    onSheetOpen: () -> Unit,
    onMealNameChange: (name: String, index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when(event) {
                MealPlanViewModel.UiEvent.OnBottomSheetClose -> {
                    scaffoldState.bottomSheetState.hide()
                }
                MealPlanViewModel.UiEvent.OnBottomSheetOpen -> {
                    scaffoldState.bottomSheetState.expand()
                }
            }
        }
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = MaterialTheme.shapes.extraLarge,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.custom_meal_plan),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(0.8f)
                    )
                    IconButton(
                        onClick = onAddMeal,
                        enabled = state.customMealPlan.meals.size < 11
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_meal_button),
                            modifier = Modifier.weight(0.1f)
                        )
                    }
                    IconButton(onClick = {
                        onSheetClose()
                        onCustomMealPlanSave(state.customMealPlan)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = stringResource(id = R.string.save_meal_plan),
                            modifier = Modifier.weight(0.1f)
                        )
                    }
                }
                state.customMealPlan.meals.onEachIndexed { index, meal ->
                    ListItem(
                        headlineContent = {
                            OutlinedTextField(
                                value = meal,
                                onValueChange = {
                                    onMealNameChange(it, index)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledBorderColor = Color.Transparent,
                                    errorBorderColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = if (state.customMealPlan.meals.lastIndex == index) {
                                        ImeAction.Done
                                    } else {
                                        ImeAction.Next
                                    }
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        onKeyboardHide()
                                    },
                                    onNext = {
                                        onFocusMove()
                                    }
                                )
                            )
                        },
                        leadingContent = {
                            Text(text = "${index + 1}")
                        },
                        trailingContent = {
                            IconButton(
                                onClick = {
                                    onDeleteMeal(index)
                                },
                                enabled = index != 0
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = stringResource(id = R.string.delete_meal)
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            MealPlanSection(
                mealPlan = state.fiveMealPlan,
                selectedMealPlan = state.selectedMealPlan,
                onMealPlanExpand = { isExpanded ->
                    onMealPlanExpand(isExpanded, MealPlanType.FIVE)
                },
                onMealPlanSelect = { type, plan ->
                    onMealPlanSelect(type, plan)
                }
            )
            MealPlanSection(
                mealPlan = state.fourMealPlan,
                selectedMealPlan = state.selectedMealPlan,
                onMealPlanExpand = { isExpanded ->
                    onMealPlanExpand(isExpanded, MealPlanType.FOUR)
                },
                onMealPlanSelect = { type, plan ->
                    onMealPlanSelect(type, plan)
                }
            )
            MealPlanSection(
                mealPlan = state.threeMealPlan,
                selectedMealPlan = state.selectedMealPlan,
                onMealPlanExpand = { isExpanded ->
                    onMealPlanExpand(isExpanded, MealPlanType.THREE)
                },
                onMealPlanSelect = { type, plan ->
                    onMealPlanSelect(type, plan)
                }
            )
            MealPlanSection(
                mealPlan = state.customMealPlan,
                selectedMealPlan = state.selectedMealPlan,
                onMealPlanExpand = { isExpanded ->
                    onMealPlanExpand(isExpanded, MealPlanType.CUSTOM)
                },
                onMealPlanSelect = { type, plan ->
                    onMealPlanSelect(type, plan)
                },
                onSheetOpen = onSheetOpen
            )
        }
    }
}


@Composable
private fun MealPlanSection(
    mealPlan: MealPlan,
    selectedMealPlan: MealPlanType?,
    onMealPlanExpand: (Boolean) -> Unit,
    onMealPlanSelect: (type: MealPlanType, plan: MealPlan) -> Unit,
    modifier: Modifier = Modifier,
    onSheetOpen: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onMealPlanSelect(mealPlan.type, mealPlan)
                    if (mealPlan.type == MealPlanType.CUSTOM) {
                        onSheetOpen()
                    }
                }
        ) {
            Box(
                modifier = modifier
                    .weight(0.1f)
            ) {
                Crossfade(targetState = selectedMealPlan == mealPlan.type, label = "") { isSelected ->
                    if (isSelected) {
                        Icon(
                            imageVector = mealPlan.selectedLeadingIcon!!,
                            contentDescription = mealPlan.name,
                            modifier = Modifier.padding(4.dp)
                        )
                    } else {
                        Icon(
                            imageVector = mealPlan.leadingIcon!!,
                            contentDescription = mealPlan.name,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
            Text(
                text = mealPlan.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(0.8f)
            )
            IconButton(
                onClick = {
                    onMealPlanExpand(!mealPlan.isExpanded)
                },
                modifier = Modifier
                    .weight(0.1f)
            ) {
                Icon(
                    imageVector = if (mealPlan.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = stringResource(id = R.string.expand)
                )
            }
        }
        if (mealPlan.isExpanded) {
            mealPlan.meals.onEachIndexed { index, meal ->
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 32.dp, bottom = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "${index + 1}")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = meal)
                }
            }
        }
    }
}