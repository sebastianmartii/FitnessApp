package com.example.fitnessapp.profile_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CaloriesGoal(
    @SerializedName("gain weight") val gainWeight: String?,
    @SerializedName("loss weight") val lossWeight: String?,
    @SerializedName("calory") val calories: Double
)
