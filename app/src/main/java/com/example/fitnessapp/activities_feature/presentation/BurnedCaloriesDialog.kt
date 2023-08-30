package com.example.fitnessapp.activities_feature.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R


@Composable
fun BurnedCaloriesDialog(
    minutes: String,
    seconds: String,
    isConfirmButtonEnabled: Boolean,
    onMinutesChange: (String) -> Unit,
    onSecondsChange: (String) -> Unit,
    onBurnedCaloriesDialogDismiss: () -> Unit,
    onConfirm: (minutes: String, seconds: String) -> Unit,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onBurnedCaloriesDialogDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(minutes, seconds)
                    onBurnedCaloriesDialogDismiss()
                },
                enabled = isConfirmButtonEnabled
            ) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onBurnedCaloriesDialogDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        text = {
            BurnedCaloriesDialogContent(
                minutes = minutes,
                seconds = seconds,
                onMinutesChange = onMinutesChange,
                onSecondsChange = onSecondsChange,
                onFocusMove = onFocusMove,
                onKeyboardHide = onKeyboardHide)
        },
        title = {
            Text(text = stringResource(id = R.string.burned_calories_dialog_title))
        },
        modifier = modifier
    )
}


@Composable
private fun BurnedCaloriesDialogContent(
    minutes: String,
    seconds: String,
    onMinutesChange: (String) -> Unit,
    onSecondsChange: (String) -> Unit,
    onFocusMove: () -> Unit,
    onKeyboardHide: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = minutes,
            onValueChange = { minutes ->
                onMinutesChange(minutes)
            },
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            label = {
                Text(text = stringResource(id = R.string.minutes_label))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.minutes_seconds_placeholder))
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Decimal
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onFocusMove()
                }
            ),
            modifier = Modifier.weight(0.2f)
        )
        Text(
            text = stringResource(id = R.string.minutes_seconds_divider),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        OutlinedTextField(
            value = seconds,
            onValueChange = { seconds ->
                onSecondsChange(seconds)
            },
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            label = {
                Text(text = stringResource(id = R.string.seconds_label))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.minutes_seconds_placeholder))
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Decimal
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