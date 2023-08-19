package com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.dto

data class Digest(
    val label: String,
    val tag: String,
    val schemaOrgTag: String,
    val total: Int,
    val hasRDI: Boolean,
    val daily: Int,
    val unit: String,
    val sub: Map<String, Any>
)