package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SignInScreen(
    signInProgress: SignInProgress,
    modifier: Modifier = Modifier
) {
    Scaffold { values ->
        AnimatedContent(
            targetState = signInProgress,
            modifier = modifier
                .padding(values)
        ) { progress ->
            when(progress) {
                SignInProgress.Introduction -> {
                    IntroductionScreen(onIntroductionDone = {})
                }
                is SignInProgress.Measurements -> {
                    MeasurementsScreen(
                        goBack = {},
                        onMeasurementsDone = { height, weight, age ->

                        }
                    )
                }
                is SignInProgress.ActivityLevelAndCaloriesGoal -> {
                    ActivityLevelAndCaloriesGoalScreen(
                        onActivityLevelAndCaloriesGoalProvided = { activityLevel, caloriesGoal ->

                        },
                        onCalculate = {},
                        goBack = {})
                }
                is SignInProgress.CaloriesGoalList -> TODO()
                SignInProgress.ProfileList -> TODO()
            }
        }
    }
}