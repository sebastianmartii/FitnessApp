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
        return !height.toFloat().isNaN()
    }

    fun isWeightValid(weight: String): Boolean {
        return !weight.toFloat().isNaN()
    }

    fun isAgeValid(age: String): Boolean {
        return !age.toFloat().isNaN()
    }
}