package com.example.fitnessapp.nutrition_calculator_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Upsert
    suspend fun insertMealPLan(mealPlanEntity: MealPlanEntity)

    @Query("DELETE FROM MealPlanEntity WHERE id = 1")
    suspend fun deleteMealPlan()

    @Query("SELECT * FROM MealPlanEntity WHERE id = 1")
    fun getMealPLan(): Flow<MealPlanEntity>

    @Query("SELECT plan_name FROM MealPlanEntity WHERE id = 1")
    fun getMealPLanName(): Flow<String>
}