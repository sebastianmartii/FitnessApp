package com.example.fitnessapp.activities_feature.presentation

import com.example.fitnessapp.activities_feature.domain.repository.ActivitiesRepository
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val repo: ActivitiesRepository
) : NavigationDrawerViewModel() {

    private val _state = MutableStateFlow(ActivitiesState())
    val state = _state.asStateFlow()

    fun onEvent(event: ActivitiesEvent) {
        when(event) {
            is ActivitiesEvent.OnIntensityLevelExpandedChange -> {

            }
        }
    }
}