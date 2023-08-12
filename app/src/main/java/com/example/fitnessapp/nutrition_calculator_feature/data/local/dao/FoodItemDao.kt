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

    @Query("SELECT * FROM FoodItemEntity")
    fun getAllCachedFoodItems(): Flow<List<FoodItemEntity>>
}