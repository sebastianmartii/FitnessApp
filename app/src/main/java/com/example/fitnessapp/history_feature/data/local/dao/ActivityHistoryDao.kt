package com.example.fitnessapp.history_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.fitnessapp.history_feature.data.local.entity.ActivityHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityHistoryDao {

    @Query("SELECT * FROM ActivityHistoryEntity WHERE month = :month")
    fun getMonthActivities(month: Int): Flow<List<ActivityHistoryEntity>>
}