package com.example.fitnessapp.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.util.DailyOverviewDataManager
import com.example.fitnessapp.core.util.GsonParser
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyActivitiesDao
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyNutritionDao
import com.example.fitnessapp.history_feature.data.local.dao.ActivityHistoryDao
import com.example.fitnessapp.history_feature.data.local.dao.NutritionHistoryDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.MealPlanTypeConverters
import com.example.fitnessapp.nutrition_calculator_feature.data.local.RecipeConverters
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.nutrition.NutritionCalculatorInterceptor
import com.example.fitnessapp.profile_feature.data.remote.CaloriesGoalInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideDailyOverviewDataManager(
        dataStore: DataStore<Preferences>,
        dailyNutritionDao: DailyNutritionDao,
        dailyActivitiesDao: DailyActivitiesDao,
        nutritionHistoryDao: NutritionHistoryDao,
        activityHistoryDao: ActivityHistoryDao
    ): DailyOverviewDataManager {
        return DailyOverviewDataManager(dataStore, dailyNutritionDao, dailyActivitiesDao, nutritionHistoryDao, activityHistoryDao)
    }


    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Set your desired logging level
        }

        return OkHttpClient.Builder().apply {
            addInterceptor(NutritionCalculatorInterceptor())
            addInterceptor(CaloriesGoalInterceptor())
            addInterceptor(loggingInterceptor)
        }.build()
    }


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
        )
            .addTypeConverter(RecipeConverters(GsonParser(Gson())))
            .addTypeConverter(MealPlanTypeConverters())
            .build()
    }
}