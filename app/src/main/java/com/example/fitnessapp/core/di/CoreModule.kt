package com.example.fitnessapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.util.GsonParser
import com.example.fitnessapp.nutrition_calculator_feature.data.local.MealConverters
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.nutrition.NutritionCalculatorInterceptor
import com.example.fitnessapp.profile_feature.data.remote.CaloriesGoalInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

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
        ).addTypeConverter(MealConverters(GsonParser(Gson())))
            .build()
    }
}