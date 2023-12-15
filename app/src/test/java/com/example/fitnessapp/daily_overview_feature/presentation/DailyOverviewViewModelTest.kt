package com.example.fitnessapp.daily_overview_feature.presentation

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.daily_overview_feature.data.repository.FakeOverviewRepository
import com.example.fitnessapp.daily_overview_feature.domain.model.Activity
import com.google.common.truth.Truth.assertThat
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DailyOverviewViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var currentUserDao: CurrentUserDao

    private lateinit var repo: FakeOverviewRepository
    private lateinit var viewModel: DailyOverviewViewModel

    @Before
    fun setUp() {
        repo = FakeOverviewRepository()
        viewModel = DailyOverviewViewModel(repo, currentUserDao)
    }

    @Test
    fun `Expand meal details when details are empty, snackBar displayed`() = runTest {
        viewModel.onEvent(OverviewEvent.OnMealDetailsExpand("Dinner", true))
        viewModel.snackbarFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo("Nothing to display here")
        }
    }

    @Test
    fun `Expand meal details when details are not empty, meal details expanded correctly`() = runTest {
        viewModel.onEvent(OverviewEvent.OnMealDetailsExpand("Dinner", false))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.mealDetails.first { it.meal == "Dinner" }.areVisible).isTrue()
        }
    }

    @Test
    fun `Reset meal details, meal details reset correctly`() = runTest {
        viewModel.onEvent(OverviewEvent.OnMealReset("Lunch"))
        viewModel.snackbarFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo("Lunch has been cleared")
        }
    }

    @Test
    fun `Delete activity, activity deleted correctly`() = runTest {
        val deletedActivity = Activity(
            name = "Forearms Workout",
            caloriesBurned = 15.0,
            duration = 8.0
        )
        viewModel.onEvent(OverviewEvent.OnActivityDelete(deletedActivity))
        viewModel.snackbarFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo("Forearms Workout activity has been deleted")
        }
    }
}