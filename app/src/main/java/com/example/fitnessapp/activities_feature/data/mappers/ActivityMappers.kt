package com.example.fitnessapp.activities_feature.data.mappers

import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import com.example.fitnessapp.activities_feature.data.remote.dto.Data
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity
import com.example.fitnessapp.activities_feature.presentation.ActivitiesTabRowItem

fun Data.toActivityList(): Activity {
    return Activity(
        id = this.id,
        name = this.activity,
        description = this.description
    )
}

fun SavedActivitiesEntity.toSavedActivity(): SavedActivity {
    return SavedActivity(
        name = this.activity,
        description = this.description,
        burnedCalories = this.caloriesBurned,
        duration = this.duration
    )
}

fun ActivitiesTabRowItem.toTabTitle(): String {
    return when(this) {
        ActivitiesTabRowItem.SAVED -> "Saved"
        ActivitiesTabRowItem.SEARCH -> "Search"
    }
}