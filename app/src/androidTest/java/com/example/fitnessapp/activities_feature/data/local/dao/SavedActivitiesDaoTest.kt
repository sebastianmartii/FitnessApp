package com.example.fitnessapp.activities_feature.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import com.example.fitnessapp.activities_feature.data.mappers.toSavedActivity
import com.example.fitnessapp.core.database.FitnessDatabase
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
class SavedActivitiesDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: FitnessDatabase
    private lateinit var savedActivitiesDao: SavedActivitiesDao

    @Before
    fun setUp() {
        hiltRule.inject()
        savedActivitiesDao = db.savedActivitiesDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun saveActivity_ActivitySavedCorrectly() = runTest {
        val activity = SavedActivitiesEntity(
            id = 1,
            activity = "running",
            description = "running",
            caloriesBurned = "500",
            duration = 30.0
        )
        savedActivitiesDao.saveActivity(activity)
        val savedActivities = savedActivitiesDao.getSavedActivities().first()
        assertThat(savedActivities).contains(activity)
    }

    @Test
    fun deleteSavedActivities_ActivitiesDeletedCorrectly() = runTest {
        val activityOne = SavedActivitiesEntity(
            id = 1,
            activity = "running",
            description = "running",
            caloriesBurned = "500",
            duration = 30.0
        )
        val activityTwo = SavedActivitiesEntity(
            id = 2,
            activity = "swimming",
            description = "swimming",
            caloriesBurned = "300",
            duration = 30.0
        )
        savedActivitiesDao.saveActivity(activityOne)
        savedActivitiesDao.saveActivity(activityTwo)
        val savedActivities = savedActivitiesDao.getSavedActivities().first()
        assertThat(savedActivities).contains(activityOne)
        assertThat(savedActivities).contains(activityTwo)
        savedActivitiesDao.deleteSavedActivities(
            listOf(
                activityOne.toSavedActivity(),
                activityTwo.toSavedActivity()
            )
        )
        val savedActivitiesUpdated = savedActivitiesDao.getSavedActivities().first()
        assertThat(savedActivitiesUpdated).isEmpty()
    }
}