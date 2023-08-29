package com.example.fitnessapp.activities_feature.data.repository

import com.example.fitnessapp.activities_feature.data.local.dao.SavedActivitiesDao
import com.example.fitnessapp.activities_feature.data.local.entity.SavedActivitiesEntity
import com.example.fitnessapp.activities_feature.data.mappers.toActivityList
import com.example.fitnessapp.activities_feature.data.remote.ActivitiesApi
import com.example.fitnessapp.activities_feature.domain.model.Activity
import com.example.fitnessapp.activities_feature.domain.repository.ActivitiesRepository
import com.example.fitnessapp.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ActivitiesRepositoryImpl(
    private val activitiesApi: ActivitiesApi,
    private val savedActivitiesDao: SavedActivitiesDao
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

    override fun getCaloriesBurnedForActivity(
        activityId: String,
        weight: Double,
        duration: Double
    ) {
    }

    override fun getSavedActivities(): Flow<List<SavedActivitiesEntity>> = savedActivitiesDao.getSavedActivities()
}