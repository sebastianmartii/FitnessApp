package com.example.fitnessapp.nutrition_calculator_feature.data.remote.recipes.dto

data class Ingredient(
    val text: String,
    val quantity: Double,
    val measure: String,
    val food: String,
    val weight: Double,
    val foodId: String
)