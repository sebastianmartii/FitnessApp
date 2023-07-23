package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.profile_feature.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.IntroductionDone -> {
                _state.update {
                    it.copy(
                        name = event.name,
                        gender = event.gender,
                        signInProgress = SignInProgress.Measurements
                    )
                }
            }
            is ProfileEvent.MeasurementsProvided -> {
                _state.update {
                    it.copy(
                        height = event.height,
                        weight = event.weight,
                        age = event.age,
                        signInProgress = SignInProgress.ActivityLevelAndCaloriesGoal
                    )
                }
            }
            is ProfileEvent.CaloriesGoalChosen -> {
                _state.update {
                    it.copy(
                        caloriesGoal = event.caloriesGoal
                    )
                }
            }
            is ProfileEvent.SignInCompleted -> {
                _state.update {
                    it.copy(
                        activityLevel = event.activityLevel,
                        caloriesGoal = event.caloriesGoal
                    )
                }
                viewModelScope.launch {
                    repo.addUser(
                        name = _state.value.name,
                        age = _state.value.age,
                        height = _state.value.height,
                        weight = _state.value.weight,
                        gender = _state.value.gender,
                        activityLevel = _state.value.activityLevel,
                        caloriesGoal = _state.value.caloriesGoal
                    )
                }
                TODO("navigate from sign in")
            }
            ProfileEvent.OnCalculateCalories -> {
                _state.update {
                    it.copy(
                        signInProgress = SignInProgress.CaloriesGoalList
                    )
                }
            }
            ProfileEvent.OnChooseProfile -> {
                _state.update {
                    it.copy(
                        signInProgress = SignInProgress.ProfileList
                    )
                }
            }
            is ProfileEvent.ProfileChosen -> {
                TODO("navigate from sign in")
            }
            is ProfileEvent.OnGoBack -> {
                val previousProgress = when(event.currentProgress) {
                    SignInProgress.ActivityLevelAndCaloriesGoal -> SignInProgress.Measurements
                    SignInProgress.CaloriesGoalList -> SignInProgress.ActivityLevelAndCaloriesGoal
                    SignInProgress.Introduction -> SignInProgress.Introduction
                    SignInProgress.Measurements -> SignInProgress.Introduction
                    SignInProgress.ProfileList -> SignInProgress.Introduction
                }
                _state.update {
                    it.copy(
                        signInProgress = previousProgress
                    )
                }
            }
        }
    }
}