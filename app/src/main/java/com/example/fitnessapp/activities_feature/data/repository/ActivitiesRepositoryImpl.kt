package com.example.fitnessapp.activities_feature.data.repository

import com.example.fitnessapp.activities_feature.data.local.dao.SavedActivitiesDao
import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import com.example.fitnessapp.activities_feature.data.mappers.toActivityList
import com.example.fitnessapp.activities_feature.data.mappers.toDailyActivity
import com.example.fitnessapp.activities_feature.data.remote.ActivitiesApi
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity
import com.example.fitnessapp.activities_feature.domain.repository.ActivitiesRepository
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyActivitiesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ActivitiesRepositoryImpl(
    private val activitiesApi: ActivitiesApi,
    private val savedActivitiesDao: SavedActivitiesDao,
    private val currentUserDao: CurrentUserDao,
    private val dailyActivitiesDao: DailyActivitiesDao
) : ActivitiesRepository {

    override fun getActivitiesForIntensityLevel(intensityLevel: Int): Flow<Resource<List<Activity>>> = flow {
        emit(Resource.Loading())

        try {
            val response = activitiesApi.getActivitiesPerIntensity(intensityLevel)
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()?.data?.map { it.toActivityList() }))
            } else {
                emit(Resource.Error(message = "${response.errorBody()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "$e"))
        }
    }

    override suspend fun getCaloriesBurnedForActivity(
        activity: Activity,
        duration: Double
    ) {
        val currentUserWeight = currentUserDao.getCurrentUserWeight()
        try {
            val response = activitiesApi.getCaloriesFromActivity(
                activity.id,
                duration,
                currentUserWeight.toDouble()
            )
            if (response.isSuccessful) {
                savedActivitiesDao.saveActivity(
                    SavedActivitiesEntity(
                        activity = activity.name,
                        description = activity.description,
                        duration = duration,
                        caloriesBurned = response.body()!!.data.burnedCalorie
                    )
                )
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    override fun getSavedActivities(): Flow<List<SavedActivitiesEntity>> = savedActivitiesDao.getSavedActivities()

    override suspend fun saveActivity(name: String, description: String?, duration: Double, burnedCalories: String) {
        savedActivitiesDao.saveActivity(
            SavedActivitiesEntity(
                activity = name,
                description = description,
                duration = duration,
                caloriesBurned = burnedCalories
            )
        )
    }

    override suspend fun deleteSavedActivities(activities: List<SavedActivity>) {
        savedActivitiesDao.deleteSavedActivities(activities)
    }

    override suspend fun performSavedActivities(activities: List<SavedActivity>) {
        dailyActivitiesDao.addActivities(activities.map { it.toDailyActivity() })
    }
}