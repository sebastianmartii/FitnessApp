package com.example.fitnessapp.profile_feature.presentation.sign_in

enum class ActivityLevel {
    LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5, LEVEL_6
}

fun ActivityLevel.toActivityLevelString(): String {
    return when(this) {
        ActivityLevel.LEVEL_1 -> "Inactive: little or no exercise"
        ActivityLevel.LEVEL_2 -> "Lightly Active: exercise 1-3 times/week"
        ActivityLevel.LEVEL_3 -> "Moderately Active: exercise 4-6 times/week"
        ActivityLevel.LEVEL_4 -> "Active: daily or intense exercise 3-4 times/week"
        ActivityLevel.LEVEL_5 -> "Very Active: intense exercise 6-7 times/week"
        ActivityLevel.LEVEL_6 -> "Extremely Active: very intense exercise daily or physical job"
    }
}

val activityLevels = listOf(
    ActivityLevel.LEVEL_1,
    ActivityLevel.LEVEL_2,
    ActivityLevel.LEVEL_3,
    ActivityLevel.LEVEL_4,
    ActivityLevel.LEVEL_5,
    ActivityLevel.LEVEL_6,
)