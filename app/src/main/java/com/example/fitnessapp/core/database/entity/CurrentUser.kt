package com.example.fitnessapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") val userID: Int? = null,
    val name: String,
    @ColumnInfo(name = "calories_goal") val caloriesGoal: Int,
    val age: Int,
    val gender: String,
    val weight: Float,
    val height: Float,
    @ColumnInfo(name = "activity_level") val activityLevel: String,
    @ColumnInfo(name = "is_signed_in") val isSignedIn: Boolean = true,
)
