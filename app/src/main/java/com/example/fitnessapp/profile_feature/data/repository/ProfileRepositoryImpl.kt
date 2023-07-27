package com.example.fitnessapp.profile_feature.data.repository

import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.database.entity.CurrentUser
import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.profile_feature.data.mappers.toCalculatedCaloriesList
import com.example.fitnessapp.profile_feature.data.mappers.toGenderString
import com.example.fitnessapp.profile_feature.data.mappers.toUserProfile
import com.example.fitnessapp.profile_feature.data.remote.CaloriesGoalApi
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile
import com.example.fitnessapp.profile_feature.domain.repository.ProfileRepository
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel
import com.example.fitnessapp.profile_feature.presentation.sign_in.CalculatedCalories
import com.example.fitnessapp.profile_feature.presentation.sign_in.toActivityLevelString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl(
    private val currentUserDao: CurrentUserDao,
    private val caloriesGoalApi: CaloriesGoalApi
) : ProfileRepository {

    override suspend fun addUser(
        name: String,
        age: Int,
        height: Float,
        weight: Float,
        gender: Gender,
        activityLevel: ActivityLevel,
        caloriesGoal: Int
    ) {
        currentUserDao.addUser(
            CurrentUser(
                name = name,
                age = age,
                height = height,
                weight = weight,
                caloriesGoal = caloriesGoal,
                activityLevel = activityLevel.toActivityLevelString(),
                gender = gender.toGenderString()
            )
        )
    }

    override suspend fun getUserProfiles(): List<UserProfile> {
        return currentUserDao.getAllUsers().map { it.toUserProfile() }
    }

    override fun getCaloriesGoals(
        age: Int,
        height: Int,
        weight: Int,
        gender: Gender,
        activityLevel: ActivityLevel
    ): Flow<Resource<List<CalculatedCalories>>> = flow {
        emit(Resource.Loading())

        try {
            val response = caloriesGoalApi.getCaloriesRequirements(
                age,
                gender.toGenderString(),
                height,
                weight,
                activityLevel.name.lowercase()
            )
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()!!.toCalculatedCaloriesList()))
            } else {
                emit(Resource.Error(message = "Request was unsuccessful"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "$e"))
        }
    }
}