package com.example.fitnessapp.activities_feature.presentation

import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.activities_feature.data.mappers.toSavedActivity
import com.example.fitnessapp.activities_feature.domain.model.IntensityItem
import com.example.fitnessapp.activities_feature.domain.model.SavedActivity
import com.example.fitnessapp.activities_feature.domain.repository.ActivitiesRepository
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import com.example.fitnessapp.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _savedActivities = MutableStateFlow<List<SavedActivity>>(emptyList())

    init {
        viewModelScope.launch {
            repo.getSavedActivities().collectLatest { savedActivitiesEntity ->
                _savedActivities.value = savedActivitiesEntity.map { it.toSavedActivity() }
            }
        }
    }

    private val _filterQuery = MutableStateFlow("")

    private val _state = MutableStateFlow(ActivitiesState(intensityLevels = _initialIntensityItems))
    val state = combine(_state, _savedActivities, _filterQuery) { state, savedActivities, filterQuery ->
        state.copy(
            filterQuery = filterQuery,
            savedActivities = if (filterQuery.isBlank()) {
                savedActivities
            } else {
                savedActivities
                    .map {
                        it
                    }
                    .filter {
                        it.name.contains(filterQuery, ignoreCase = true) ||
                                it.description?.contains(filterQuery, ignoreCase = true) == true
                    }
            },
            intensityLevels = if (filterQuery.isBlank()) {
                state.intensityLevels
            } else {
                state.intensityLevels
                    .map {
                        it.copy(
                            activities = it.activities.filter { activity ->
                                activity.name.contains(filterQuery, ignoreCase = true) ||
                                        activity.description.contains(filterQuery, ignoreCase = true)
                            }
                        )
                    }
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ActivitiesState(intensityLevels = _initialIntensityItems))

    private val _channel = Channel<Int>()
    val pagerFlow = _channel.receiveAsFlow()

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
                        isBurnedCaloriesDialogVisible = true,
                        chosenActivity = event.activity
                    )
                }
            }
            is ActivitiesEvent.OnActivitiesTabChange -> {
                viewModelScope.launch {
                    _channel.send(event.index)
                }
            }
            ActivitiesEvent.OnBurnedCaloriesDialogDismiss -> {
                _state.update {
                    it.copy(
                        isBurnedCaloriesDialogVisible = false,
                        minutes = "",
                        seconds = ""
                    )
                }
            }
            is ActivitiesEvent.OnBurnedCaloriesDialogConfirm -> {
                viewModelScope.launch {
                    repo.getCaloriesBurnedForActivity(event.activity, event.duration)
                }
                _state.update {
                    it.copy(
                        minutes = "",
                        seconds = "",
                        chosenActivity = null
                    )
                }
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
            is ActivitiesEvent.OnFilterActivities -> {
                _state.update {
                    it.copy(
                        areActivitiesFiltered = event.areActivitiesFiltered
                    )
                }
            }
            is ActivitiesEvent.OnFilterQueryChange -> {
                _filterQuery.value = event.query

            }
            is ActivitiesEvent.OnFilterQueryClear -> {
                _filterQuery.value = ""

            }
            is ActivitiesEvent.OnSavedActivityClick -> {
                val updatedSavedActivities = state.value.savedActivities.toMutableList()
                updatedSavedActivities[event.savedActivityIndex] = updatedSavedActivities[event.savedActivityIndex].copy(
                    isSelected = !event.isSelected
                )
                _savedActivities.value = updatedSavedActivities
                _state.update {
                    it.copy(
                        isSavedActivitiesFABVisible = updatedSavedActivities.any { savedActivity -> savedActivity.isSelected }
                    )
                }
            }
            is ActivitiesEvent.OnSavedActivitiesDelete -> {
                viewModelScope.launch {
                    repo.deleteSavedActivities(event.deletedActivities)
                }
                _state.update {
                    it.copy(
                        isSavedActivitiesFABVisible = false
                    )
                }
            }
            is ActivitiesEvent.OnSavedActivitiesPerform -> {
                viewModelScope.launch {
                    repo.performSavedActivities(
                        event.performedActivities
                    )
                }
            }
        }
    }
}