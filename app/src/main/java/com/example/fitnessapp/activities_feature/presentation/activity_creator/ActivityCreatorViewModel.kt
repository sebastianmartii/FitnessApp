package com.example.fitnessapp.activities_feature.presentation.activity_creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.activities_feature.domain.repository.ActivitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityCreatorViewModel @Inject constructor(
    private val repo: ActivitiesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ActivityCreatorState())
    val state = _state.asStateFlow()

    fun onEvent(event: ActivityCreatorEvent) {
        when(event) {
            is ActivityCreatorEvent.OnActivityBurnedCaloriesChange -> {
                _state.update {
                    it.copy(
                        burnedCalories = event.burnedCalories
                    )
                }
            }
            is ActivityCreatorEvent.OnActivityDescriptionChange -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }
            is ActivityCreatorEvent.OnActivityMinutesChange -> {
                _state.update {
                    it.copy(
                        minutes = event.minutes
                    )
                }
            }
            is ActivityCreatorEvent.OnActivitySecondsChange -> {
                _state.update {
                    it.copy(
                        seconds = event.seconds
                    )
                }
            }
            is ActivityCreatorEvent.OnActivityNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is ActivityCreatorEvent.OnActivitySave -> {
                viewModelScope.launch {
                    repo.saveActivity(
                        event.name,
                        event.description,
                        event.duration,
                        event.burnedCalories
                    )
                }
            }
        }
    }
}