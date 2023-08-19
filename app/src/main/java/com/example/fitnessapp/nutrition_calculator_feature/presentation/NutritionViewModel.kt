package com.example.fitnessapp.nutrition_calculator_feature.presentation

import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NutritionViewModel @Inject constructor(

) : NavigationDrawerViewModel() {

    private val _state = MutableStateFlow(NutritionState())
    val state = _state.asStateFlow()

    fun onEvent(event: NutritionEvent) {
        when(event) {
            is NutritionEvent.OnTabChange -> {
                _state.update {
                    it.copy(
                        currentTabRowItem = event.tabRowItem,
                        selectedTabIndex = event.tabIndex
                    )
                }
            }
        }
    }
}