package com.example.fitnessapp.profile_feature.data.repository

import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.database.entity.CurrentUser
import com.example.fitnessapp.profile_feature.data.mappers.toGenderString
import com.example.fitnessapp.profile_feature.data.mappers.toUserProfile
import com.example.fitnessapp.profile_feature.data.remote.CaloriesGoalApi
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile
import com.example.fitnessapp.profile_feature.domain.repository.ProfileRepository
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel
import com.example.fitnessapp.profile_feature.presentation.sign_in.toActivityLevelString

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

    override suspend fun getCaloriesGoals(
        age: Int,
        height: Float,
        weight: Float,
        gender: Gender,
        activityLevel: ActivityLevel
    ) {
        caloriesGoalApi.getCaloriesRequirements(
            age = age,
            gender = gender.toGenderString(),
            height = height,
            weight = weight,
            activityLevel = activityLevel.name.lowercase()
        )
    }
}