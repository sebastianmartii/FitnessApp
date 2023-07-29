package com.example.fitnessapp.profile_feature.data.mappers

import com.example.fitnessapp.core.database.entity.CurrentUser
import com.example.fitnessapp.profile_feature.data.remote.dto.CaloriesRequirementsDto
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.model.UserProfile
import com.example.fitnessapp.profile_feature.domain.model.CalculatedCalories
import com.example.fitnessapp.profile_feature.domain.model.TypeOfGoal

fun CurrentUser.toUserProfile(): UserProfile {
    return UserProfile(
        name = this.name,
        gender = this.gender.toGender()
    )
}

fun String.toGender(): Gender {
    return when(this) {
        "male" -> Gender.MALE
        else -> Gender.FEMALE
    }
}

fun Gender.toGenderString(): String {
    return when(this) {
        Gender.MALE -> "male"
        Gender.FEMALE -> "female"
        Gender.NONE -> "none"
    }
}

fun CaloriesRequirementsDto.toWeightLossGoals(): List<CalculatedCalories> {
    return listOf(
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
        )
    )
}

fun CaloriesRequirementsDto.toWeightGainGoals(): List<CalculatedCalories> {
    return listOf(
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

fun CaloriesRequirementsDto.toMaintainWeightGoal(): CalculatedCalories {
    return CalculatedCalories(
        typeOfGoal = TypeOfGoal.MAINTAIN_WEIGHT,
        calories = this.data.goals.maintainWeight,
    )
}

fun Double.toCaloriesString(): String {
    return this.toInt().toString()
}