package com.example.fitnessapp.profile_feature.presentation.profile

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.profile_feature.data.repository.FakeProfileRepository
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel
import com.google.common.truth.Truth.assertThat
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var currentUserDao: CurrentUserDao

    private lateinit var repo: FakeProfileRepository
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        repo = FakeProfileRepository()
        viewModel = ProfileViewModel(repo, currentUserDao)
    }

    @Test
    fun `Change name, name changed correctly`() = runTest {
        viewModel.onEvent(ProfileEvent.OnUserNameChange("new userName"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.userName).isEqualTo("new userName")
        }
    }

    @Test
    fun `Change age, age changed correctly`() = runTest {
        viewModel.onEvent(ProfileEvent.OnAgeChange("22", isAgeValid = true))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.age).isEqualTo("22")
            assertThat(emission.isAgeValid).isTrue()
        }
    }

    @Test
    fun `Change weight, weight changed correctly`() = runTest {
        viewModel.onEvent(ProfileEvent.OnWeightChange("73", isWeightValid = true))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.weight).isEqualTo("73")
            assertThat(emission.isWeightValid).isTrue()
        }
    }

    @Test
    fun `Change height, height changed correctly`() = runTest {
        viewModel.onEvent(ProfileEvent.OnHeightChange("180", isHeightValid = true))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.height).isEqualTo("180")
            assertThat(emission.isHeightValid).isTrue()
        }
    }
    @Test
    fun `Change gender, gender changed correctly`() = runTest {
        viewModel.onEvent(ProfileEvent.OnGenderChange(Gender.MALE))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.gender).isEqualTo(Gender.MALE)
        }
    }

    @Test
    fun `Change activity level, activity level changed correctly`() = runTest {
        viewModel.onEvent(ProfileEvent.OnActivityLevelChange(ActivityLevel.LEVEL_3))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.activityLevel).isEqualTo(ActivityLevel.LEVEL_3)
        }
    }

    @Test
    fun `Change calories goal, calories goal changed correctly`() = runTest {
        viewModel.onEvent(ProfileEvent.OnCaloriesGoalChange("2700", isCaloriesGoalValid = true))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.caloriesGoal).isEqualTo("2700")
            assertThat(emission.isCaloriesGoalValid).isTrue()
        }
    }

    @Test
    fun `Expand activity level, activity level expanded`() = runTest {
        viewModel.onEvent(ProfileEvent.OnActivityLevelExpandedChange(true))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.activityLevelExpanded).isTrue()
        }
    }

    @Test
    fun `Expand gender, gender expanded`() = runTest {
        viewModel.onEvent(ProfileEvent.OnGenderExpandedChange(true))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.genderExpanded).isTrue()
        }
    }

    @Test
    fun `Display user update dialog, user update dialog displayed`() = runTest {
        viewModel.onEvent(ProfileEvent.OnUserUpdateDialogShow(""))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.isUserUpdateDialogVisible).isTrue()
        }
    }

    @Test
    fun `Dismiss user update dialog, user update dialog dismissed`() = runTest {
        viewModel.onEvent(ProfileEvent.OnUserUpdateDialogShow(""))
        viewModel.onEvent(ProfileEvent.OnUserUpdateDialogDismiss)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.isUserUpdateDialogVisible).isFalse()
        }
    }

    @Test
    fun `Decline user update dialog, dialog declined navigation performed`() = runTest {
        viewModel.onEvent(ProfileEvent.OnUserUpdateDialogDecline)
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(ProfileViewModel.UiEvent.Navigate(""))
        }
    }

    @Test
    fun `Update user info with valid changes, user updated, pending navigation performed`() = runTest {
        viewModel.onEvent(ProfileEvent.OnUserUpdateDialogShow("overview"))
        viewModel.onEvent(ProfileEvent.OnUserUpdate(
            ProfileState(
                age = "25",
                height = "185"
            ),
            isAgeValid = true,
            isHeightValid = true,
            isWeightValid = true,
            isCaloriesGoalValid = true,
        ))
        assertThat(repo.getCurrentUserAge()).isEqualTo("25")
        assertThat(repo.getCurrentUserHeight()).isEqualTo("185")
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(ProfileViewModel.UiEvent.Navigate("overview"))
        }
    }

    @Test
    fun `Update user info with invalid changes, error message displayed, validators active`() = runTest {
        viewModel.onEvent(ProfileEvent.OnUserUpdate(
            ProfileState(),
            isAgeValid = true,
            isHeightValid = false,
            isWeightValid = true,
            isCaloriesGoalValid = true,
        ))
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(ProfileViewModel.UiEvent.ShowSnackbar(
                "Some Of The Updated Fields Are Not Valid, Please Go Over All Changes And Try Again"
            ))
        }
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.shouldUseValidators).isTrue()
            assertThat(emission.isCaloriesGoalValid).isTrue()
            assertThat(emission.isHeightValid).isFalse()
            assertThat(emission.isWeightValid).isTrue()
            assertThat(emission.isAgeValid).isTrue()
        }
    }
}