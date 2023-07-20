package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class)
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
                is SignInProgress.ActivityLevelAndCaloriesGoal -> TODO()
                SignInProgress.Introduction -> TODO()
                is SignInProgress.Measurements -> TODO()
                SignInProgress.ProfileList -> TODO()
            }
        }
    }
}