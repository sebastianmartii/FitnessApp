package com.example.fitnessapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.database.entity.CurrentUser

@Database(
    entities = [CurrentUser::class],
    version = 1,
    exportSchema = false
)
abstract class FitnessDatabase : RoomDatabase() {

    abstract val currentUserDao: CurrentUserDao
}