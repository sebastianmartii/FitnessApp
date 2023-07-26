package com.example.fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitnessapp.profile_feature.presentation.sign_in.SignInScreen
import com.example.fitnessapp.profile_feature.presentation.sign_in.SignInViewModel
import com.example.fitnessapp.ui.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                val viewModel = hiltViewModel<SignInViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                SignInScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    calculate = viewModel::calculateCalories
                )
            }
        }
    }
}