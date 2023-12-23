package com.example.fitnessapp.history_feature.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.history_feature.data.local.entity.NutritionHistoryEntity
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
class NutritionHistoryDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: FitnessDatabase
    private lateinit var nutritionHistoryDao: NutritionHistoryDao

    @Before
    fun setUp() {
        hiltRule.inject()
        nutritionHistoryDao = db.nutritionHistoryDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addNutritionToHistory_NutritionAddedCorrectly() = runTest {
        val nutrition = NutritionHistoryEntity(
            id = 1,
            name = "Banana",
            servingSize = 100.0,
            calories = 100.0,
            carbs = 12.0,
            protein = 3.5,
            fat = 1.5,
            meal = "Breakfast",
            year = 2023,
            month = 12,
            day = 23
        )
        nutritionHistoryDao.addToHistory(listOf(nutrition))
        val nutritionHistory = nutritionHistoryDao.getMonthNutrition(12).first()
        assertThat(nutritionHistory).contains(nutrition)
    }
}