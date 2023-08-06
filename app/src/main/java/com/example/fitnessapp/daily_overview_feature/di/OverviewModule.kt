package com.example.fitnessapp.daily_overview_feature.di

import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.daily_overview_feature.data.repository.OverviewRepositoryImpl
import com.example.fitnessapp.daily_overview_feature.domain.repository.OverviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OverviewModule {

    @Provides
    @Singleton
    fun provideOverviewRepository(
        db: FitnessDatabase
    ): OverviewRepository {
        return OverviewRepositoryImpl(db.currentUserDao)
    }
}