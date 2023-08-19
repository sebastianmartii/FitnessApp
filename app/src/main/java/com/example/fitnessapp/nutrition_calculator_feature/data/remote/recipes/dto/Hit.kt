package com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.dto

import com.google.gson.annotations.SerializedName

data class Hit(
    val recipe: Recipe,
    @SerializedName("_links") val links: Links
)