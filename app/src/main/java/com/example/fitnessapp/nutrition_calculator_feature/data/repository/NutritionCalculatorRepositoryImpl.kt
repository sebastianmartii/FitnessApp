package com.example.fitnessapp.nutrition_calculator_feature.data.repository

import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.FoodItemDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toFoodItem
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toFoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.NutritionCalculatorApi
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NutritionCalculatorRepositoryImpl(
    private val foodItemDao: FoodItemDao,
    private val nutritionCalculatorApi: NutritionCalculatorApi
) : NutritionCalculatorRepository {

    override suspend fun insertFoodItem(foodItem: FoodItem) {
        foodItemDao.insertFoodItem(foodItem.toFoodItemEntity())
    }

    override fun getAllCachedFoodItems(): Flow<List<FoodItemEntity>> = foodItemDao.getAllCachedFoodItems()

    override fun getFoodNutrition(query: String): Flow<Resource<List<FoodItem>>> = flow {
        emit(Resource.Loading())

        try {
            val response = nutritionCalculatorApi.getFoodNutrition(query)
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()?.items?.map { it.toFoodItem() }))
            } else {
                emit(Resource.Error(message = "${response.errorBody() }"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "$e"))
        }
    }

    override suspend fun cacheChosenProducts(products: List<FoodItem>) {
        foodItemDao.insertAllFoodItems(products.map { it.toFoodItemEntity() })
    }

    override suspend fun deleteFoodItem(foodItem: FoodItem) {
        foodItemDao.deleteFoodItem(
            name = foodItem.name,
            servingSize = foodItem.servingSize,
            calories = foodItem.calories,
            carbs = foodItem.carbs,
            protein = foodItem.protein,
            fat = foodItem.totalFat,
            saturatedFat = foodItem.saturatedFat,
            fiber = foodItem.fiber,
            sugar = foodItem.sugar
        )
    }
}