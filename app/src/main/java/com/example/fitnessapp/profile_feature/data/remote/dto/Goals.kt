package com.example.fitnessapp.profile_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Goals(
    @SerializedName("maintain weight") val maintainWeight: Double,
    @SerializedName("Mild weight loss") val mildWeightLoss: CaloriesGoal,
    @SerializedName("Weight loss") val weightLoss: CaloriesGoal,
    @SerializedName("Extreme weight loss") val extremeWeightLoss: CaloriesGoal,
    @SerializedName("Mild weight gain") val mildWeightGain: CaloriesGoal,
    @SerializedName("Weight gain") val weightGain: CaloriesGoal,
    @SerializedName("Extreme weight gain") val extremeWeightGain: CaloriesGoal
)