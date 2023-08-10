package com.example.fitnessapp.nutrition_calculator_feature.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.fitnessapp.core.util.JsonParser
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Meal
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class MealConverters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun toMealList(json: String): List<Meal> {
        return jsonParser.fromJson<ArrayList<Meal>>(
            json,
            object : TypeToken<ArrayList<Meal>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun fromMealList(meals: List<Meal>): String {
        return jsonParser.toJson(
            meals,
            object : TypeToken<ArrayList<Meal>>(){}.type
        ) ?: "[]"
    }

}