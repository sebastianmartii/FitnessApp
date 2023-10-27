package com.example.fitnessapp.profile_feature.data.mappers

import com.example.fitnessapp.core.database.entity.CurrentUser
import com.example.fitnessapp.profile_feature.data.remote.dto.CaloriesRequirementsDto
import com.example.fitnessapp.profile_feature.domain.model.CalculatedCalories
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.TypeOfGoal
import com.example.fitnessapp.profile_feature.domain.model.UserProfile
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel

fun CurrentUser.toUserProfile(): UserProfile {
    return UserProfile(
        userID = this.userID ?: 0,
        name = this.name,
        gender = this.gender.toGender()
    )
}

fun String.toGender(): Gender {
    return when(this) {
        "male" -> Gender.MALE
        "female" -> Gender.FEMALE
        else -> Gender.NONE
    }
}

fun Gender.toGenderString(): String {
    return when(this) {
        Gender.MALE -> "male"
        Gender.FEMALE -> "female"
        Gender.NONE -> "none"
    }
}

fun CaloriesRequirementsDto.toCalculatedCaloriesList(): List<CalculatedCalories> {
    return listOf(
        CalculatedCalories(
            typeOfGoal = TypeOfGoal.MAINTAIN_WEIGHT,
            calories = this.data.goals.maintainWeight,
        ),
        CalculatedCalories(
            typeOfGoal = TypeOfGoal.EXTREME_WEIGHT_LOSE,
            calories = this.data.goals.extremeWeightLoss.calories,
            weightLose = this.data.goals.extremeWeightLoss.lossWeight
        ),
        CalculatedCalories(
            typeOfGoal = TypeOfGoal.WEIGHT_LOSE,
            calories = this.data.goals.weightLoss.calories,
            weightLose = this.data.goals.weightLoss.lossWeight
        ),
        CalculatedCalories(
            typeOfGoal = TypeOfGoal.MILD_WEIGHT_LOSE,
            calories = this.data.goals.mildWeightLoss.calories,
            weightLose = this.data.goals.mildWeightLoss.lossWeight
        ),
        CalculatedCalories(
            typeOfGoal = TypeOfGoal.EXTREME_WEIGHT_GAIN,
            calories = this.data.goals.extremeWeightGain.calories,
            weightGain = this.data.goals.extremeWeightGain.gainWeight
        ),
        CalculatedCalories(
            typeOfGoal = TypeOfGoal.WEIGHT_GAIN,
            calories = this.data.goals.weightGain.calories,
            weightGain = this.data.goals.weightGain.gainWeight
        ),
        CalculatedCalories(
            typeOfGoal = TypeOfGoal.MILD_WEIGHT_GAIN,
            calories = this.data.goals.mildWeightGain.calories,
            weightGain = this.data.goals.mildWeightGain.gainWeight
        )
    )
}

fun Double.toCaloriesString(): String {
    return this.toInt().toString()
}

fun ActivityLevel.toActivityLevelString(): String {
    return when(this) {
        ActivityLevel.LEVEL_1 -> "Inactive"
        ActivityLevel.LEVEL_2 -> "Lightly Active"
        ActivityLevel.LEVEL_3 -> "Moderately Active"
        ActivityLevel.LEVEL_4 -> "Active"
        ActivityLevel.LEVEL_5 -> "Very Active"
        ActivityLevel.LEVEL_6 -> "Extremely Active"
        ActivityLevel.LEVEL_0 -> ""
    }
}

fun String.toDropDownMenuString(): String {
    return when(this) {
        "LEVEL_1" -> "Inactive"
        "LEVEL_2" -> "Lightly Active"
        "LEVEL_3" -> "Moderately Active"
        "LEVEL_4" -> "Active"
        "LEVEL_5" -> "Very Active"
        "LEVEL_6" -> "Extremely Active"
        "MALE" -> "male"
        "FEMALE" -> "female"
        else -> ""
    }
}

fun ActivityLevel.toActivityLevelToolTipHint(): String {
    return when(this) {
        ActivityLevel.LEVEL_1 -> "Little or no exercise"
        ActivityLevel.LEVEL_2 -> "Exercise 1-3 times/week"
        ActivityLevel.LEVEL_3 -> "Exercise 4-6 times/week"
        ActivityLevel.LEVEL_4 -> "Daily or intense exercise 3-4 times/week"
        ActivityLevel.LEVEL_5 -> "Intense exercise 6-7 times/week"
        ActivityLevel.LEVEL_6 -> "Very intense exercise daily or physical job"
        ActivityLevel.LEVEL_0 -> ""
    }
}