package com.example.fitnessapp.activities_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedActivitiesDao {

    @Upsert
    suspend fun saveActivity(activitiesDao: SavedActivitiesEntity)

    @Query("SELECT * FROM SavedActivitiesEntity")
    fun getSavedActivities(): Flow<List<SavedActivitiesEntity>>
}