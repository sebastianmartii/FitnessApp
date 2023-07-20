package com.example.fitnessapp.profile_feature.presentation.sign_in

data class SignInState(
    val signInProgress: SignInProgress = SignInProgress.Introduction,
    val name: String = ""
)