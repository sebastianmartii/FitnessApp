package com.example.fitnessapp.nutrition_calculator_feature.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.fitnessapp.core.util.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class RecipeConverters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun toListString(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun fromListString(list: List<String>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: "[]"
    }
}