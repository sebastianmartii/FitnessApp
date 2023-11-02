package com.example.fitnessapp.profile_feature.presentation.profile

import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.profile_feature.data.mappers.toCalculatedCaloriesList
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.repository.ProfileRepository
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileRepository,
    currentUserDao: CurrentUserDao
) : NavigationDrawerViewModel(currentUserDao) {

    private val _shouldValidatorsUpdate = MutableStateFlow(false)

    private val _pendingNavigationRoute = MutableStateFlow("")

    private val _state = MutableStateFlow(ProfileState())

    init {
        viewModelScope.launch {
            repo.getCurrentUser().collectLatest {  currentUser ->
                if (currentUser != null) {
                    _state.update {
                        it.copy(
                            userName = currentUser.name,
                            caloriesGoal = currentUser.caloriesGoal.toString(),
                            age = currentUser.age.toString(),
                            gender = Gender.valueOf(currentUser.gender),
                            activityLevel = ActivityLevel.valueOf(currentUser.activityLevel),
                            weight = currentUser.weight.toString(),
                            height = currentUser.height.toString(),
                            isSaveUserActionVisible = false
                        )
                    }
                }
            }
        }
    }

    val state = _state.asStateFlow()

    private val _channel = Channel<UiEvent>()
    val eventFlow = _channel.receiveAsFlow()

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.OnActivityLevelChange -> {
                _state.update {
                    it.copy(
                        activityLevel = event.newActivityLevel,
                        isSaveUserActionVisible = true
                    )
                }
            }
            is ProfileEvent.OnActivityLevelExpandedChange -> {
                _state.update {
                    it.copy(
                        activityLevelExpanded = event.expanded
                    )
                }
            }
            is ProfileEvent.OnAgeChange -> {
                if (_shouldValidatorsUpdate.value) {
                    _state.update {
                        it.copy(
                            age = event.newAge,
                            isAgeValid = event.isAgeValid,
                            isSaveUserActionVisible = true
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            age = event.newAge,
                            isSaveUserActionVisible = true
                        )
                    }
                }
            }
            is ProfileEvent.OnCaloriesGoalCalculate -> {
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
            is ProfileEvent.OnCaloriesGoalChange -> {
                if (_shouldValidatorsUpdate.value) {
                    _state.update {
                        it.copy(
                            caloriesGoal = event.newCaloriesGoal,
                            isCaloriesGoalValid = event.isCaloriesGoalValid,
                            isSaveUserActionVisible = true
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            caloriesGoal = event.newCaloriesGoal,
                            isSaveUserActionVisible = true
                        )
                    }
                }
            }
            is ProfileEvent.OnGenderChange -> {
                _state.update {
                    it.copy(
                        gender = event.newGender,
                        isSaveUserActionVisible = true
                    )
                }
            }
            is ProfileEvent.OnGenderExpandedChange -> {
                _state.update {
                    it.copy(
                        genderExpanded = event.expanded
                    )
                }
            }
            is ProfileEvent.OnHeightChange -> {
                if (_shouldValidatorsUpdate.value) {
                    _state.update {
                        it.copy(
                            height = event.newHeight,
                            isHeightValid = event.isHeightValid,
                            isSaveUserActionVisible = true
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            height = event.newHeight,
                            isSaveUserActionVisible = true
                        )
                    }
                }
            }
            is ProfileEvent.OnUserNameChange -> {
                _state.update {
                    it.copy(
                        userName = event.newUserName,
                        isSaveUserActionVisible = true
                    )
                }
            }
            is ProfileEvent.OnWeightChange -> {
                if (_shouldValidatorsUpdate.value) {
                    _state.update {
                        it.copy(
                            weight = event.newWeight,
                            isWeightValid = event.isWeightValid,
                            isSaveUserActionVisible = true
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            weight = event.newWeight,
                            isSaveUserActionVisible = true
                        )
                    }
                }
            }
            is ProfileEvent.OnUserUpdate -> {
                viewModelScope.launch {
                    if (event.isAgeValid && event.isHeightValid && event.isWeightValid && event.isCaloriesGoalValid) {
                        repo.changeUserName(event.state.userName)
                        repo.changeAge(event.state.age)
                        repo.changeHeight(event.state.height)
                        repo.changeWeight(event.state.weight)
                        repo.changeGender(event.state.gender)
                        repo.changeActivityLevel(event.state.activityLevel)
                        repo.changeCaloriesGoal(event.state.caloriesGoal)
                        if (_pendingNavigationRoute.value != "") {
                            _channel.send(UiEvent.Navigate(_pendingNavigationRoute.value))
                            _pendingNavigationRoute.value = ""
                        }
                    } else {
                        _channel.send(UiEvent.ShowSnackbar("Some Of The Updated Fields Are Not Valid, " +
                                "Please Go Over All Changes And Try Again"))
                        _state.update {
                            it.copy(
                                isAgeValid = event.isAgeValid,
                                isHeightValid = event.isHeightValid,
                                isWeightValid = event.isWeightValid,
                                isCaloriesGoalValid = event.isCaloriesGoalValid
                            )
                        }
                        _shouldValidatorsUpdate.value = true
                    }
                }
            }
            is ProfileEvent.OnUserUpdateDialogDismiss -> {
                _state.update {
                    it.copy(
                        isUserUpdateDialogVisible = false
                    )
                }
            }
            is ProfileEvent.OnUserUpdateDialogShow -> {
                _state.update {
                    it.copy(
                        isUserUpdateDialogVisible = true
                    )
                }
                _pendingNavigationRoute.value = event.pendingNavigationRoute
            }
            ProfileEvent.OnUserUpdateDialogDecline -> {
                viewModelScope.launch {
                    _channel.send(UiEvent.Navigate(_pendingNavigationRoute.value))
                    _pendingNavigationRoute.value = ""
                }
            }
        }
    }

    sealed class UiEvent {
        data class Navigate(val route: String) : UiEvent()
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}