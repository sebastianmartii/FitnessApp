package com.example.fitnessapp.daily_overview_feature.presentation

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import com.example.fitnessapp.daily_overview_feature.domain.repository.OverviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyOverviewViewModel @Inject constructor(
    private val repo: OverviewRepository
) : NavigationDrawerViewModel() {

    private val _caloriesGoal = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            repo.getCurrentUserCaloriesRequirements().collect { caloriesGoal ->
                _caloriesGoal.value = caloriesGoal
            }
        }
    }

    private val _currentCaloriesCount = MutableStateFlow(1000)

    private val _state = MutableStateFlow(OverviewState())
    val state = combine(_state, _caloriesGoal, _currentCaloriesCount) { state, caloriesGoal, currentCaloriesCount ->
        state.copy(
            caloriesGoal = caloriesGoal,
            currentCaloriesCount = currentCaloriesCount,
            progress = 330.dp * currentCaloriesCount / caloriesGoal
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), OverviewState())

    fun onEvent(event: OverviewEvent) {
        when(event) {
            OverviewEvent.OnAddMeal -> {
                _currentCaloriesCount.value = _currentCaloriesCount.value + 500
            }
        }
    }
}