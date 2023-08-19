package com.example.fitnessapp.nutrition_calculator_feature.domain.model

data class Recipe(
    val label: String,
    val smallImage: String,
    val bigImage: String,
    val dietLabels: List<String>,
    val ingredients: List<String>,
    val calories: Double,
    val servingSize: Double,
    val carbs: Double,
    val protein: Double,
    val fat: Double,
    val saturatedFat: Double,
    val fiber: Double,
    val sugar: Double,
    val externalUrl: String
)
