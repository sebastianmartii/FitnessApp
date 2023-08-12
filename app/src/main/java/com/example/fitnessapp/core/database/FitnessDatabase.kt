package com.example.fitnessapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.database.entity.CurrentUser
import com.example.fitnessapp.nutrition_calculator_feature.data.local.MealConverters
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.FoodItemDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.MealDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity

@Database(
    entities = [CurrentUser::class, MealPlanEntity::class, FoodItemEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MealConverters::class)
abstract class FitnessDatabase : RoomDatabase() {

    abstract val currentUserDao: CurrentUserDao

    abstract val mealDao: MealDao

    abstract val foodItemDao: FoodItemDao
}