package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R

@Composable
fun MealSelectionDialog(
    mealList: List<String>?,
    selectedMeal: String?,
    onMealSelectionDialogDismiss: () -> Unit,
    onMealSelectionDialogConfirm: (String?) -> Unit,
    onMealSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onMealSelectionDialogDismiss,
        confirmButton = {
            TextButton(onClick = {
                onMealSelectionDialogConfirm(selectedMeal)
                onMealSelectionDialogDismiss()
            }) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onMealSelectionDialogDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.meal_selection_dialog_title))
        },
        text = {
            Column {
                HorizontalDivider()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    mealList!!.onEach {  meal ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onMealSelect(meal)
                                }
                        ) {
                            RadioButton(
                                selected = selectedMeal == meal,
                                onClick = {
                                    onMealSelect(meal)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = meal,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
                HorizontalDivider()
            }
        },
        modifier = modifier
    )
}