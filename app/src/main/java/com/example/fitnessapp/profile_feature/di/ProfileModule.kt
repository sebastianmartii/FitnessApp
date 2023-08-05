package com.example.fitnessapp.profile_feature.di

import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.util.Endpoints
import com.example.fitnessapp.profile_feature.data.remote.CaloriesGoalApi
import com.example.fitnessapp.profile_feature.data.remote.CaloriesGoalInterceptor
import com.example.fitnessapp.profile_feature.data.repository.ProfileRepositoryImpl
import com.example.fitnessapp.profile_feature.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(CaloriesGoalInterceptor())
        }.build()
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        db: FitnessDatabase,
        caloriesGoalApi: CaloriesGoalApi
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            currentUserDao = db.currentUserDao,
            caloriesGoalApi = caloriesGoalApi
        )
    }

    @Provides
    @Singleton
    fun provideCaloriesGoalApi(okHttpClient: OkHttpClient): CaloriesGoalApi {
        return Retrofit.Builder()
            .baseUrl(Endpoints.FITNESS_CALCULATOR_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}