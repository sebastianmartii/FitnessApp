package com.example.fitnessapp.nutrition_calculator_feature.data.remote.nutrition.dto

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("calories") val calories: Double,
    @SerializedName("carbohydrates_total_g") val carbs: Double,
    @SerializedName("cholesterol_mg") val cholesterol: Int,
    @SerializedName("fat_saturated_g") val saturatedFat: Double,
    @SerializedName("fat_total_g") val totalFat: Double,
    @SerializedName("fiber_g") val fiber: Double,
    @SerializedName("name") val name: String,
    @SerializedName("potassium_mg") val potassium: Int,
    @SerializedName("protein_g") val protein: Double,
    @SerializedName("serving_size_g") val servingSize: Double,
    @SerializedName("sodium_mg") val sodium: Int,
    @SerializedName("sugar_g") val sugar: Double
)