package com.example.fitnessapp.activities_feature.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitnessapp.R
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity

@Composable
fun SavedActivitiesScreen(
    savedActivities: List<SavedActivity>
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        savedActivities.onEach { savedActivity ->
            ListItem(
                headlineContent = {
                    Text(text = savedActivity.name)
                },
                supportingContent = {
                    if (!savedActivity.description.isNullOrBlank()) {
                        Text(text = savedActivity.description)
                    }
                },
                leadingContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = savedActivity.duration.toString())
                        Text(text = stringResource(id = R.string.suffix_minutes))
                    }
                },
                trailingContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = savedActivity.burnedCalories)
                        Text(text = stringResource(id = R.string.suffix_calories))
                    }
                }
            )
        }
    }
}