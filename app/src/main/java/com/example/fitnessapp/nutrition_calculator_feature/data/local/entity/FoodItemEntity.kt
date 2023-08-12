package com.example.fitnessapp.nutrition_calculator_feature.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val calories: Double,
    @ColumnInfo(name = "serving_size") val servingSize: Double,
    val carbs: Double,
    val protein: Double,
    val fat: Double,
    @ColumnInfo(name = "saturated_fat") val saturatedFat: Double,
    val fiber: Double,
    val sugar: Double,
)
