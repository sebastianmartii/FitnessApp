package com.example.fitnessapp.profile_feature.domain.repository

import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel
import com.example.fitnessapp.profile_feature.presentation.sign_in.CalculatedCalories
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun addUser(name: String, age: Int, height: Float, weight: Float, gender: Gender, activityLevel: ActivityLevel, caloriesGoal: Int)

    suspend fun getUserProfiles(): List<UserProfile>

    fun getCaloriesGoals(age: Int, height: Int, weight: Int, gender: Gender, activityLevel: ActivityLevel): Flow<Resource<List<CalculatedCalories>>>
}