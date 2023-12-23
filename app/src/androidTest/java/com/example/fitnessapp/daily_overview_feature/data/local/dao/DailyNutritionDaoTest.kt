package com.example.fitnessapp.daily_overview_feature.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyNutritionEntity
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
class DailyNutritionDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: FitnessDatabase
    private lateinit var dailyNutritionDao: DailyNutritionDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dailyNutritionDao = db.dailyNutritionDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addFoodItemToDailyOverview_FoodItemAddedCorrectly() = runTest {
        val foodItem = DailyNutritionEntity(
            id = 1,
            name = "Banana",
            servingSize = 80.0,
            calories = 100.0,
            carbs = 12.0,
            protein = 3.0,
            fat = 1.5,
            meal = "Breakfast"
        )
        dailyNutritionDao.addFoodItems(listOf(foodItem))
        val dailyNutrition = dailyNutritionDao.getDailyNutrition().first()
        assertThat(dailyNutrition).contains(foodItem)
    }

    @Test
    fun deleteFoodItemFromDailyOverview_FoodItemDeletedCorrectly() = runTest {
        val foodItem = DailyNutritionEntity(
            id = 1,
            name = "Banana",
            servingSize = 80.0,
            calories = 100.0,
            carbs = 12.0,
            protein = 3.0,
            fat = 1.5,
            meal = "Breakfast"
        )
        dailyNutritionDao.addFoodItems(listOf(foodItem))
        val dailyNutrition = dailyNutritionDao.getDailyNutrition().first()
        assertThat(dailyNutrition).contains(foodItem)
        dailyNutritionDao.deleteFoodItem(
            name = "Banana",
            size = 80.0,
            calories = 100.0,
            carbs = 12.0,
            protein = 3.0,
            fat = 1.5,
            meal = "Breakfast"
        )
        val dailyNutritionUpdated = dailyNutritionDao.getDailyNutrition().first()
        assertThat(dailyNutritionUpdated).doesNotContain(foodItem)
    }

    @Test
    fun resetDailyNutrition_NutritionResetCorrectly() = runTest {
        val foodItemOne = DailyNutritionEntity(
            id = 1,
            name = "Banana",
            servingSize = 80.0,
            calories = 100.0,
            carbs = 12.0,
            protein = 3.0,
            fat = 1.5,
            meal = "Breakfast"
        )
        val foodItemTwo = DailyNutritionEntity(
            id = 2,
            name = "Rolled Oats",
            servingSize = 100.0,
            calories = 360.0,
            carbs = 40.0,
            protein = 6.0,
            fat = 3.0,
            meal = "Lunch"
        )
        dailyNutritionDao.addFoodItems(listOf(foodItemOne, foodItemTwo))
        val dailyNutrition = dailyNutritionDao.getDailyNutrition().first()
        assertThat(dailyNutrition).contains(foodItemOne)
        assertThat(dailyNutrition).contains(foodItemTwo)
        dailyNutritionDao.resetDailyNutrition()
        val dailyNutritionUpdated = dailyNutritionDao.getDailyNutrition().first()
        assertThat(dailyNutritionUpdated).isEmpty()
    }

    @Test
    fun resetMealFromDailyOverview_MealResetCorrectly() = runTest {
        val foodItemOne = DailyNutritionEntity(
            id = 1,
            name = "Banana",
            servingSize = 80.0,
            calories = 100.0,
            carbs = 12.0,
            protein = 3.0,
            fat = 1.5,
            meal = "Breakfast"
        )
        val foodItemTwo = DailyNutritionEntity(
            id = 2,
            name = "Rolled Oats",
            servingSize = 100.0,
            calories = 360.0,
            carbs = 40.0,
            protein = 6.0,
            fat = 3.0,
            meal = "Lunch"
        )
        dailyNutritionDao.addFoodItems(listOf(foodItemOne, foodItemTwo))
        val dailyNutrition = dailyNutritionDao.getDailyNutrition().first()
        assertThat(dailyNutrition).contains(foodItemOne)
        assertThat(dailyNutrition).contains(foodItemTwo)
        dailyNutritionDao.resetMeal("Lunch")
        val dailyNutritionUpdated = dailyNutritionDao.getDailyNutrition().first()
        assertThat(dailyNutritionUpdated).contains(foodItemOne)
        assertThat(dailyNutritionUpdated).doesNotContain(foodItemTwo)
    }
}