package com.example.fitnessapp.activities_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BurnedCaloriesDto(
    val `data`: DataX,
    @SerializedName("request_result") val requestResult: String,
    @SerializedName("status_code") val statusCode: Int
)