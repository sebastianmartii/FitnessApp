package com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.nutrition_calculator_feature.data.repository.FakeCustomMealPlanCreatorRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MealPlanViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repo: FakeCustomMealPlanCreatorRepository
    private lateinit var viewModel: MealPlanViewModel

    @Before
    fun setUp() {
        repo = FakeCustomMealPlanCreatorRepository()
        viewModel = MealPlanViewModel(repo)
    }

    @Test
    fun `Expand chosen meal plan, meal plan expand`() = runTest {
        viewModel.onEvent(MealPlanEvent.OnMealPlanExpandedChange(true, MealPlanType.THREE))
        viewModel.onEvent(MealPlanEvent.OnMealPlanExpandedChange(true, MealPlanType.CUSTOM))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.threeMealPlan.isExpanded).isTrue()
            assertThat(emission.customMealPlan.isExpanded).isTrue()
        }
    }

    @Test
    fun `Change meal plan, meal plan changed correctly`() = runTest {
        val mealPlan = MealPlan(
            name = "Four Meal Plan",
            type = MealPlanType.FOUR,
            meals = mutableListOf(
                "Breakfast",
                "Second Breakfast",
                "Lunch",
                "Dinner"
            )
        )
        viewModel.onEvent(MealPlanEvent.OnMealPlanSelectedChange(MealPlanType.FOUR, mealPlan))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.selectedMealPlan).isEqualTo(MealPlanType.FOUR)
        }
    }

    @Test
    fun `Close bottom sheet, ui event close bottom sheet channeled`() = runTest {
        viewModel.onEvent(MealPlanEvent.OnSheetClose)
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(MealPlanViewModel.UiEvent.OnBottomSheetClose)
        }
    }

    @Test
    fun `Change meal plan to custom one, meal plan changed correctly`() = runTest {
        val customMealPlan = MealPlan(
            name = "Custom Meal Plan",
            type = MealPlanType.CUSTOM,
            meals = mutableListOf(
                "Breakfast",
                "Dinner"
            )
        )
        viewModel.onEvent(MealPlanEvent.OnCustomMealPlanSave(customMealPlan))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.selectedMealPlan).isEqualTo(MealPlanType.CUSTOM)
        }
    }

    @Test
    fun `Add meal to custom meal plan, meal added correctly`() = runTest {
        viewModel.onEvent(MealPlanEvent.OnAddMeal)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.customMealPlan.meals.size).isEqualTo(2)
        }
    }

    @Test
    fun `Delete meal from custom meal plan, meal deleted correctly`() = runTest {
        viewModel.onEvent(MealPlanEvent.OnAddMeal)
        viewModel.onEvent(MealPlanEvent.OnDeleteMeal(1))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.customMealPlan.meals.size).isEqualTo(1)
        }
    }

    @Test
    fun `Rename meal from custom meal plan, meal renamed correctly`() = runTest {
        viewModel.onEvent(MealPlanEvent.OnMealNameChange("Breakfast", 0))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.customMealPlan.meals.first()).isEqualTo("Breakfast")
        }
    }
}