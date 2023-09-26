package com.example.fitnessapp.history_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessapp.history_feature.data.local.entity.NutritionHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionHistoryDao {

    @Query("SELECT * FROM NutritionHistoryEntity WHERE month = :month")
    fun getMonthNutrition(month: Int): Flow<List<NutritionHistoryEntity>>

    @Insert
    suspend fun addToHistory(nutrition: List<NutritionHistoryEntity>)
}