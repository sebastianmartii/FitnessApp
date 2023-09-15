package com.example.fitnessapp.activities_feature.data.mappers

import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import com.example.fitnessapp.activities_feature.data.remote.dto.Data
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity
import com.example.fitnessapp.activities_feature.presentation.ActivitiesTabRowItem
import com.example.fitnessapp.core.util.toDuration
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyActivitiesEntity
import kotlin.math.roundToInt

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

fun Double.toDurationString(isDuration: Boolean = false): String {
    val duration = if (isDuration) this else this.toDuration()
    val minutes = duration.toInt()
    val seconds = (((duration % 1.0) * 0.6) * 100.0).roundToInt()
    return when {
        seconds == 0 -> {
          "${minutes}min"
        }
        minutes == 0 -> {
            "${seconds}s"
        }
        seconds == 60 -> {
            "${minutes + 1}min"
        }
        else -> {
            "$minutes:$seconds"
        }
    }
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