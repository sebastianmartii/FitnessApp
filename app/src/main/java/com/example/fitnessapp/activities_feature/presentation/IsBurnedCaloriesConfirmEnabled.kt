package com.example.fitnessapp.activities_feature.presentation

import androidx.core.text.isDigitsOnly

fun isBurnedCaloriesConfirmEnabled(minutes: String, seconds: String): Boolean {
    if (!minutes.isDigitsOnly() || minutes.isBlank() || !seconds.isDigitsOnly() || seconds.isBlank()) {
        return false
    }
    seconds.toInt()
    return minutes.toInt() in 1..60 && seconds.toInt() in 0..60
}