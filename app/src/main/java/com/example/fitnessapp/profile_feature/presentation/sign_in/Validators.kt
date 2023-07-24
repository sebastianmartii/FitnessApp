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
        if (height.isBlank()) {
            return false
        }
        return !height.toFloat().isNaN() && height.toFloat() > 0
    }

    fun isWeightValid(weight: String): Boolean {
        if (weight.isBlank()) {
            return false
        }
        return !weight.toFloat().isNaN() && weight.toFloat() > 0
    }

    fun isAgeValid(age: String): Boolean {
        if (age.isBlank()) {
            return false
        }
        return !age.toFloat().isNaN() && age.toFloat() > 0
    }
}