package com.example.fitnessapp.profile_feature.data.repository

import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.database.entity.CurrentUser
import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.profile_feature.data.mappers.toGenderString
import com.example.fitnessapp.profile_feature.data.mappers.toUserProfile
import com.example.fitnessapp.profile_feature.data.remote.CaloriesGoalApi
import com.example.fitnessapp.profile_feature.data.remote.dto.CaloriesRequirementsDto
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile
import com.example.fitnessapp.profile_feature.domain.repository.ProfileRepository
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel
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
                activityLevel = activityLevel.name,
                gender = gender.name
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
    ): Flow<Resource<CaloriesRequirementsDto>> = flow {
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
                emit(Resource.Success(data = response.body()))
            } else {
                emit(Resource.Error(message = "Request was unsuccessful"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "$e"))
        }
    }

    override suspend fun signIn(userID: Int) {
        currentUserDao.signIn(userID)
    }

    override suspend fun signOut() {
        currentUserDao.signOut()
    }

    override fun getCurrentUser(): Flow<CurrentUser?> = currentUserDao.getCurrentUser()

    override suspend fun changeUserName(userName: String) {
        currentUserDao.updateName(userName)
    }

    override suspend fun changeAge(age: String) {
        currentUserDao.updateAge(age.toInt())
    }

    override suspend fun changeGender(gender: Gender) {
        currentUserDao.updateGender(gender.name)
    }

    override suspend fun changeWeight(weight: String) {
        currentUserDao.updateWeight(weight.toFloat())
    }

    override suspend fun changeHeight(height: String) {
        currentUserDao.updateHeight(height.toFloat())
    }

    override suspend fun changeActivityLevel(activityLevel: ActivityLevel) {
        currentUserDao.updateActivityLevel(activityLevel.name)
    }

    override suspend fun changeCaloriesGoal(caloriesGoal: String) {
        currentUserDao.updateCaloriesGoal(caloriesGoal.toInt())
    }
}