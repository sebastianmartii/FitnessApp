package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toLabel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemCreatorScreen(
    name: String,
    foodComponents: List<FoodComponent>,
    onEvent: (FoodItemCreatorEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = name)
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
            FloatingActionButton(onClick = {
                onEvent(FoodItemCreatorEvent.OnFoodItemCreated)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.add_food_item)
                )
            }
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
            foodComponents.onEachIndexed { index, foodComponent ->
                if (foodComponent.leadingIcon != null) {
                    IconTextField(
                        componentValue = foodComponent.value,
                        leadingIcon = foodComponent.leadingIcon,
                        leadingIconDescription = foodComponent.leadingIconDescription,
                        label = foodComponent.type.toLabel(),
                        trailingText = foodComponent.trailingTexT,
                        onComponentValueChange = {
                            onEvent(FoodItemCreatorEvent.OnFoodComponentChange(it, index))
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .fillMaxWidth()
                    )
                } else {
                    OutlinedTextField(
                        value = foodComponent.value,
                        onValueChange = {
                            onEvent(FoodItemCreatorEvent.OnFoodComponentChange(it, index))
                        },
                        label = {
                            Text(text = foodComponent.type.toLabel())
                        },
                        trailingIcon = {
                            if (!foodComponent.trailingTexT.isNullOrEmpty()) {
                                Text(text = foodComponent.trailingTexT)
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .fillMaxWidth(0.8f)
                    )
                }
            }
        }
    }
}

@Composable
private fun IconTextField(
    componentValue: String,
    label: String,
    trailingText: String?,
    leadingIcon: ImageVector,
    leadingIconDescription: String?,
    onComponentValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isKeyboardDecimal: Boolean = false
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = leadingIconDescription,
            modifier = Modifier.weight(0.2f)
        )
        OutlinedTextField(
            value = componentValue,
            onValueChange = {
                onComponentValueChange(it)
            },
            label = {
                Text(text = label)
            },
            trailingIcon = {
                if (!trailingText.isNullOrEmpty()) {
                    Text(text = trailingText)
                }
            },
            keyboardOptions = if (isKeyboardDecimal) {
                KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            } else {
                KeyboardOptions.Default
            },
            modifier = Modifier.weight(0.8f)
        )
    }
}