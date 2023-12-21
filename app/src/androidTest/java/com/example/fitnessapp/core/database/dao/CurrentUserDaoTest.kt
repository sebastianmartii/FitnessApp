package com.example.fitnessapp.core.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.database.entity.CurrentUser
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
}