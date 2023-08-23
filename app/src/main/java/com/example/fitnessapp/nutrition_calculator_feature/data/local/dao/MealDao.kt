package com.example.fitnessapp.nutrition_calculator_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealPlan(mealPlanEntity: MealPlanEntity)

    @Query("DELETE FROM MealPlanEntity WHERE id = 1")
    suspend fun deleteMealPlan()

    @Query("SELECT * FROM MealPlanEntity WHERE id = 1")
    fun getMealPlan(): Flow<MealPlanEntity>
}