package com.example.fitnessapp.activities_feature.data.mappers

import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import com.example.fitnessapp.activities_feature.data.remote.dto.Data
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity
import com.example.fitnessapp.activities_feature.presentation.ActivitiesTabRowItem
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyActivitiesEntity

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

fun Double.toDurationString(): String {
    val splitDuration = this.toString().split(".")
    val suffix: String = when {
        this % 1.0 == 0.0 -> {
            "min"
        }
        this < 0.60 -> {
            "s"
        }
        else -> ""
    }
    val decimal = splitDuration[1]
    val additionalZero = if (decimal.length == 1 && decimal != "0") "0" else ""
    return "${splitDuration.filter { it != "0" }.joinToString(":")}$additionalZero$suffix"
}

fun String.toCaloriesString(): String {
    return this.toDouble().toInt().toString()
}

fun SavedActivity.toDailyActivity(): DailyActivitiesEntity {
    return DailyActivitiesEntity(
        name = this.name,
        caloriesBurned = this.burnedCalories,
        duration = this.duration
    )
}