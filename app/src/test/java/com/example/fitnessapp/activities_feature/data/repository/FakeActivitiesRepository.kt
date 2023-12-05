package com.example.fitnessapp.activities_feature.data.repository

import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity
import com.example.fitnessapp.activities_feature.domain.repository.ActivitiesRepository
import com.example.fitnessapp.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeActivitiesRepository : ActivitiesRepository {

    private val savedActivities = mutableListOf(
        SavedActivitiesEntity(
            id = 1,
            activity = "running",
            description = "running",
            caloriesBurned = "200",
            duration = 30.0
        ),
        SavedActivitiesEntity(
            id = 2,
            activity = "biking",
            description = "riding a bike",
            caloriesBurned = "100",
            duration = 25.0
        ),
        SavedActivitiesEntity(
            id = 3,
            activity = "running",
            description = "marathon",
            caloriesBurned = "2000",
            duration = 180.0
        ),
        SavedActivitiesEntity(
            id = 4,
            activity = "stretching",
            description = "stretching",
            caloriesBurned = "2",
            duration = 3.0
        ),
        SavedActivitiesEntity(
            id = 5,
            activity = "swimming",
            description = "swimming",
            caloriesBurned = "150",
            duration = 30.0
        )
    )

    override fun getActivitiesForIntensityLevel(intensityLevel: Int): Flow<Resource<List<Activity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCaloriesBurnedForActivity(activity: Activity, duration: Double) {}

    override fun getSavedActivities(): Flow<List<SavedActivitiesEntity>> = flow {
        emit(savedActivities)
    }

    override suspend fun saveActivity(
        name: String,
        description: String?,
        duration: Double,
        burnedCalories: String
    ) {
        savedActivities.add(
            SavedActivitiesEntity(
                id = savedActivities.lastIndex + 2,
                activity = name,
                description = description,
                caloriesBurned = burnedCalories,
                duration = duration
            )
        )
    }

    override suspend fun deleteSavedActivities(activities: List<SavedActivity>) {
        activities.onEach { activityToDelete ->
            savedActivities.removeIf { savedActivity ->
                savedActivity.activity == activityToDelete.name && savedActivity.description == activityToDelete.description
                        && savedActivity.duration == activityToDelete.duration && savedActivity.caloriesBurned == activityToDelete.burnedCalories
            }
        }
    }

    override suspend fun performSavedActivities(activities: List<SavedActivity>) {}
}