package com.example.fitnessapp.history_feature.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.history_feature.data.local.entity.ActivityHistoryEntity
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
class ActivityHistoryDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: FitnessDatabase
    private lateinit var activityHistoryDao: ActivityHistoryDao

    @Before
    fun setUp() {
        hiltRule.inject()
        activityHistoryDao = db.activityHistoryDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addActivitiesToHistory_ActivitiesAddedCorrectly() = runTest {
        val activity = ActivityHistoryEntity(
            id = 1,
            name = "running",
            caloriesBurned = "500",
            duration = 30.0,
            year = 2023,
            month = 12,
            day = 23
        )
        activityHistoryDao.addToHistory(listOf(activity))
        val activitiesHistory = activityHistoryDao.getMonthActivities(12).first()
        assertThat(activitiesHistory).contains(activity)
    }
}