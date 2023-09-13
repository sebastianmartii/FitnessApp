package com.example.fitnessapp.daily_overview_feature.presentation

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import com.example.fitnessapp.daily_overview_feature.data.mappers.mapDailyNutritionEntityToMealDetails
import com.example.fitnessapp.daily_overview_feature.domain.repository.OverviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyOverviewViewModel @Inject constructor(
    private val repo: OverviewRepository
) : NavigationDrawerViewModel() {

    private val _currentCaloriesCount = MutableStateFlow(1000)

    private val _state = MutableStateFlow(OverviewState())

    private val _meals = repo.getMeals()

    init {
        viewModelScope.launch {
            repo.getCurrentUserCaloriesRequirements().collect { caloriesGoal ->
                _state.update {
                    it.copy(
                        caloriesGoal = caloriesGoal
                    )
                }
            }
        }
        viewModelScope.launch {
            repo.getMealDetails().collectLatest { dailyNutritionEntities ->
                _state.update {
                    it.copy(
                        mealDetails = mapDailyNutritionEntityToMealDetails(dailyNutritionEntities)
                    )
                }
            }
        }
    }

    val state = combine(_state, _meals, _currentCaloriesCount) { state, meals, currentCaloriesCount ->
        state.copy(
            currentCaloriesCount = currentCaloriesCount,
            progress = 330.dp * currentCaloriesCount / state.caloriesGoal,
            mealPlan = meals
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), OverviewState())

    fun onEvent(event: OverviewEvent) {
        when(event) {
            is OverviewEvent.OnMealDetailsExpand -> {
                var updatedMealDetails = _state.value.mealDetails
                updatedMealDetails = updatedMealDetails.map { details ->
                    if (details.meal == event.meal) {
                        details.copy(
                            areVisible = !details.areVisible
                        )
                    } else details
                }
                _state.update {
                    it.copy(
                        mealDetails = updatedMealDetails
                    )
                }
            }
            is OverviewEvent.OnMealReset -> {

            }
        }
    }
}