package com.example.fitnessapp.nutrition_calculator_feature.data.repository

import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toFoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNutritionCalculatorRepository : NutritionCalculatorRepository {

    private val cachedItems = mutableListOf(
        FoodItemEntity(
            id = 1,
            name = "Banana",
            calories = 100.0,
            servingSize = 80.0,
            carbs = 12.0,
            protein = 0.5,
            fat = 1.5,
            saturatedFat = 0.0,
            fiber = 4.5,
            sugar = 2.0
        ),
        FoodItemEntity(
            id = 2,
            name = "dark chocolate",
            calories = 80.0,
            servingSize = 16.0,
            carbs = 20.0,
            protein = 1.0,
            fat = 4.5,
            saturatedFat = 2.0,
            fiber = 2.5,
            sugar = 7.0
        ),
        FoodItemEntity(
            id = 3,
            name = "milk",
            calories = 50.0,
            servingSize = 100.0,
            carbs = 15.0,
            protein = 3.5,
            fat = 2.5,
            saturatedFat = 0.0,
            fiber = 2.0,
            sugar = 1.5
        )
    )

    private val mealPlan = listOf(
        "Breakfast",
        "Lunch",
        "Dinner"
    )

    private val foodNutritionList = listOf(
        FoodItem(
            name = "Banana",
            calories = 100.0,
            servingSize = 80.0,
            carbs = 12.0,
            protein = 0.5,
            totalFat = 1.5,
            saturatedFat = 0.0,
            fiber = 4.5,
            sugar = 2.0,
            isSelected = false
        ),
        FoodItem(
            name = "Milk",
            calories = 50.0,
            servingSize = 100.0,
            carbs = 12.0,
            protein = 0.5,
            totalFat = 1.5,
            saturatedFat = 0.0,
            fiber = 4.5,
            sugar = 2.0,
            isSelected = false
        ),
        FoodItem(
            name = "Oats",
            calories = 50.0,
            servingSize = 180.0,
            carbs = 22.0,
            protein = 6.5,
            totalFat = 1.5,
            saturatedFat = 0.0,
            fiber = 7.0,
            sugar = 0.0,
            isSelected = false
        )
    )

    override suspend fun insertFoodItem(foodItem: FoodItem) {
        cachedItems.add(foodItem.toFoodItemEntity())
    }

    override fun getAllCachedFoodItems(): Flow<List<FoodItemEntity>> = flow {
        emit(cachedItems)
    }

    override fun getFoodNutrition(query: String): Flow<Resource<List<FoodItem>>> = flow {
        emit(Resource.Success(data = foodNutritionList.filter { it.name.contains(query) }))
    }

    override suspend fun deleteFoodItems(foodItems: List<FoodItem>) {
        foodItems.onEach { foodItem ->
            cachedItems.remove(foodItem.toFoodItemEntity())
        }
    }

    override suspend fun cacheChosenProducts(products: List<FoodItem>) {
        products.onEach { foodItem ->
            cachedItems.add(foodItem.toFoodItemEntity())
        }
    }

    override suspend fun addFoodItemsToDailyNutrition(list: List<FoodItem>, meal: String) {}

    override fun getMeals(): Flow<List<String>> = flow {
        emit(mealPlan)
    }
}