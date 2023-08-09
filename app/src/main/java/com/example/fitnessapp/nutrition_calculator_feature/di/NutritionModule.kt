package com.example.fitnessapp.nutrition_calculator_feature.di

import com.example.fitnessapp.core.util.Endpoints
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.NutritionCalculatorApi
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.NutritionCalculatorInterceptor
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
object NutritionModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(NutritionCalculatorInterceptor())
        }.build()
    }

    @Provides
    @Singleton
    fun provideNutritionCalculatorApi(client: OkHttpClient): NutritionCalculatorApi {
        return Retrofit.Builder()
            .baseUrl(Endpoints.NUTRITION_CALCULATOR_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NutritionCalculatorApi::class.java)
    }
}