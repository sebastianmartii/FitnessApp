package com.example.fitnessapp.daily_overview_feature.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.daily_overview_feature.data.local.entity.DailyActivitiesEntity
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
class DailyActivitiesDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: FitnessDatabase
    private lateinit var dailyActivitiesDao: DailyActivitiesDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dailyActivitiesDao = db.dailyActivitiesDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addActivityToDailyOverview_ActivityAddedCorrectly() = runTest {
        val activity = DailyActivitiesEntity(
            id = 1,
            name = "running",
            caloriesBurned = "500",
            duration = 30.0
        )
        dailyActivitiesDao.addActivities(listOf(activity))
        val dailyActivities = dailyActivitiesDao.getDailyActivities().first()
        assertThat(dailyActivities).contains(activity)
    }

    @Test
    fun deleteActivityFromDailyOverview_ActivityDeletedCorrectly() = runTest {
        val activity = DailyActivitiesEntity(
            id = 1,
            name = "running",
            caloriesBurned = "500",
            duration = 30.0
        )
        dailyActivitiesDao.addActivities(listOf(activity))
        val dailyActivities = dailyActivitiesDao.getDailyActivities().first()
        assertThat(dailyActivities).contains(activity)
        dailyActivitiesDao.deleteActivity("running")
        val dailyActivitiesUpdated = dailyActivitiesDao.getDailyActivities().first()
        assertThat(dailyActivitiesUpdated).doesNotContain(activity)
    }

    @Test
    fun resetDailyActivities_ActivitiesResetCorrectly() = runTest {
        val activityOne = DailyActivitiesEntity(
            id = 1,
            name = "running",
            caloriesBurned = "500",
            duration = 30.0
        )
        val activityTwo = DailyActivitiesEntity(
            id = 2,
            name = "swimming",
            caloriesBurned = "500",
            duration = 60.0
        )
        val activityThree = DailyActivitiesEntity(
            id = 3,
            name = "core workout",
            caloriesBurned = "50",
            duration = 3.0
        )
        dailyActivitiesDao.addActivities(listOf(activityOne))
        dailyActivitiesDao.addActivities(listOf(activityTwo))
        dailyActivitiesDao.addActivities(listOf(activityThree))
        val dailyActivities = dailyActivitiesDao.getDailyActivities().first()
        assertThat(dailyActivities).contains(activityOne)
        assertThat(dailyActivities).contains(activityTwo)
        assertThat(dailyActivities).contains(activityThree)
        dailyActivitiesDao.resetDailyActivities()
        val dailyActivitiesUpdated = dailyActivitiesDao.getDailyActivities().first()
        assertThat(dailyActivitiesUpdated).isEmpty()
    }
}