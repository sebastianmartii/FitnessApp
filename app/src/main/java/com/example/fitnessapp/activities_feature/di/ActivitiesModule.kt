package com.example.fitnessapp.activities_feature.di

import com.example.fitnessapp.activities_feature.data.local.dao.SavedActivitiesDao
import com.example.fitnessapp.activities_feature.data.remote.ActivitiesApi
import com.example.fitnessapp.activities_feature.data.repository.ActivitiesRepositoryImpl
import com.example.fitnessapp.activities_feature.domain.repository.ActivitiesRepository
import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.util.Endpoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActivitiesModule {

    @Provides
    @Singleton
    fun provideSavedActivitiesDao(db: FitnessDatabase): SavedActivitiesDao {
        return db.savedActivitiesDao
    }


    @Provides
    @Singleton
    fun provideActivitiesRepository(
        activitiesApi: ActivitiesApi,
        savedActivitiesDao: SavedActivitiesDao,
        currentUserDao: CurrentUserDao
    ): ActivitiesRepository {
        return ActivitiesRepositoryImpl(activitiesApi, savedActivitiesDao, currentUserDao)
    }

    @Provides
    @Singleton
    fun provideActivitiesApi(client: OkHttpClient): ActivitiesApi {
        return Retrofit.Builder()
            .baseUrl(Endpoints.FITNESS_CALCULATOR_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActivitiesApi::class.java)
    }
}