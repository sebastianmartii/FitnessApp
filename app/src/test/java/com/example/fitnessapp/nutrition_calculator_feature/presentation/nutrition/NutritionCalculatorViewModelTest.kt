package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.nutrition_calculator_feature.data.repository.FakeNutritionCalculatorRepository
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NutritionCalculatorViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repo: FakeNutritionCalculatorRepository
    private lateinit var viewModel: NutritionCalculatorViewModel

    @Before
    fun setUp() {
        repo = FakeNutritionCalculatorRepository()
        viewModel = NutritionCalculatorViewModel(repo)
    }

    @Test
    fun `Select food item, food item is selected`() = runTest {
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
        viewModel.onEvent(NutritionCalculatorEvent.OnFoodItemSelectedChange(selectedFoodItem, 0))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.cachedProducts[0].isSelected).isTrue()
            assertThat(emission.isFABVisible).isTrue()
        }
    }

    @Test
    fun `Delete cached food item, food item is deleted`() = runTest {
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
        viewModel.onEvent(NutritionCalculatorEvent.OnFoodItemSelectedChange(selectedFoodItem, 0))
        viewModel.onEvent(NutritionCalculatorEvent.OnFoodItemDelete)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.cachedProducts).doesNotContain(selectedFoodItem)
            assertThat(emission.isFABVisible).isFalse()
        }
    }

    @Test
    fun `Add food item without selected meal plan, navigate to meal plan screen ui event is triggered`() = runTest {
        viewModel.onEvent(NutritionCalculatorEvent.OnFoodItemsAdd(true))
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(NutritionCalculatorViewModel.UiEvent.NavigateToMealPlanScreen)
        }
    }

    @Test
    fun `Add food item with selected meal plan, meal selection dialog is displayed`() = runTest {
        viewModel.onEvent(NutritionCalculatorEvent.OnFoodItemsAdd(false))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.isMealSelectionDialogVisible).isTrue()
        }
    }

    @Test
    fun `Select meal from meal selection dialog, meal is selected`() = runTest {
        viewModel.onEvent(NutritionCalculatorEvent.OnMealSelectionDialogMealSelect("Breakfast"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.selectedMeal).isEqualTo("Breakfast")
        }
    }

    @Test
    fun `Dismiss meal selection dialog, dialog is dismissed`() = runTest {
        viewModel.onEvent(NutritionCalculatorEvent.OnMealSelectionDialogDismiss)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.isMealSelectionDialogVisible).isFalse()
            assertThat(emission.isFABVisible).isFalse()
            emission.cachedProducts.onEach {  foodItem ->
                assertThat(foodItem.isSelected).isFalse()
            }
        }
    }

    @Test
    fun `Confirm meal selection dialog, dialog is confirmed`() = runTest {
        viewModel.onEvent(NutritionCalculatorEvent.OnMealSelectionDialogConfirm("Breakfast"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.selectedMeal).isNull()
        }
    }
}