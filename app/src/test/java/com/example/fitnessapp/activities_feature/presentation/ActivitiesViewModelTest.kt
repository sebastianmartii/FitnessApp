package com.example.fitnessapp.activities_feature.presentation

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.activities_feature.data.repository.FakeActivitiesRepository
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.google.common.truth.Truth.assertThat
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ActivitiesViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var currentUserDao: CurrentUserDao

    private lateinit var repo: FakeActivitiesRepository
    private lateinit var viewModel: ActivitiesViewModel

    @Before
    fun setUp() {
        repo = FakeActivitiesRepository()
        viewModel = ActivitiesViewModel(repo, currentUserDao)
    }

    @Test
    fun `Change filter query, filter query updated correctly`() = runTest {
        viewModel.onEvent(ActivitiesEvent.OnFilterQueryChange("running"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.filterQuery).isEqualTo("running")
        }
    }

    @Test
    fun `Clear filter query, filter query cleared`() = runTest {
        viewModel.onEvent(ActivitiesEvent.OnFilterQueryChange("running"))
        viewModel.onEvent(ActivitiesEvent.OnFilterQueryClear)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.filterQuery).isEqualTo("")
        }
    }

    @Test
    fun `Remove saved activities, activities removed correctly`() = runTest {
        val activityToDelete = SavedActivity(
            name = "flutter kicks",
            description = "ab workout",
            duration = 0.30,
            burnedCalories = "20"
        )
        repo.saveActivity(
            name = "flutter kicks",
            description = "ab workout",
            duration = 0.30,
            burnedCalories = "20"
        )
        viewModel.onEvent(ActivitiesEvent.OnSavedActivitiesDelete(
            listOf(
                activityToDelete
            )
        ))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.savedActivities).doesNotContain(activityToDelete)
        }
    }

    @Test
    fun `Change minutes, minutes changed correctly`() = runTest {
        viewModel.onEvent(ActivitiesEvent.OnMinutesChange("2"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.minutes).isEqualTo("2")
        }
    }

    @Test
    fun `Change seconds, seconds changed correctly`() = runTest {
        viewModel.onEvent(ActivitiesEvent.OnSecondsChange("45"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.seconds).isEqualTo("45")
        }
    }

    @Test
    fun `Filter activities, activities are filtered`() = runTest {
        viewModel.onEvent(ActivitiesEvent.OnFilterActivities(true))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.areActivitiesFiltered).isTrue()
        }
    }

    @Test
    fun `Save activity, burned calories dialog displayed for selected activity`() = runTest {
        val selectedActivity = Activity(
            id = "2",
            name = "running",
            description = "running"
        )
        viewModel.onEvent(ActivitiesEvent.OnActivityClick(selectedActivity))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.isBurnedCaloriesDialogVisible).isTrue()
            assertThat(emission.chosenActivity).isEqualTo(selectedActivity)
        }
    }

    @Test
    fun `Dismiss burned calories dialog, dialog dismissed`() = runTest {
        viewModel.onEvent(ActivitiesEvent.OnBurnedCaloriesDialogDismiss)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.isBurnedCaloriesDialogVisible).isFalse()
            assertThat(emission.minutes).isEmpty()
            assertThat(emission.seconds).isEmpty()
        }
    }

    @Test
    fun `Confirm burned calories dialog, dialog confirmed`() = runTest {
        val selectedActivity = Activity(
            id = "2",
            name = "running",
            description = "running"
        )
        viewModel.onEvent(ActivitiesEvent.OnBurnedCaloriesDialogConfirm(selectedActivity, 30.0))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.minutes).isEmpty()
            assertThat(emission.seconds).isEmpty()
            assertThat(emission.chosenActivity).isNull()
        }
    }
}