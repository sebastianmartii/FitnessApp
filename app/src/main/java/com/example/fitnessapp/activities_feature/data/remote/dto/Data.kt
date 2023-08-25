package com.example.fitnessapp.activities_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("_id") val uid: String,
    @SerializedName("activity") val activity: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: String,
    @SerializedName("intensityLevel") val intensityLevel: Int,
    @SerializedName("metValue") val metValue: String
)