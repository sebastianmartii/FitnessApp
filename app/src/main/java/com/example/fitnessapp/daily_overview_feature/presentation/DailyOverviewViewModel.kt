package com.example.fitnessapp.daily_overview_feature.presentation

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.daily_overview_feature.domain.repository.OverviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyOverviewViewModel @Inject constructor(
    private val repo: OverviewRepository
) : ViewModel() {

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

    private val _channel = Channel<UiEvent>()
    val eventFlow = _channel.receiveAsFlow()

    fun onEvent(event: OverviewEvent) {
        when(event) {
            OverviewEvent.CloseDrawer -> {
                viewModelScope.launch {
                    _channel.send(UiEvent.CloseNavigationDrawer)
                }
            }
            is OverviewEvent.OnDrawerItemSelect -> {
                _state.update {
                    it.copy(
                        selectedDrawerItem = event.selectedDrawerItem
                    )
                }
            }
            OverviewEvent.OpenDrawer -> {
                viewModelScope.launch {
                    _channel.send(UiEvent.OpenNavigationDrawer)
                }
            }
            OverviewEvent.AddMeal -> {
                _currentCaloriesCount.value = _currentCaloriesCount.value + 500
            }
        }
    }

    sealed class UiEvent {
        object OpenNavigationDrawer : UiEvent()
        object CloseNavigationDrawer : UiEvent()
    }
}