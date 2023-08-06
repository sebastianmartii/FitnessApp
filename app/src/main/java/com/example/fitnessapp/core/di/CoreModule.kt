package com.example.fitnessapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideCurrentUserDao(
        db: FitnessDatabase
    ): CurrentUserDao {
        return db.currentUserDao
    }

    @Provides
    @Singleton
    fun provideFitnessDatabase(
        @ApplicationContext context: Context
    ): FitnessDatabase {
        return Room.databaseBuilder(
            context,
            FitnessDatabase::class.java,
            "db"
        ).build()
    }
}