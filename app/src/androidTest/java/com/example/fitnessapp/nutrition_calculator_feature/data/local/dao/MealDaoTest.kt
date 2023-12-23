package com.example.fitnessapp.nutrition_calculator_feature.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanType
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
class MealDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: FitnessDatabase
    private lateinit var mealDao: MealDao

    @Before
    fun setUp() {
        hiltRule.inject()
        mealDao = db.mealDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addMealPlan_MealPlanAddedCorrectly() = runTest {
        val mealPlanEntity = MealPlanEntity(
            id = 1,
            mealPlanType = MealPlanType.THREE,
            planName = "Three Meals Plan",
            meals = listOf(
                "Breakfast",
                "Lunch",
                "Dinner"
            )
        )
        mealDao.insertMealPlan(mealPlanEntity)
        val mealPlan = mealDao.getMealPlan().first()
        assertThat(mealPlan).isEqualTo(mealPlanEntity)
    }

    @Test
    fun deleteMealPlan_MealPlanAddedCorrectly() = runTest {
        val mealPlanEntity = MealPlanEntity(
            id = 1,
            mealPlanType = MealPlanType.THREE,
            planName = "Three Meals Plan",
            meals = listOf(
                "Breakfast",
                "Lunch",
                "Dinner"
            )
        )
        mealDao.insertMealPlan(mealPlanEntity)
        val mealPlan = mealDao.getMealPlan().first()
        assertThat(mealPlan).isEqualTo(mealPlanEntity)
        mealDao.deleteMealPlan()
        val mealPlanUpdated = mealDao.getMealPlan().first()
        assertThat(mealPlanUpdated).isNull()
    }

    @Test
    fun getMealsFromMealPlan_MealsRetrievedCorrectly() = runTest {
        val mealPlanEntity = MealPlanEntity(
            id = 1,
            mealPlanType = MealPlanType.FOUR,
            planName = "Four Meals Plan",
            meals = listOf(
                "Breakfast",
                "Second Breakfast",
                "Lunch",
                "Dinner"
            )
        )
        mealDao.insertMealPlan(mealPlanEntity)
        val meals = mealDao.getMeals().first()
        assertThat(meals).contains("Breakfast")
        assertThat(meals).contains("Second Breakfast")
        assertThat(meals).contains("Lunch")
        assertThat(meals).contains("Dinner")
    }
}