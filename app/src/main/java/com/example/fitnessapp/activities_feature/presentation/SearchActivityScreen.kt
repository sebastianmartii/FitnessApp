package com.example.fitnessapp.activities_feature.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fitnessapp.R
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.IntensityItem


@Composable
fun SearchActivityScreen(
    intensityLevels: List<IntensityItem>,
    onIntensityLevelExpandedChange: (isExpanded: Boolean, intensityIndex: Int) -> Unit,
    onIntensityLevelActivitiesFetch: (intensityLevel: IntensityLevel, intensityIndex: Int) -> Unit,
    onActivityClick: (Activity) -> Unit
) {
    LazyColumn {
        itemsIndexed(intensityLevels) { index, item ->
            IntensitySection(
                intensityItem = item,
                intensityActivities = item.activities,
                onSectionExpand = { expanded, level ->
                    onIntensityLevelExpandedChange(expanded, index)
                    if (item.activities.isEmpty()) {
                        onIntensityLevelActivitiesFetch(level, index)
                    }
                },
                onActivityClick = onActivityClick
            )
        }
    }
}


@Composable
private fun IntensitySection(
    intensityItem: IntensityItem,
    intensityActivities: List<Activity>,
    onSectionExpand: (isExpanded: Boolean, intensityLevel: IntensityLevel) -> Unit,
    onActivityClick: (Activity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = intensityItem.intensityLevel.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(0.8f)
            )
            IconButton(onClick = {
                onSectionExpand(!intensityItem.isExpanded, intensityItem.intensityLevel)
            }) {
                Icon(
                    imageVector = if (intensityItem.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = stringResource(id = R.string.expand)
                )
            }
        }
        if (intensityItem.isLoading) {
            CircularProgressIndicator()
        }
        if (intensityItem.isExpanded) {
            intensityActivities.onEach { activity ->
                ListItem(
                    headlineContent = {
                        Text(text = activity.name)
                    },
                    supportingContent = {
                        Text(text = activity.description)
                    },
                    modifier = Modifier.clickable {
                        onActivityClick(activity)
                    }
                )
            }
        }
    }
}