package com.example.fitnessapp.nutrition_calculator_feature.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanType

@ProvidedTypeConverter
class MealPlanTypeConverters {

    @TypeConverter
    fun fromStatus(mealPlanType: MealPlanType): String {
        return mealPlanType.name
    }

    @TypeConverter
    fun toStatus(value: String): MealPlanType {
        return enumValueOf(value)
    }
}