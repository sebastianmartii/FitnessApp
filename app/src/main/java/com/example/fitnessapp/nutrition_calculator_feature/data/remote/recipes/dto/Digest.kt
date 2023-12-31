package com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.dto

data class Digest(
    val label: String,
    val tag: String,
    val schemaOrgTag: String,
    val total: Double,
    val hasRDI: Boolean,
    val daily: Double,
    val unit: String,
    val sub: List<Sub>
)