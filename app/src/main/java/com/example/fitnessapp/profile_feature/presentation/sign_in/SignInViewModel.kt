package com.example.fitnessapp.profile_feature.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.profile_feature.data.mappers.toCalculatedCaloriesList
import com.example.fitnessapp.profile_feature.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: ProfileRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun calculateCalories() {
        viewModelScope.launch {
            repo.getCaloriesGoals(
                _state.value.age.toInt(),
                _state.value.height.toInt(),
                _state.value.weight.toInt(),
                _state.value.gender,
                _state.value.activityLevel
            ).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                calculatedCalories = result.data?.toCalculatedCaloriesList() ?: emptyList()
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                calculatedCalories = result.data?.toCalculatedCaloriesList() ?: emptyList()
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                calculatedCalories = result.data?.toCalculatedCaloriesList() ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is SignInEvent.OnGenderChange -> {
                _state.update {
                    it.copy(
                        gender = event.gender
                    )
                }
            }
            is SignInEvent.OnAgeChange -> {
                _state.update {
                    it.copy(
                        age = event.age
                    )
                }
            }
            is SignInEvent.OnHeightChange -> {
                _state.update {
                    it.copy(
                        height = event.height
                    )
                }
            }
            is SignInEvent.OnWeightChange -> {
                _state.update {
                    it.copy(
                        weight = event.weight
                    )
                }
            }
            is SignInEvent.OnActivityLevelChange -> {
                _state.update {
                    it.copy(
                        activityLevel = event.activityLevel
                    )
                }
            }
            is SignInEvent.OnCaloriesGoalChange -> {
                _state.update {
                    it.copy(
                        caloriesGoal = event.caloriesGoal
                    )
                }
            }
            SignInEvent.OnSignInComplete -> {
                viewModelScope.launch {
                    repo.addUser(
                        _state.value.name,
                        _state.value.age.toInt(),
                        _state.value.height.toFloat(),
                        _state.value.weight.toFloat(),
                        _state.value.gender,
                        _state.value.activityLevel,
                        _state.value.caloriesGoal.toInt(),
                    )
                }
            }
            SignInEvent.OnGetExistingProfiles -> {
                viewModelScope.launch {
                    repo.getUserProfiles().also { userProfileList ->
                        _state.update {
                            it.copy(
                                profileList = userProfileList
                            )
                        }
                    }
                }
            }
            is SignInEvent.OnSignInWithExistingProfile -> {
                viewModelScope.launch {
                    repo.signIn(event.userID)
                }
            }
        }
    }
}