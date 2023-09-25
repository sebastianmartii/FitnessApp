package com.example.fitnessapp.history_feature.di

import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.history_feature.data.local.dao.ActivityHistoryDao
import com.example.fitnessapp.history_feature.data.local.dao.NutritionHistoryDao
import com.example.fitnessapp.history_feature.data.repository.HistoryRepositoryImpl
import com.example.fitnessapp.history_feature.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {

    @Provides
    @Singleton
    fun provideHistoryRepository(
        activityHistoryDao: ActivityHistoryDao,
        nutritionHistoryDao: NutritionHistoryDao
    ): HistoryRepository {
        return HistoryRepositoryImpl(activityHistoryDao, nutritionHistoryDao)
    }


    @Provides
    @Singleton
    fun provideActivityHistoryDao(db: FitnessDatabase): ActivityHistoryDao {
        return db.activityHistoryDao
    }


    @Provides
    @Singleton
    fun provideNutritionHistoryDao(db: FitnessDatabase): NutritionHistoryDao {
        return db.nutritionHistoryDao
    }

    @Provides
    @Singleton
    fun provideCalendar(): Calendar {
        return Calendar.getInstance()
    }
}