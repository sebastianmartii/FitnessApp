package com.example.fitnessapp.profile_feature.data.remote.dto

data class Goals(
    val extremeWeightGain: ExtremeWeightGain,
    val extremeWeightLoss: ExtremeWeightLoss,
    val mildWeightGain: MildWeightGain,
    val mildWeightLoss: MildWeightLoss,
    val weightGain: WeightGain,
    val weightLoss: WeightLoss,
    val maintainWeight: Int
)