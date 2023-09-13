package com.example.fitnessapp.daily_overview_feature.di

import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyActivitiesDao
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyNutritionDao
import com.example.fitnessapp.daily_overview_feature.data.repository.OverviewRepositoryImpl
import com.example.fitnessapp.daily_overview_feature.domain.repository.OverviewRepository
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.MealDao
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
    fun provideDailyActivitiesDao(db: FitnessDatabase): DailyActivitiesDao {
        return db.dailyActivitiesDao
    }

    @Provides
    @Singleton
    fun provideDailyNutritionDao(db: FitnessDatabase): DailyNutritionDao {
        return db.dailyNutritionDao
    }


    @Provides
    @Singleton
    fun provideOverviewRepository(
        currentUserDao: CurrentUserDao,
        dailyNutritionDao: DailyNutritionDao,
        dailyActivitiesDao: DailyActivitiesDao,
        mealDao: MealDao
    ): OverviewRepository {
        return OverviewRepositoryImpl(currentUserDao, dailyNutritionDao, dailyActivitiesDao, mealDao)
    }
}