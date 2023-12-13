package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.food_item_creator

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.nutrition_calculator_feature.data.repository.FakeNutritionCalculatorRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FoodItemCreatorViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repo: FakeNutritionCalculatorRepository
    private lateinit var viewModel: FoodItemCreatorViewModel

    @Before
    fun setUp() {
        repo = FakeNutritionCalculatorRepository()
        viewModel = FoodItemCreatorViewModel(repo)
    }

    @Test
    fun `Change calories, calories changed correctly`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnCaloriesChange("200"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.calories).isEqualTo("200")
        }
    }

    @Test
    fun `Change carbs, carbs changed correctly`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnCarbsChange("80"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.carbs).isEqualTo("80")
        }
    }

    @Test
    fun `Change protein, protein changed correctly`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnProteinChange("20"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.protein).isEqualTo("20")
        }
    }

    @Test
    fun `Change name, name changed correctly`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnNameChange("test name"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.name).isEqualTo("test name")
        }
    }

    @Test
    fun `Change total fat, total fat changed correctly`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnTotalFatChange("4"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.totalFat).isEqualTo("4")
        }
    }

    @Test
    fun `Change saturated fat, saturated fat changed correctly`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnSaturatedFatChange("2"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.saturatedFat).isEqualTo("2")
        }
    }

    @Test
    fun `Change serving size, serving size changed correctly`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnServingSizeChange("500"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.servingSize).isEqualTo("500")
        }
    }

    @Test
    fun `Change sugar, sugar changed correctly`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnSugarChange("5"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.sugar).isEqualTo("5")
        }
    }

    @Test
    fun `Change fiber, fiber changed correctly`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnFiberChange("12"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.fiber).isEqualTo("12")
        }
    }

    @Test
    fun `Create food item with correct components, ui event navigate back triggered`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnFoodItemCreated(true))
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(FoodItemCreatorViewModel.UiEvent.NavigateBack)
        }
    }

    @Test
    fun `Create food item with incorrect components, ui event show snackBar triggered`() = runTest {
        viewModel.onEvent(FoodItemCreatorEvent.OnFoodItemCreated(false))
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission)
                .isEqualTo(FoodItemCreatorViewModel
                    .UiEvent
                    .ShowSnackbar("Some Components Are Not Valid. Provide Valid Components To Create A Food Item"))
        }
    }
}