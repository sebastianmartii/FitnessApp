package com.example.fitnessapp.profile_feature.presentation.sign_in

import com.example.fitnessapp.profile_feature.domain.model.Gender

object Validators {

    fun isUserNameValid(userName: String): Boolean {
        return userName.length > 3
    }

    fun isGenderValid(gender: Gender): Boolean {
        return gender == Gender.MALE || gender == Gender.FEMALE
    }

    fun isHeightValid(height: String): Boolean {
        if (height.isBlank() || height.isEmpty() || height.isNotANumber()) {
            return false
        }
        return !height.toFloat().isNaN() && height.toFloat() > 129 && height.toFloat() < 231
    }

    fun isWeightValid(weight: String): Boolean {
        if (weight.isBlank() || weight.isEmpty() || weight.isNotANumber()) {
            return false
        }
        return weight.toFloat() > 39 && weight.toFloat() < 161
    }

    fun isAgeValid(age: String): Boolean {
        if (age.isBlank() || age.isEmpty() || age.isNotANumber()) {
            return false
        }
        return !age.toFloat().isNaN() && age.toFloat() > 0 && age.toInt() < 81
    }

    fun areCaloriesValid(calories: String): Boolean {
        if (calories.isBlank() || calories.isNotANumber()) {
            return false
        }
        return true
    }

    private fun String.isNotANumber(): Boolean {
        return this.any {
            !it.isDigit() && it.toString() != "."
        }
    }
}