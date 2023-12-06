package com.example.fitnessapp.activities_feature.presentation.activity_creator

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.activities_feature.data.repository.FakeActivitiesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ActivityCreatorViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    private lateinit var repo: FakeActivitiesRepository
    private lateinit var viewModel: ActivityCreatorViewModel

    @Before
    fun setUp() {
        repo = FakeActivitiesRepository()
        viewModel = ActivityCreatorViewModel(repo)
    }

    @Test
    fun `Change burned calories, burned calories changed correctly`() = runTest {
        viewModel.onEvent(ActivityCreatorEvent.OnActivityBurnedCaloriesChange("200"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.burnedCalories).isEqualTo("200")
        }
    }

    @Test
    fun `Change description, description changed correctly`() = runTest {
        viewModel.onEvent(ActivityCreatorEvent.OnActivityDescriptionChange("running"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.description).isEqualTo("running")
        }
    }

    @Test
    fun `Change minutes, minutes changed correctly`() = runTest {
        viewModel.onEvent(ActivityCreatorEvent.OnActivityMinutesChange("2"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.minutes).isEqualTo("2")
        }
    }

    @Test
    fun `Change seconds, seconds changed correctly`() = runTest {
        viewModel.onEvent(ActivityCreatorEvent.OnActivitySecondsChange("30"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.seconds).isEqualTo("30")
        }
    }

    @Test
    fun `Change activity name, activity name changed correctly`() = runTest {
        viewModel.onEvent(ActivityCreatorEvent.OnActivityNameChange("running"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.name).isEqualTo("running")
        }
    }

    @Test
    fun `Save correctly created activity, ui event navigate back channeled`() = runTest {
        viewModel.onEvent(ActivityCreatorEvent.OnActivitySave("", "", 0.0, "", true))
        viewModel.snackbarFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(ActivityCreatorViewModel.UiEvent.NavigateBack)
        }
    }

    @Test
    fun `Save incorrectly created activity, ui event show snackbar channeled`() = runTest {
        viewModel.onEvent(ActivityCreatorEvent.OnActivitySave("", "", 0.0, "", false))
        viewModel.snackbarFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(ActivityCreatorViewModel.UiEvent.ShowSnackbar("Provide Valid Name, Duration and Calories to Create Activity"))
        }
    }
}