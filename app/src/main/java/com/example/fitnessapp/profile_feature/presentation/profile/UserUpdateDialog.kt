package com.example.fitnessapp.profile_feature.presentation.profile

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.fitnessapp.R

@Composable
fun UserUpdateDialog(
    onUserUpdateDialogDismiss: () -> Unit,
    onUserUpdateDialogDecline: () -> Unit,
    onUserUpdateDialogConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onUserUpdateDialogDismiss,
        confirmButton = {
            TextButton(onClick = {
                onUserUpdateDialogConfirm()
                onUserUpdateDialogDismiss()
            }) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onUserUpdateDialogDismiss()
                onUserUpdateDialogDecline()
            }) {
                Text(text = stringResource(id = R.string.decline))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.user_update_dialog_title))
        },
        text = {
            Text(text = stringResource(id = R.string.user_update_dialog_text))
        }
    )
}