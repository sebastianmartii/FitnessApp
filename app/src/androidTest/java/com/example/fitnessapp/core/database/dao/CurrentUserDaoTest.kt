package com.example.fitnessapp.core.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.database.entity.CurrentUser
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class CurrentUserDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: FitnessDatabase
    private lateinit var currentUserDao: CurrentUserDao


    @Before
    fun setUp() {
        hiltRule.inject()
        currentUserDao = db.currentUserDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addUser_UserAddedCorrectly() = runTest {
        val user = CurrentUser(
            userID = 1,
            name = "Karol",
            caloriesGoal = 2600,
            age = 24,
            gender = "male",
            weight = 78f,
            height = 182f,
            activityLevel = "level_2",
            isSignedIn = false
        )
        currentUserDao.addUser(user)
        val allUsers = currentUserDao.getAllUsers()
        assertThat(allUsers).contains(user)
    }

    @Test
    fun deleteUser_UserDeletedCorrectly() = runTest {
        val user = CurrentUser(
            userID = 2,
            name = "Pawel",
            caloriesGoal = 2600,
            age = 24,
            gender = "male",
            weight = 78f,
            height = 182f,
            activityLevel = "level_2",
            isSignedIn = false
        )
        currentUserDao.addUser(user)
        val usersBefore = currentUserDao.getAllUsers()
        assertThat(usersBefore).contains(user)
        currentUserDao.deleteUser(user)
        val usersAfter = currentUserDao.getAllUsers()
        assertThat(usersAfter).doesNotContain(user)
    }

    @Test
    fun updateUserInfo_UserInfoUpdatedCorrectly() = runTest {
        val user = CurrentUser(
            userID = 1,
            name = "Karol",
            caloriesGoal = 2600,
            age = 24,
            gender = "male",
            weight = 78f,
            height = 182f,
            activityLevel = "level_2",
            isSignedIn = true
        )
        currentUserDao.addUser(user)
        val currentUser = currentUserDao.getCurrentUser().first()
        assertThat(currentUser).isEqualTo(user)
        currentUserDao.updateName("Karolina")
        currentUserDao.updateAge(21)
        currentUserDao.updateGender("female")
        currentUserDao.updateCaloriesGoal(2200)
        val updatedUser = currentUserDao.getCurrentUser().first()
        assertThat(updatedUser?.name).isEqualTo("Karolina")
        assertThat(updatedUser?.age).isEqualTo(21)
        assertThat(updatedUser?.gender).isEqualTo("female")
        assertThat(updatedUser?.caloriesGoal).isEqualTo(2200)
    }

    @Test
    fun signInAndSignOut_UserIsSignedInAndThenSignedOut() = runTest {
        val user = CurrentUser(
            userID = 1,
            name = "Karol",
            caloriesGoal = 2600,
            age = 24,
            gender = "male",
            weight = 78f,
            height = 182f,
            activityLevel = "level_2",
            isSignedIn = false
        )
        currentUserDao.addUser(user)
        currentUserDao.signIn(1)
        assertThat(currentUserDao.getCurrentUser().first()).isEqualTo(user.copy(isSignedIn = true))
        currentUserDao.signOut()
        assertThat(currentUserDao.getCurrentUser().first()).isNull()
    }

    @Test
    fun getUserWeightAndCaloriesGoal_WeightAndCaloriesGoalRetrievedCorrectly() = runTest {
        val user = CurrentUser(
            userID = 1,
            name = "Karol",
            caloriesGoal = 2600,
            age = 24,
            gender = "male",
            weight = 78f,
            height = 182f,
            activityLevel = "level_2",
            isSignedIn = true
        )
        currentUserDao.addUser(user)
        val currentUserWeight = currentUserDao.getCurrentUserWeight()
        val currentUserCaloriesGoal = currentUserDao.getCurrentUserCaloriesRequirements().first()
        assertThat(currentUserWeight).isEqualTo(78f)
        assertThat(currentUserCaloriesGoal).isEqualTo(2600)
    }
}