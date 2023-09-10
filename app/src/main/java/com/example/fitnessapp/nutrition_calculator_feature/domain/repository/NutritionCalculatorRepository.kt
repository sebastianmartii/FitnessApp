package com.example.fitnessapp.nutrition_calculator_feature.domain.repository

import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import kotlinx.coroutines.flow.Flow

interface NutritionCalculatorRepository {

    suspend fun insertFoodItem(foodItem: FoodItem)

    fun getAllCachedFoodItems(): Flow<List<FoodItemEntity>>

    fun getFoodNutrition(query: String): Flow<Resource<List<FoodItem>>>

    suspend fun deleteFoodItem(foodItem: FoodItem)

    suspend fun cacheChosenProducts(products: List<FoodItem>)

    suspend fun addFoodItemsToDailyNutrition(list: List<FoodItem>, meal: String)
    fun getMeals(): Flow<List<String>>
}