package com.example.fitnessapp.activities_feature.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.activities_feature.data.mappers.toCaloriesString
import com.example.fitnessapp.activities_feature.data.mappers.toDurationString
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity

@Composable
fun SavedActivitiesScreen(
    savedActivities: List<SavedActivity>
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(savedActivities) { savedActivity ->
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
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = savedActivity.burnedCalories.toCaloriesString())
                        Text(text = stringResource(id = R.string.suffix_calories))
                    }
                },
                trailingContent = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = savedActivity.duration.toDurationString(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                }
            )
        }
        item {
            Spacer(modifier = Modifier.height(256.dp))
        }
    }
}