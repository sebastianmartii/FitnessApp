package com.example.fitnessapp.nutrition_calculator_feature.presentation.recipe

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.nutrition_calculator_feature.data.repository.FakeRecipesRepository
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Recipe
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecipeSearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repo: FakeRecipesRepository
    private lateinit var viewModel: RecipeSearchViewModel

    @Before
    fun setUp() {
        repo = FakeRecipesRepository()
        viewModel = RecipeSearchViewModel(repo)
    }

    @Test
    fun `Save recipe, recipe is saved correctly`() = runTest {
        val recipe = Recipe(
            label = "",
            smallImage = "",
            bigImage = "",
            dietLabels = emptyList(),
            ingredients = emptyList(),
            calories = 0.0,
            servingSize = 0.0,
            carbs = 0.0,
            fat = 0.0,
            saturatedFat = 0.0,
            protein = 0.0,
            fiber = 0.0,
            sugar = 0.0,
            externalUrl = ""
        )
        viewModel.onEvent(RecipeSearchEvent.OnIsRecipeSavedChange(recipe, false))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.savedRecipes).contains(recipe)
        }
    }

    @Test
    fun `Delete recipe, recipe is deleted correctly`() = runTest {
        val recipe = Recipe(
            label = "",
            smallImage = "",
            bigImage = "",
            dietLabels = emptyList(),
            ingredients = emptyList(),
            calories = 0.0,
            servingSize = 0.0,
            carbs = 0.0,
            fat = 0.0,
            saturatedFat = 0.0,
            protein = 0.0,
            fiber = 0.0,
            sugar = 0.0,
            externalUrl = ""
        )
        viewModel.onEvent(RecipeSearchEvent.OnIsRecipeSavedChange(recipe, true))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.savedRecipes).doesNotContain(recipe)
        }
    }

    @Test
    fun `Change search query, query is changed correctly`() = runTest {
        viewModel.onEvent(RecipeSearchEvent.OnQueryChange("test query"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.query).isEqualTo("test query")
        }
    }

    @Test
    fun `Clear search query, query is cleared correctly`() = runTest {
        viewModel.onEvent(RecipeSearchEvent.OnQueryClear)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.query).isEmpty()
        }
    }

    @Test
    fun `Display or hide search bar, search bar is displayed or hidden`() = runTest {
        viewModel.onEvent(RecipeSearchEvent.OnIsSearchBarActiveChange(true))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.isSearchBarActive).isTrue()
        }
    }

    @Test
    fun `Navigate to recipe details, recipe is chosen correctly`() = runTest {
        val recipe = Recipe(
            label = "",
            smallImage = "",
            bigImage = "",
            dietLabels = emptyList(),
            ingredients = emptyList(),
            calories = 0.0,
            servingSize = 0.0,
            carbs = 0.0,
            fat = 0.0,
            saturatedFat = 0.0,
            protein = 0.0,
            fiber = 0.0,
            sugar = 0.0,
            externalUrl = ""
        )
        viewModel.onEvent(RecipeSearchEvent.OnNavigateToRecipeDetails(1, true))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.isRecipeSaved).isTrue()
            assertThat(emission.inspectedRecipe).isEqualTo(recipe)
        }
    }
}