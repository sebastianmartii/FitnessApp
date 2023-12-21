package com.example.fitnessapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.util.GsonParser
import com.example.fitnessapp.nutrition_calculator_feature.data.local.MealPlanTypeConverters
import com.example.fitnessapp.nutrition_calculator_feature.data.local.RecipeConverters
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestCoreModule {

    @Provides
    @Singleton
    @Named("test_db")
    fun provideInMemoryDb(
        @ApplicationContext context: Context
    ) : FitnessDatabase {
     return Room.inMemoryDatabaseBuilder(
            context,
            FitnessDatabase::class.java
        )
         .addTypeConverter(RecipeConverters(GsonParser(Gson())))
         .addTypeConverter(MealPlanTypeConverters())
         .allowMainThreadQueries()
         .build()
    }
}