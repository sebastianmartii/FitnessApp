package com.example.fitnessapp.nutrition_calculator_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Meal

@Entity
data class MealPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val planName: String,
    val meals: List<Meal>
)
