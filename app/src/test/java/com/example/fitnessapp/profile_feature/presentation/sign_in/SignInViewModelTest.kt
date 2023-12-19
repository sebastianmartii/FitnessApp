package com.example.fitnessapp.profile_feature.presentation.sign_in

import app.cash.turbine.test
import com.example.fitnessapp.MainDispatcherRule
import com.example.fitnessapp.profile_feature.data.repository.FakeProfileRepository
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignInViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repo: FakeProfileRepository
    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        repo = FakeProfileRepository()
        viewModel = SignInViewModel(repo)
    }

    @Test
    fun `Change name, name changed correctly`() = runTest {
        viewModel.onEvent(SignInEvent.OnNameChange("new name"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.name).isEqualTo("new name")
        }
    }

    @Test
    fun `Change gender, gender changed correctly`() = runTest {
        viewModel.onEvent(SignInEvent.OnGenderChange(Gender.MALE))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.gender).isEqualTo(Gender.MALE)
        }
    }

    @Test
    fun `Change age, age changed correctly`() = runTest {
        viewModel.onEvent(SignInEvent.OnAgeChange("21"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.age).isEqualTo("21")
        }
    }

    @Test
    fun `Change weight, weight changed correctly`() = runTest {
        viewModel.onEvent(SignInEvent.OnWeightChange("70"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.weight).isEqualTo("70")
        }
    }

    @Test
    fun `Change height, height changed correctly`() = runTest {
        viewModel.onEvent(SignInEvent.OnHeightChange("176"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.height).isEqualTo("176")
        }
    }

    @Test
    fun `Change activity level, activity level changed correctly`() = runTest {
        viewModel.onEvent(SignInEvent.OnActivityLevelChange(ActivityLevel.LEVEL_5))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.activityLevel).isEqualTo(ActivityLevel.LEVEL_5)
        }
    }
    @Test
    fun `Change calories goal, calories goal changed correctly`() = runTest {
        viewModel.onEvent(SignInEvent.OnCaloriesGoalChange("2800"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.caloriesGoal).isEqualTo("2800")
        }
    }

    @Test
    fun `Calculate calories with user info, calories calculated accordingly`() = runTest {
        viewModel.onEvent(SignInEvent.OnAgeChange("21"))
        viewModel.onEvent(SignInEvent.OnHeightChange("176"))
        viewModel.onEvent(SignInEvent.OnWeightChange("70"))
        viewModel.onEvent(SignInEvent.OnGenderChange(Gender.MALE))
        viewModel.onEvent(SignInEvent.OnActivityLevelChange(ActivityLevel.LEVEL_5))
        viewModel.calculateCalories()
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.calculatedCalories).isNotEmpty()
        }
    }

    @Test
    fun `Add profile and display profile list, user added and profile list displayed correctly`() = runTest {
        viewModel.onEvent(SignInEvent.OnNameChange("Karol"))
        viewModel.onEvent(SignInEvent.OnAgeChange("32"))
        viewModel.onEvent(SignInEvent.OnHeightChange("168"))
        viewModel.onEvent(SignInEvent.OnWeightChange("59"))
        viewModel.onEvent(SignInEvent.OnGenderChange(Gender.MALE))
        viewModel.onEvent(SignInEvent.OnActivityLevelChange(ActivityLevel.LEVEL_3))
        viewModel.onEvent(SignInEvent.OnCaloriesGoalChange("2300"))
        viewModel.onEvent(SignInEvent.OnSignInComplete)
        viewModel.onEvent(SignInEvent.OnGetExistingProfiles)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.profileList).contains(UserProfile(2, "Karol", Gender.MALE))
        }
    }

    @Test
    fun `Sign in with existing profile, profile signed in correctly`() = runTest {
        viewModel.onEvent(SignInEvent.OnSignInWithExistingProfile(0))
        val currentUser = repo.getCurrentUser()
        currentUser.test {
            val emission = awaitItem()
            assertThat(emission?.userID).isEqualTo(0)
            awaitComplete()
        }
    }
}