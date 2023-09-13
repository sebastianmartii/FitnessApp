package com.example.fitnessapp.daily_overview_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyNutritionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyNutritionDao {

    @Upsert
    suspend fun addFoodItems(foodItems: List<DailyNutritionEntity>)

    @Query("DELETE FROM DailyNutritionEntity")
    suspend fun resetDailyNutrition()

    @Query("DELETE FROM DailyNutritionEntity WHERE meal = :meal")
    suspend fun resetMeal(meal: String)

    @Query("DELETE FROM DailyNutritionEntity WHERE name = :name AND servingSize = :size AND calories = :calories " +
            "AND carbs = :carbs AND protein = :protein AND fat = :fat AND meal = :meal")
    suspend fun deleteFoodItem(name: String, size: Double, calories: Double, carbs: Double, protein: Double, fat: Double, meal: String)
    @Query("SELECT * FROM DailyNutritionEntity")
    fun getDailyNutrition(): Flow<List<DailyNutritionEntity>>
}