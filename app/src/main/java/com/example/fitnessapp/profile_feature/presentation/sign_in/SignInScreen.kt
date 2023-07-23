package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SignInScreen(
    state: SignInState,
    onEvent: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { values ->
        AnimatedContent(
            targetState = state.signInProgress,
            modifier = Modifier
                .padding(values)
        ) { progress ->
            when(progress) {
                SignInProgress.Introduction -> {
                    IntroductionScreen(
                        onIntroductionDone = { name, gender ->
                            onEvent(ProfileEvent.IntroductionDone(name, gender))
                        }
                    )
                }
                SignInProgress.Measurements -> {
                    MeasurementsScreen(
                        onGoBack = {
                            onEvent(ProfileEvent.OnGoBack(SignInProgress.Measurements))
                        },
                        onMeasurementsDone = { height, weight, age ->
                            onEvent(ProfileEvent.MeasurementsProvided(age, height, weight))
                        }
                    )
                }
                SignInProgress.ActivityLevelAndCaloriesGoal -> {
                    ActivityLevelAndCaloriesGoalScreen(
                        onActivityLevelAndCaloriesGoalProvided = { activityLevel, caloriesGoal ->
                            onEvent(ProfileEvent.SignInCompleted(activityLevel, caloriesGoal))
                        },
                        onCalculate = {
                            onEvent(ProfileEvent.OnCalculateCalories)
                        },
                        onGoBack = {
                            onEvent(ProfileEvent.OnGoBack(SignInProgress.ActivityLevelAndCaloriesGoal))
                        }
                    )
                }
                SignInProgress.CaloriesGoalList -> {
                    CaloriesGoalListScreen(
                        calculatedCalories = emptyList(),
                        onCaloriesGoalChosen = { caloriesGoal ->
                            onEvent(ProfileEvent.CaloriesGoalChosen(caloriesGoal))
                        },
                        onGoBack = {
                            onEvent(ProfileEvent.OnGoBack(SignInProgress.CaloriesGoalList))
                        }
                    )
                }
                SignInProgress.ProfileList -> {
                    ProfileListScreen(
                        profileList = emptyList(),
                        onProfileChosen = { profileName ->
                            onEvent(ProfileEvent.ProfileChosen(profileName))
                        },
                        onGoBack = {
                            onEvent(ProfileEvent.OnGoBack(SignInProgress.ProfileList))
                        }
                    )
                }
            }
        }
    }
}