package com.example.fitnessapp.profile_feature.presentation.sign_in

data class CalculatedCalories(
    val typeOfGoal: TypeOfGoal,
    val calories: Int,
    val weightLose: Float? = null,
    val weightGain: Float? = null
) {
    override fun toString(): String {
        return when(this.typeOfGoal) {
            TypeOfGoal.MAINTAIN_WEIGHT -> "Maintain Weight"
            TypeOfGoal.MILD_WEIGHT_LOSE -> "Mild Weight Lose"
            TypeOfGoal.WEIGHT_LOSE -> "Weight Lose"
            TypeOfGoal.EXTREME_WEIGHT_LOSE -> "Extreme Weight Lose"
            TypeOfGoal.MILD_WEIGHT_GAIN -> "Mild Weight Gain"
            TypeOfGoal.WEIGHT_GAIN -> "Weight Gain"
            TypeOfGoal.EXTREME_WEIGHT_GAIN -> "Extreme Weight Gain"
        }
    }
}

enum class TypeOfGoal {
    MAINTAIN_WEIGHT, MILD_WEIGHT_LOSE, WEIGHT_LOSE, EXTREME_WEIGHT_LOSE, MILD_WEIGHT_GAIN, WEIGHT_GAIN, EXTREME_WEIGHT_GAIN
}
