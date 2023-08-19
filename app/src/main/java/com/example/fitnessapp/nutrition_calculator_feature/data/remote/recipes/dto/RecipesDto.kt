package com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.dto

import com.google.gson.annotations.SerializedName

data class RecipesDto(
    val from: Int,
    val to: Int,
    val count: Int,
    @SerializedName("_links") val links: Links,
    val hits: List<Hit>
)