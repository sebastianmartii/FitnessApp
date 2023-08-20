package com.example.fitnessapp.nutrition_calculator_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipesEntity(
    @PrimaryKey(autoGenerate = false)
    val label: String,
    val smallImage: String,
    val bigImage: String,
    val dietLabels: List<String>,
    val ingredients: List<String>,
    val calories: Double,
    val servingSize: Double,
    val carbs: Double,
    val fat: Double,
    val protein: Double,
    val saturatedFat: Double,
    val fiber: Double,
    val sugar: Double,
    val externalUrl: String,
)
