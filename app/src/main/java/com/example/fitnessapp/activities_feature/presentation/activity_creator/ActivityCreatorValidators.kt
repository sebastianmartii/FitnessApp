package com.example.fitnessapp.activities_feature.presentation.activity_creator

import androidx.core.text.isDigitsOnly

object ActivityCreatorValidators {

    fun isCreatedActivityValid(name: String, calories: String, minutes: String, seconds: String): Boolean {
        return isNameValid(name) && areCaloriesBurnedValid(calories) && isDurationValid(minutes, seconds)
    }

    fun isNameValid(name: String): Boolean {
        return name.length > 3 && name.isNotBlank()
    }

    fun areCaloriesBurnedValid(calories: String): Boolean {
        if (!calories.isDigitsOnly() || calories.isBlank()) {
            return false
        }
        return calories.toDouble() > 0
    }

    fun isDurationValid(minutes: String, seconds: String): Boolean {
        if (!minutes.isDigitsOnly() || !seconds.isDigitsOnly() || minutes.isBlank() || seconds.isBlank()) {
            return false
        }
        return minutes.toInt() in 1..60 && seconds.toInt() in 0..60 || minutes.toInt() in 0..60 && seconds.toInt() in 1..60
    }
}