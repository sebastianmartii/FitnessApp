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

    @Query("UPDATE CurrentUser SET name = :newName WHERE is_signed_in = 1")
    suspend fun updateName(newName: String)

    @Query("UPDATE CurrentUser SET age = :newAge WHERE  is_signed_in = 1")
    suspend fun updateAge(newAge: Int)

    @Query("UPDATE CurrentUser SET gender = :newGender WHERE is_signed_in = 1")
    suspend fun updateGender(newGender: String)

    @Query("UPDATE CurrentUser SET height = :newHeight WHERE is_signed_in = 1")
    suspend fun updateHeight(newHeight: Float)

    @Query("UPDATE CurrentUser SET weight = :newWeight WHERE is_signed_in = 1")
    suspend fun updateWeight(newWeight: Float)

    @Query("UPDATE CurrentUser SET calories_goal = :newCaloriesGoal WHERE is_signed_in = 1")
    suspend fun updateCaloriesGoal(newCaloriesGoal: Int)

    @Query("UPDATE CurrentUser SET activity_level = :newActivityLevel WHERE is_signed_in = 1")
    suspend fun updateActivityLevel(newActivityLevel: String)

    @Query("SELECT * FROM CurrentUser WHERE is_signed_in = 1")
    fun getCurrentUser(): Flow<CurrentUser?>

    @Query("SELECT weight FROM CurrentUser WHERE is_signed_in = 1")
    suspend fun getCurrentUserWeight(): Float

    @Query("SELECT * FROM CurrentUser")
    suspend fun getAllUsers(): List<CurrentUser>

    @Query("UPDATE CurrentUser SET is_signed_in = 0 WHERE is_signed_in = 1")
    suspend fun signOut()

    @Query("UPDATE CurrentUser SET is_signed_in = 1 WHERE user_id = :userID")
    suspend fun signIn(userID: Int)

    @Query("SELECT calories_goal FROM CurrentUser WHERE is_signed_in = 1")
    fun getCurrentUserCaloriesRequirements(): Flow<Int?>
}
