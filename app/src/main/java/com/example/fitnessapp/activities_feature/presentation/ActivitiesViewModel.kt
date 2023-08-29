package com.example.fitnessapp.activities_feature.presentation

import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.activities_feature.data.mappers.toSavedActivity
import com.example.fitnessapp.activities_feature.domain.model.IntensityItem
import com.example.fitnessapp.activities_feature.domain.repository.ActivitiesRepository
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import com.example.fitnessapp.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val repo: ActivitiesRepository
) : NavigationDrawerViewModel() {

    private val _initialIntensityItems = listOf(
        IntensityItem(intensityLevel = IntensityLevel.INTENSITY_1),
        IntensityItem(intensityLevel = IntensityLevel.INTENSITY_2),
        IntensityItem(intensityLevel = IntensityLevel.INTENSITY_3),
        IntensityItem(intensityLevel = IntensityLevel.INTENSITY_4),
        IntensityItem(intensityLevel = IntensityLevel.INTENSITY_5),
        IntensityItem(intensityLevel = IntensityLevel.INTENSITY_6),
    )

    private val _savedActivities = repo.getSavedActivities()

    private val _state = MutableStateFlow(ActivitiesState(intensityLevels = _initialIntensityItems))
    val state = _state.combine(_savedActivities) { state, savedActivities ->
        state.copy(
            savedActivities = savedActivities.map { it.toSavedActivity() }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ActivitiesState(intensityLevels = _initialIntensityItems))

    fun onEvent(event: ActivitiesEvent) {
        when(event) {
            is ActivitiesEvent.OnIntensityLevelActivitiesFetch -> {
                viewModelScope.launch {
                    repo.getActivitiesForIntensityLevel(event.intensityLevel.level).collect { result ->
                        when(result) {
                            is Resource.Error -> {
                                val updatedIntensityLevels = _state.value.intensityLevels.toMutableList()
                                updatedIntensityLevels[event.intensityLevelIndex] = updatedIntensityLevels[event.intensityLevelIndex].copy(
                                    isLoading = false,
                                    activities = result.data ?: emptyList()
                                )
                                _state.update {
                                    it.copy(
                                        intensityLevels = updatedIntensityLevels
                                    )
                                }
                            }
                            is Resource.Loading -> {
                                val updatedIntensityLevels = _state.value.intensityLevels.toMutableList()
                                updatedIntensityLevels[event.intensityLevelIndex] = updatedIntensityLevels[event.intensityLevelIndex].copy(
                                    isLoading = true,
                                    activities = result.data ?: emptyList()
                                )
                                _state.update {
                                    it.copy(
                                        intensityLevels = updatedIntensityLevels
                                    )
                                }
                            }
                            is Resource.Success -> {
                                val updatedIntensityLevels = _state.value.intensityLevels.toMutableList()
                                updatedIntensityLevels[event.intensityLevelIndex] = updatedIntensityLevels[event.intensityLevelIndex].copy(
                                    isLoading = false,
                                    activities = result.data ?: emptyList(),
                                )
                                _state.update {
                                    it.copy(
                                        intensityLevels = updatedIntensityLevels
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is ActivitiesEvent.OnIntensityLevelExpandedChange -> {
                val updatedIntensityLevels = _state.value.intensityLevels.toMutableList()
                updatedIntensityLevels[event.intensityLevelIndex] = updatedIntensityLevels[event.intensityLevelIndex].copy(
                    isExpanded = event.isExpanded
                )
                _state.update {
                    it.copy(
                        intensityLevels = updatedIntensityLevels
                    )
                }
            }
            is ActivitiesEvent.OnActivityClick -> {
                _state.update {
                    it.copy(
                        isBurnedCaloriesDialogVisible = true
                    )
                }
            }
            is ActivitiesEvent.OnActivitiesTabChange -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = event.tabIndex,
                        currentSelectedActivitiesTabRowItem = event.activitiesTabRowItem
                    )
                }
            }
            ActivitiesEvent.OnBurnedCaloriesDialogDismiss -> {
                _state.update {
                    it.copy(
                        isBurnedCaloriesDialogVisible = false
                    )
                }
            }
            is ActivitiesEvent.OnBurnedCaloriesDialogConfirm -> {

            }
            is ActivitiesEvent.OnMinutesChange -> {
                _state.update {
                    it.copy(
                        minutes = event.minutes
                    )
                }
            }
            is ActivitiesEvent.OnSecondsChange -> {
                _state.update {
                    it.copy(
                        seconds = event.seconds
                    )
                }
            }
        }
    }
}