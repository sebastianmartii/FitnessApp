package com.example.fitnessapp.nutrition_calculator_feature.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.FoodItemEntity
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toFoodItem
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class FoodItemDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: FitnessDatabase
    private lateinit var foodItemDao: FoodItemDao

    @Before
    fun setUp() {
        hiltRule.inject()
        foodItemDao = db.foodItemDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addFoodItems_FoodItemsAddedCorrectly() = runTest {
        val foodItem = FoodItemEntity(
            id = 1,
            name = "Banana",
            servingSize = 100.0,
            calories = 100.0,
            carbs = 12.0,
            protein = 3.5,
            fat = 1.5,
            saturatedFat = 0.0,
            fiber = 4.0,
            sugar = 2.0
        )
        foodItemDao.insertFoodItem(foodItem)
        val foodItems = foodItemDao.getAllCachedFoodItems().first()
        assertThat(foodItems).contains(foodItem)
    }

    @Test
    fun deleteFoodItems_FoodItemsDeletedCorrectly() = runTest {
        val foodItemOne = FoodItemEntity(
            id = 1,
            name = "Banana",
            servingSize = 100.0,
            calories = 100.0,
            carbs = 12.0,
            protein = 3.5,
            fat = 1.5,
            saturatedFat = 0.0,
            fiber = 4.0,
            sugar = 2.0
        )
        val foodItemTwo = FoodItemEntity(
            id = 2,
            name = "Rolled Oats",
            servingSize = 100.0,
            calories = 360.0,
            carbs = 40.0,
            protein = 6.5,
            fat = 1.5,
            saturatedFat = 0.0,
            fiber = 8.0,
            sugar = 2.5
        )
        val foodItemThree = FoodItemEntity(
            id = 3,
            name = "Milk",
            servingSize = 100.0,
            calories = 50.0,
            carbs = 7.0,
            protein = 1.5,
            fat = 0.5,
            saturatedFat = 0.0,
            fiber = 2.0,
            sugar = 1.0
        )
        foodItemDao.insertAllFoodItems(listOf(foodItemOne, foodItemTwo, foodItemThree))
        val foodItems = foodItemDao.getAllCachedFoodItems().first()
        assertThat(foodItems).contains(foodItemOne)
        assertThat(foodItems).contains(foodItemTwo)
        assertThat(foodItems).contains(foodItemThree)
        foodItemDao.deleteFoodItems(listOf(foodItemOne.toFoodItem(), foodItemThree.toFoodItem()))
        foodItemDao.deleteFoodItem(
            name = "Rolled Oats",
            servingSize = 100.0,
            calories = 360.0,
            carbs = 40.0,
            protein = 6.5,
            fat = 1.5,
            saturatedFat = 0.0,
            fiber = 8.0,
            sugar = 2.5
        )
        val foodItemsUpdated = foodItemDao.getAllCachedFoodItems().first()
        assertThat(foodItemsUpdated).isEmpty()
    }
}