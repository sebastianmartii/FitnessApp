package com.example.fitnessapp.nutrition_calculator_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Upsert
    suspend fun upsertRecipe(recipe: RecipesEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipesEntity)

    @Query("SELECT * FROM RecipesEntity")
    suspend fun getAllRecipes(): List<RecipesEntity>

    @Query("SELECT * FROM RecipesEntity")
    fun getAllRecipesAsFlow(): Flow<List<RecipesEntity>>
}