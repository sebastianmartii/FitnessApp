package com.example.fitnessapp.nutrition_calculator_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodItemDao {

    @Insert
    suspend fun insertFoodItem(foodItemEntity: FoodItemEntity)

    @Insert
    suspend fun insertAllFoodItems(foodItems: List<FoodItemEntity>)

    @Query("DELETE FROM FoodItemEntity WHERE name = :name AND calories = :calories AND serving_size = :servingSize AND carbs = :carbs " +
            "AND protein = :protein AND fat = :fat AND saturated_fat = :saturatedFat AND fiber = :fiber AND sugar = :sugar")
    suspend fun deleteFoodItem(
        name: String,
        calories: Double,
        servingSize: Double,
        carbs: Double,
        protein: Double,
        fat: Double,
        saturatedFat: Double,
        fiber: Double,
        sugar: Double
    )

    @Query("SELECT * FROM FoodItemEntity")
    fun getAllCachedFoodItems(): Flow<List<FoodItemEntity>>
}