package com.example.fitnessapp.daily_overview_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyActivitiesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyActivitiesDao {

    @Upsert
    suspend fun addActivities(activities: List<DailyActivitiesEntity>)

    @Query("DELETE FROM DailyActivitiesEntity WHERE name = :name AND caloriesBurned = :caloriesBurned AND duration = :duration")
    suspend fun deleteActivity(name: String, caloriesBurned: String, duration: Double)

    @Query("DELETE FROM DailyActivitiesEntity")
    suspend fun resetDailyActivities()

    @Query("SELECT * FROM DailyActivitiesEntity")
    fun getDailyActivities(): Flow<List<DailyActivitiesEntity>>
}