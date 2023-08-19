package com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.dto

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("THUMBNAIL") val thumbnail: ImageInfo,
    @SerializedName("SMALL") val small: ImageInfo,
    @SerializedName("REGULAR") val regular: ImageInfo,
    @SerializedName("LARGE") val large: ImageInfo
)