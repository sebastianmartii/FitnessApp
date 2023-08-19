package com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.dto

import com.google.gson.annotations.SerializedName

data class TotalDaily(
    @SerializedName("ENERC_KCAL") val enercKcal: Nutrient,
    @SerializedName("FAT") val fat: Nutrient,
    @SerializedName("FASAT") val fasat: Nutrient,
    @SerializedName("CHOCDF") val chocdf: Nutrient,
    @SerializedName("FIBTG") val fibtg: Nutrient,
    @SerializedName("PROCNT") val procnt: Nutrient,
    @SerializedName("CHOLE") val chole: Nutrient,
    @SerializedName("NA") val na: Nutrient,
    @SerializedName("CA") val ca: Nutrient,
    @SerializedName("MG") val mg: Nutrient,
    @SerializedName("K") val k: Nutrient,
    @SerializedName("FE") val fe: Nutrient,
    @SerializedName("ZN") val zn: Nutrient,
    @SerializedName("P") val p: Nutrient,
    @SerializedName("VITA_RAE") val vitaRae: Nutrient,
    @SerializedName("VITC") val vitc: Nutrient,
    @SerializedName("THIA") val thia: Nutrient,
    @SerializedName("RIBF") val ribf: Nutrient,
    @SerializedName("NIA") val nia: Nutrient,
    @SerializedName("VITB6A") val vitb6a: Nutrient,
    @SerializedName("FOLDFE") val foldfe: Nutrient,
    @SerializedName("VITB12") val vitb12: Nutrient,
    @SerializedName("VITD") val vitd: Nutrient,
    @SerializedName("TOCPHA") val tocpha: Nutrient,
    @SerializedName("VITK1") val vitk1: Nutrient
)
