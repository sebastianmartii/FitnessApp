package com.example.fitnessapp.profile_feature.presentation.sign_in

enum class ActivityLevel {
    LEVEL_0, LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5, LEVEL_6
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

val activityLevels = listOf(
    ActivityLevel.LEVEL_1,
    ActivityLevel.LEVEL_2,
    ActivityLevel.LEVEL_3,
    ActivityLevel.LEVEL_4,
    ActivityLevel.LEVEL_5,
    ActivityLevel.LEVEL_6,
)