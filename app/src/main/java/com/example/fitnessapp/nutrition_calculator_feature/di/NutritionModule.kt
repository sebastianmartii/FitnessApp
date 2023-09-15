package com.example.fitnessapp.nutrition_calculator_feature.di

import com.example.fitnessapp.core.database.FitnessDatabase
import com.example.fitnessapp.core.util.Endpoints
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyNutritionDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.FoodItemDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.MealDao
import com.example.fitnessapp.nutrition_calculator_feature.data.local.dao.RecipesDao
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.nutrition.NutritionCalculatorApi
import com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.RecipesApi
import com.example.fitnessapp.nutrition_calculator_feature.data.repository.CustomMealPlanCreatorRepositoryImpl
import com.example.fitnessapp.nutrition_calculator_feature.data.repository.NutritionCalculatorRepositoryImpl
import com.example.fitnessapp.nutrition_calculator_feature.data.repository.RecipesRepositoryImpl
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.CustomMealPlanCreatorRepository
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.RecipesRepository
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
    fun provideRecipesDao(db: FitnessDatabase): RecipesDao {
        return db.recipesDao
    }


    @Provides
    @Singleton
    fun provideRecipesRepository(
        recipesApi: RecipesApi,
        recipesDao: RecipesDao
    ): RecipesRepository {
        return RecipesRepositoryImpl(recipesApi, recipesDao)
    }


    @Provides
    @Singleton
    fun provideNutritionCalculatorRepository(
        foodItemDao: FoodItemDao,
        nutritionCalculatorApi: NutritionCalculatorApi,
        dailyNutritionDao: DailyNutritionDao,
        mealDao: MealDao
    ): NutritionCalculatorRepository {
        return NutritionCalculatorRepositoryImpl(foodItemDao, nutritionCalculatorApi, dailyNutritionDao, mealDao)
    }


    @Provides
    @Singleton
    fun provideFoodItemDao(db: FitnessDatabase): FoodItemDao {
        return db.foodItemDao
    }


    @Provides
    @Singleton
    fun provideCustomMealPlanCreatorRepository(
        mealDao: MealDao,
        dailyNutritionDao: DailyNutritionDao
    ): CustomMealPlanCreatorRepository {
        return CustomMealPlanCreatorRepositoryImpl(mealDao, dailyNutritionDao)
    }


    @Provides
    @Singleton
    fun provideMealDao(db: FitnessDatabase): MealDao {
        return db.mealDao
    }


    @Provides
    @Singleton
    fun provideRecipesApi(client: OkHttpClient): RecipesApi {
        return Retrofit.Builder()
            .baseUrl(Endpoints.RECIPES_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipesApi::class.java)
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