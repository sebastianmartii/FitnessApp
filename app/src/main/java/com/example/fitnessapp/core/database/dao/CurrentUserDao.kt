package com.example.fitnessapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessapp.core.database.entity.CurrentUser
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentUserDao {

    @Insert
    suspend fun addUser(user: CurrentUser)

    @Delete
    suspend fun deleteUser(user: CurrentUser)

    @Query("UPDATE CurrentUser SET name = :newName WHERE user_id = :userID")
    suspend fun updateName(newName: String, userID: Int)

    @Query("UPDATE CurrentUser SET age = :newAge WHERE user_id = :userID")
    suspend fun updateAge(newAge: Int, userID: Int)

    @Query("UPDATE CurrentUser SET gender = :newGender WHERE user_id = :userID")
    suspend fun updateGender(newGender: String, userID: Int)

    @Query("UPDATE CurrentUser SET height = :newHeight WHERE user_id = :userID")
    suspend fun updateHeight(newHeight: Float, userID: Int)

    @Query("UPDATE CurrentUser SET weight = :newWeight WHERE user_id = :userID")
    suspend fun updateWeight(newWeight: Float, userID: Int)

    @Query("UPDATE CurrentUser SET calories_goal = :newCaloriesGoal WHERE user_id = :userID")
    suspend fun updateCaloriesGoal(newCaloriesGoal: Int, userID: Int)

    @Query("UPDATE CurrentUser SET activity_level = :newActivityLevel WHERE user_id = :userID")
    suspend fun updateActivityLevel(newActivityLevel: String, userID: Int)

    @Query("SELECT * FROM CurrentUser WHERE is_signed_in = 1")
    fun getCurrentUser(): Flow<CurrentUser?>

    @Query("SELECT weight FROM CurrentUser WHERE is_signed_in = 1")
    suspend fun getCurrentUserWeight(): Float

    @Query("SELECT * FROM CurrentUser")
    suspend fun getAllUsers(): List<CurrentUser>

    @Query("UPDATE CurrentUser SET is_signed_in = :isSignedIn WHERE user_id = :userID")
    suspend fun updateIsUserSignedIn(isSignedIn: Boolean, userID: Int)

    @Query("SELECT calories_goal FROM CurrentUser WHERE is_signed_in = 1")
    fun getCurrentUserCaloriesRequirements(): Flow<Int>
}
