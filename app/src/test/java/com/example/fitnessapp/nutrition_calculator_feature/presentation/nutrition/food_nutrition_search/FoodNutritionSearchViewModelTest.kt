package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.food_nutrition_search

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.nutrition_calculator_feature.data.repository.FakeNutritionCalculatorRepository
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FoodNutritionSearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repo: FakeNutritionCalculatorRepository
    private lateinit var viewModel: FoodNutritionSearchViewModel

    @Before
    fun setUp() {
        repo = FakeNutritionCalculatorRepository()
        viewModel = FoodNutritionSearchViewModel(repo)
    }

    @Test
    fun `Change query, query is changed accordingly`() = runTest {
        viewModel.onEvent(FoodNutritionSearchEvent.OnQueryChange("test query"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.query).isEqualTo("test query")
        }
    }

    @Test
    fun `Get nutrition for entered query, nutrition is returned correctly`() = runTest {
        viewModel.onEvent(FoodNutritionSearchEvent.OnQueryChange("Banana"))
        viewModel.onEvent(FoodNutritionSearchEvent.OnNutritionSearch)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.foodItems.first().name).isEqualTo("Banana")
        }
    }

    @Test
    fun `Select food item, item is selected`() = runTest {
        val selectedFoodItem = FoodItem(
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
        )
        viewModel.onEvent(FoodNutritionSearchEvent.OnQueryChange("Banana"))
        viewModel.onEvent(FoodNutritionSearchEvent.OnNutritionSearch)
        viewModel.onEvent(FoodNutritionSearchEvent.OnFoodItemSelect(selectedFoodItem))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.foodItems.first().isSelected).isTrue()
        }
    }
}