package com.example.fitnessapp.activities_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedActivitiesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val activity: String,
    val description: String? = null,
    val caloriesBurned: String,
    val duration: Double,
)
