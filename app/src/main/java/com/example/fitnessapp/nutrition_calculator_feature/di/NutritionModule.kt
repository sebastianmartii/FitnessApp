package com.example.fitnessapp.nutrition_calculator_feature.di

import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.util.Endpoints
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.FoodItemDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.MealDao
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.NutritionCalculatorApi
import com.example.fitnessapp.nutrition_calculator_feature.data.repository.CustomMealPlanCreatorRepositoryImpl
import com.example.fitnessapp.nutrition_calculator_feature.data.repository.NutritionCalculatorRepositoryImpl
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.CustomMealPlanCreatorRepository
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
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
    fun provideNutritionCalculatorRepository(
        foodItemDao: FoodItemDao,
        nutritionCalculatorApi: NutritionCalculatorApi
    ): NutritionCalculatorRepository {
        return NutritionCalculatorRepositoryImpl(foodItemDao, nutritionCalculatorApi)
    }


    @Provides
    @Singleton
    fun provideFoodItemDao(db: FitnessDatabase): FoodItemDao {
        return db.foodItemDao
    }


    @Provides
    @Singleton
    fun provideCustomMealPlanCreatorRepository(
        mealDao: MealDao
    ): CustomMealPlanCreatorRepository {
        return CustomMealPlanCreatorRepositoryImpl(mealDao)
    }


    @Provides
    @Singleton
    fun provideMealDao(db: FitnessDatabase): MealDao {
        return db.mealDao
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