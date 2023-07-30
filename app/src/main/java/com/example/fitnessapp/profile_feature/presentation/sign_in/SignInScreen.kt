package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SignInScreen(
    state: SignInState,
    onEvent: (ProfileEvent) -> Unit,
    calculate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { values ->
        AnimatedContent(
            targetState = state.signInProgress,
            label = "",
            transitionSpec = {
                when {
                    targetState == SignInProgress.Introduction && initialState == SignInProgress.Measurements -> {
                        (slideInHorizontally(
                            keyframes {
                                durationMillis = 200
                            }
                        ) { fullWidth -> -fullWidth } + fadeIn()).togetherWith(
                            slideOutHorizontally(
                                keyframes {
                                    durationMillis = 200
                                }
                            ) { fullWidth -> fullWidth } + fadeOut())
                    }
                    targetState == SignInProgress.Introduction && initialState == SignInProgress.ProfileList -> {
                        (slideInHorizontally(
                            keyframes {
                                durationMillis = 200
                            }
                        ) { fullWidth -> -fullWidth } + fadeIn()).togetherWith(
                            slideOutHorizontally(
                                keyframes {
                                    durationMillis = 200
                                }
                            ) { fullWidth -> fullWidth } + fadeOut())
                    }
                    targetState == SignInProgress.Measurements && initialState == SignInProgress.Introduction -> {
                        (slideInHorizontally(
                            keyframes {
                                durationMillis = 200
                            }
                        ) { fullWidth -> fullWidth } + fadeIn()).togetherWith(
                            slideOutHorizontally(
                                keyframes {
                                    durationMillis = 200
                                }
                            ) { fullWidth -> -fullWidth } + fadeOut())
                    }
                    targetState == SignInProgress.Measurements && initialState == SignInProgress.ActivityLevelAndCaloriesGoal -> {
                        (slideInHorizontally(
                            keyframes {
                                durationMillis = 200
                            }
                        ) { fullWidth -> -fullWidth } + fadeIn()).togetherWith(
                            slideOutHorizontally(
                                keyframes {
                                    durationMillis = 200
                                }
                            ) { fullWidth -> fullWidth } + fadeOut())
                    }
                    targetState == SignInProgress.ActivityLevelAndCaloriesGoal && initialState == SignInProgress.Measurements -> {
                        (slideInHorizontally(
                            keyframes {
                                durationMillis = 200
                            }
                        ) { fullWidth -> fullWidth } + fadeIn()).togetherWith(
                            slideOutHorizontally(
                                keyframes {
                                    durationMillis = 200
                                }
                            ) { fullWidth -> -fullWidth } + fadeOut())
                    }
                    targetState == SignInProgress.ActivityLevelAndCaloriesGoal && initialState == SignInProgress.CaloriesGoalList -> {
                        (slideInHorizontally(
                            keyframes {
                                durationMillis = 200
                            }
                        ) { fullWidth -> -fullWidth } + fadeIn()).togetherWith(
                            slideOutHorizontally(
                                keyframes {
                                    durationMillis = 200
                                }
                            ) { fullWidth -> fullWidth } + fadeOut())
                    }
                    targetState == SignInProgress.CaloriesGoalList -> {
                        (slideInHorizontally(
                            keyframes {
                                durationMillis = 200
                            }
                        ) { fullWidth -> fullWidth } + fadeIn()).togetherWith(
                            slideOutHorizontally(
                                keyframes {
                                    durationMillis = 200
                                }
                            ) { fullWidth -> -fullWidth } + fadeOut())
                    }
                    targetState == SignInProgress.ProfileList -> {
                        (slideInHorizontally(
                            keyframes {
                                durationMillis = 200
                            }
                        ) { fullWidth -> fullWidth } + fadeIn()).togetherWith(
                            slideOutHorizontally(
                                keyframes {
                                    durationMillis = 200
                                }
                            ) { fullWidth -> -fullWidth } + fadeOut())
                    }
                    else -> {
                        (slideInHorizontally(
                            keyframes {
                                durationMillis = 150
                            }
                        ) { fullWidth -> fullWidth } + fadeIn()).togetherWith(
                            slideOutHorizontally(
                                keyframes {
                                    durationMillis = 150
                                }
                            ) { fullWidth -> -fullWidth } + fadeOut())
                    }
                }.using(SizeTransform(clip = false))
            },
            modifier = Modifier
                .padding(values)
        ) { progress ->
            when(progress) {
                SignInProgress.Introduction -> {
                    IntroductionScreen(
                        name = state.name,
                        gender = state.gender,
                        menuExpanded = state.genderMenuExpanded,
                        onNameChange = { name ->
                            onEvent(ProfileEvent.OnNameChange(name))
                        },
                        onGenderChange = { gender ->
                            onEvent(ProfileEvent.OnGenderChange(gender))
                        },
                        onMenuExpandedChange = { expanded ->
                            onEvent(ProfileEvent.OnGenderMenuExpandedChange(expanded))
                        },
                        onNavigateToProfileListScreen = {
                            onEvent(ProfileEvent.OnProfileSelect)
                        },
                        onNavigateToMeasurementsScreen = {
                            onEvent(ProfileEvent.OnIntroductionDone)
                        }
                    )
                }
                SignInProgress.Measurements -> {
                    MeasurementsScreen(
                        age = state.age,
                        height = state.height,
                        weight = state.weight,
                        onAgeChange = { age ->
                            onEvent(ProfileEvent.OnAgeChange(age))
                        },
                        onHeightChange = { height ->
                            onEvent(ProfileEvent.OnHeightChange(height))
                        },
                        onWeightChange = { weight ->
                            onEvent(ProfileEvent.OnWeightChange(weight))
                        },
                        onNavigateToActivityLevelAndCaloriesGoalScreen = {
                            onEvent(ProfileEvent.OnMeasurementsTaken)
                        },
                        onGoBack = {
                            onEvent(ProfileEvent.OnGoBack(SignInProgress.Measurements))
                        }
                    )
                }
                SignInProgress.ActivityLevelAndCaloriesGoal -> {
                    ActivityLevelAndCaloriesGoalScreen(
                        activityLevel = state.activityLevel,
                        caloriesGoal = state.caloriesGoal,
                        menuExpanded = state.activityLevelMenuExpanded,
                        onActivityLevelChange = { activityLevel ->
                            onEvent(ProfileEvent.OnActivityLevelChange(activityLevel))
                        },
                        onCaloriesGoalChange = { calories ->
                            onEvent(ProfileEvent.OnCaloriesGoalChange(calories))
                        },
                        onMenuExpandedChange = { expanded ->
                            onEvent(ProfileEvent.OnActivityLevelMenuExpandedChange(expanded))
                        },
                        onCalculate = {
                            onEvent(ProfileEvent.OnCalculateCalories)
                        },
                        onNavigateToOverviewScreen = {

                        },
                        onGoBack = {
                            onEvent(ProfileEvent.OnGoBack(SignInProgress.ActivityLevelAndCaloriesGoal))
                        })
                }
                SignInProgress.CaloriesGoalList -> {
                    CaloriesGoalListScreen(
                        calories = state.caloriesGoal,
                        calculatedCaloriesList = state.calculatedCalories,
                        onCaloriesGoalChosen = { caloriesGoal ->
                            onEvent(ProfileEvent.OnCaloriesGoalChange(caloriesGoal))
                        },
                        calculate = calculate,
                        onGoBack = {
                            onEvent(ProfileEvent.OnGoBack(SignInProgress.CaloriesGoalList))
                        }
                    )
                }
                SignInProgress.ProfileList -> {
                    ProfileListScreen(
                        profileList = state.profileList,
                        onProfileChosen = { profileName ->
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