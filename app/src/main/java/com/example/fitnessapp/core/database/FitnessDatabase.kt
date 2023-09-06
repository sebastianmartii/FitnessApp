package com.example.fitnessapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitnessapp.activities_feature.data.local.dao.SavedActivitiesDao
import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.database.entity.CurrentUser
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyActivitiesDao
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyNutritionDao
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyActivitiesEntity
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyNutritionEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.local.MealPlanTypeConverters
import com.example.fitnessapp.nutrition_calculator_feature.data.local.RecipeConverters
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.FoodItemDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.MealDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.RecipesDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.RecipesEntity

@Database(
    entities = [
        CurrentUser::class,
        MealPlanEntity::class,
        FoodItemEntity::class,
        RecipesEntity::class,
        SavedActivitiesEntity::class,
        DailyActivitiesEntity::class,
        DailyNutritionEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipeConverters::class, MealPlanTypeConverters::class)
abstract class FitnessDatabase : RoomDatabase() {

    abstract val currentUserDao: CurrentUserDao

    abstract val mealDao: MealDao

    abstract val foodItemDao: FoodItemDao

    abstract val recipesDao: RecipesDao

    abstract val savedActivitiesDao: SavedActivitiesDao

    abstract val dailyActivitiesDao: DailyActivitiesDao

    abstract val dailyNutritionDao: DailyNutritionDao
}