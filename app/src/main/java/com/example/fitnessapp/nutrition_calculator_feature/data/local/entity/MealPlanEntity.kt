package com.example.fitnessapp.nutrition_calculator_feature.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.MealPlanType

@Entity
data class MealPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "meal_plan_type") val mealPlanType: MealPlanType,
    @ColumnInfo(name = "plan_name") val planName: String,
    val meals: List<String>
)
