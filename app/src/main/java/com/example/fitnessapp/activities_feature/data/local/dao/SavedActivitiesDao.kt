package com.example.fitnessapp.activities_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedActivitiesDao {

    @Upsert
    suspend fun saveActivity(activitiesDao: SavedActivitiesEntity)

    @Query("DELETE FROM SavedActivitiesEntity WHERE activity = :name AND description = :description " +
            "AND caloriesBurned = :caloriesBurned AND duration = :duration")
    suspend fun deleteSavedActivity(name: String, description: String?, caloriesBurned: String, duration: Double)

    @Transaction
    suspend fun deleteSavedActivities(activities: List<SavedActivity>) {
        activities.onEach { activity ->
            deleteSavedActivity(
                activity.name,
                activity.description,
                activity.burnedCalories,
                activity.duration
            )
        }
    }

    @Query("SELECT * FROM SavedActivitiesEntity")
    fun getSavedActivities(): Flow<List<SavedActivitiesEntity>>
}