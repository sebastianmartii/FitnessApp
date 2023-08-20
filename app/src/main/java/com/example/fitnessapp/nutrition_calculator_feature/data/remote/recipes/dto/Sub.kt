package com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.dto

data class Sub(
    val label: String,
    val tag: String,
    val schemaOrgTag: String,
    val total: Double,
    val hadRDI: Boolean,
    val daily: Double,
    val unit: String
)
