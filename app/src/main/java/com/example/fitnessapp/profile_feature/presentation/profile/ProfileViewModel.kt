package com.example.fitnessapp.profile_feature.presentation.profile

import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import com.example.fitnessapp.profile_feature.domain.model.Gender
import com.example.fitnessapp.profile_feature.domain.repository.ProfileRepository
import com.example.fitnessapp.profile_feature.presentation.sign_in.ActivityLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileRepository,
    currentUserDao: CurrentUserDao
) : NavigationDrawerViewModel(currentUserDao) {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

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
                            height = currentUser.height.toString()
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.OnActivityLevelChange -> {
                _state.update {
                    it.copy(
                        activityLevel = event.newActivityLevel
                    )
                }
                viewModelScope.launch {
                    repo.changeActivityLevel(event.newActivityLevel)
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
                _state.update {
                    it.copy(
                        age = event.newAge
                    )
                }
                viewModelScope.launch {
                    repo.changeAge(event.newAge)
                }
            }
            is ProfileEvent.OnCaloriesGoalCalculate -> {

            }
            is ProfileEvent.OnCaloriesGoalChange -> {
                _state.update {
                    it.copy(
                        caloriesGoal = event.newCaloriesGoal
                    )
                }
                viewModelScope.launch {
                    repo.changeCaloriesGoal(event.newCaloriesGoal)
                }
            }
            is ProfileEvent.OnCaloriesGoalSelect -> {

            }
            is ProfileEvent.OnGenderChange -> {
                _state.update {
                    it.copy(
                        gender = event.newGender
                    )
                }
                viewModelScope.launch {
                    repo.changeGender(event.newGender)
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
                _state.update {
                    it.copy(
                        height = event.newHeight
                    )
                }
                viewModelScope.launch {
                    repo.changeHeight(event.newHeight)
                }
            }
            is ProfileEvent.OnUserNameChange -> {
                _state.update {
                    it.copy(
                        userName = event.newUserName
                    )
                }
                viewModelScope.launch {
                    repo.changeUserName(event.newUserName)
                }
            }
            is ProfileEvent.OnWeightChange -> {
                _state.update {
                    it.copy(
                        weight = event.newWeight
                    )
                }
                viewModelScope.launch {
                    repo.changeWeight(event.newWeight)
                }
            }
        }
    }
}