package com.example.fitnessapp.nutrition_calculator_feature.data.repository

import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyNutritionDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.FoodItemDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.MealDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toDailyNutrition
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toFoodItem
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toFoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.nutrition.NutritionCalculatorApi
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NutritionCalculatorRepositoryImpl(
    private val foodItemDao: FoodItemDao,
    private val nutritionCalculatorApi: NutritionCalculatorApi,
    private val dailyNutritionDao: DailyNutritionDao,
    private val mealDao: MealDao
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

    override suspend fun deleteFoodItems(foodItems: List<FoodItem>) {
        foodItemDao.deleteFoodItems(foodItems)
    }

    override suspend fun addFoodItemsToDailyNutrition(list: List<FoodItem>, meal: String) {
        dailyNutritionDao.addFoodItems(list.map { it.toDailyNutrition(meal) })
    }

    override fun getMeals(): Flow<List<String>> = mealDao.getMeals().map { json ->
        val listType = object : TypeToken<List<String>>() {}.type
        Gson().fromJson(json, listType)
    }
}