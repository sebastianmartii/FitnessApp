package com.example.fitnessapp.profile_feature.data.repository

import com.example.fitnessapp.core.database.entity.CurrentUser
import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.profile_feature.data.remote.dto.CaloriesGoal
import com.example.fitnessapp.profile_feature.data.remote.dto.CaloriesRequirementsDto
import com.example.fitnessapp.profile_feature.data.remote.dto.Data
import com.example.fitnessapp.profile_feature.data.remote.dto.Goals
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile
import com.example.fitnessapp.profile_feature.domain.repository.ProfileRepository
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProfileRepository : ProfileRepository {

    private val profileList = mutableListOf(
        UserProfile(
            userID = 0,
            name = "Sebastian",
            gender = Gender.MALE
        ),
        UserProfile(
            userID = 1,
            name = "Fiona",
            gender = Gender.FEMALE
        )
    )

    private val userList = mutableListOf(
        CurrentUser(
            userID = 0,
            name = "Sebastian",
            caloriesGoal = 2600,
            age = 21,
            weight = 70f,
            height = 176f,
            gender = "MALE",
            activityLevel = "LEVEL_5",
            isSignedIn = false
        )
    )

    private var currentUser: CurrentUser? = null

    private var currentUserAge = ""

    private var currentUserHeight = ""

    override suspend fun addUser(
        name: String,
        age: Int,
        height: Float,
        weight: Float,
        gender: Gender,
        activityLevel: ActivityLevel,
        caloriesGoal: Int
    ) {
        val nextProfileId = profileList.last().userID + 1
        val nextUserId = userList.last().userID?.plus(1)
        profileList.add(UserProfile(userID = nextProfileId, name = name, gender = gender))
        userList.add(
            CurrentUser(
                userID = nextUserId,
                name = name,
                caloriesGoal = caloriesGoal,
                age = age,
                weight = weight,
                height = height,
                gender = gender.name,
                activityLevel = activityLevel.name,
                isSignedIn = false
            )
        )
    }

    override suspend fun getUserProfiles(): List<UserProfile> = profileList

    override fun getCaloriesGoals(
        age: Int,
        height: Int,
        weight: Int,
        gender: Gender,
        activityLevel: ActivityLevel
    ): Flow<Resource<CaloriesRequirementsDto>> = flow {
        val result = CaloriesRequirementsDto(
            Data(
                BMR = 0.0,
                goals = Goals(
                    maintainWeight = 2300.0,
                    mildWeightLoss = CaloriesGoal(
                        gainWeight = null,
                        lossWeight = null,
                        calories = 2000.0
                    ),
                    weightLoss = CaloriesGoal(
                        gainWeight = null,
                        lossWeight = null,
                        calories = 1800.0
                    ),
                    extremeWeightLoss = CaloriesGoal(
                        gainWeight = null,
                        lossWeight = null,
                        calories = 1500.0
                    ),
                    mildWeightGain = CaloriesGoal(
                        gainWeight = null,
                        lossWeight = null,
                        calories = 2500.0
                    ),
                    weightGain = CaloriesGoal(
                        gainWeight = null,
                        lossWeight = null,
                        calories = 2700.0
                    ),
                    extremeWeightGain = CaloriesGoal(
                        gainWeight = null,
                        lossWeight = null,
                        calories = 3000.0
                    )
                )
            )
        )
        emit(Resource.Success(data = result))
    }

    override suspend fun signIn(userID: Int) {
        currentUser = userList.firstOrNull { it.userID == userID }
    }

    override suspend fun signOut() {
        currentUser = null
    }

    override fun getCurrentUser(): Flow<CurrentUser?> = flow {
        emit(currentUser)
    }

    override suspend fun changeUserName(userName: String) {}

    override suspend fun changeAge(age: String) {
        currentUserAge = age
    }

    override suspend fun changeGender(gender: Gender) {}

    override suspend fun changeWeight(weight: String) {}

    override suspend fun changeHeight(height: String) {
        currentUserHeight = height
    }

    override suspend fun changeActivityLevel(activityLevel: ActivityLevel) {}

    override suspend fun changeCaloriesGoal(caloriesGoal: String) {}

    fun getCurrentUserAge() = currentUserAge

    fun getCurrentUserHeight() = currentUserHeight
}