package com.example.fitnessapp.daily_overview_feature.presentation

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import com.example.fitnessapp.core.util.capitalizeEachWord
import com.example.fitnessapp.daily_overview_feature.data.mappers.mapDailyActivitiesToActivity
import com.example.fitnessapp.daily_overview_feature.data.mappers.mapDailyNutritionEntityToMealDetails
import com.example.fitnessapp.daily_overview_feature.domain.repository.OverviewRepository
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
import kotlin.math.max

@HiltViewModel
class DailyOverviewViewModel @Inject constructor(
    private val repo: OverviewRepository,
    currentUserDao: CurrentUserDao
) : NavigationDrawerViewModel(currentUserDao) {

    private val _state = MutableStateFlow(OverviewState())

    private val _meals = repo.getMeals()

    init {
        viewModelScope.launch {
            repo.getCurrentUserCaloriesRequirements().collect { caloriesGoal ->
                if (caloriesGoal != null) {
                    _state.update {
                        it.copy(
                            caloriesGoal = caloriesGoal
                        )
                    }
                }
            }
        }
        viewModelScope.launch {
            repo.getMealDetails().collectLatest { dailyNutritionEntities ->
                _state.update {
                    it.copy(
                        mealDetails = mapDailyNutritionEntityToMealDetails(dailyNutritionEntities),
                        currentCaloriesCount = dailyNutritionEntities.sumOf { dailyNutritionEntity -> dailyNutritionEntity.calories.toInt() }
                    )
                }
            }
        }
        viewModelScope.launch {
            repo.getActivities().collectLatest {  dailyActivitiesEntities ->
                _state.update {
                    it.copy(
                        activities = mapDailyActivitiesToActivity(dailyActivitiesEntities)
                    )
                }
            }
        }
    }

    val state = combine(_state, _meals) { state, meals ->
        state.copy(
            progress = 330.dp * state.currentCaloriesCount / max(1, state.caloriesGoal),
            mealPlan = meals
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), OverviewState())

    private val _channel = Channel<String>()
    val snackbarFlow = _channel.receiveAsFlow()

    fun onEvent(event: OverviewEvent) {
        when(event) {
            is OverviewEvent.OnMealDetailsExpand -> {
                if (event.areDetailsEmpty) {
                    viewModelScope.launch {
                        _channel.send("Nothing to display here")
                    }
                } else {
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
            }
            is OverviewEvent.OnMealReset -> {
                viewModelScope.launch {
                    repo.resetMeal(event.meal)
                    _channel.send("${event.meal} has been cleared")
                }
            }
            is OverviewEvent.OnActivityDelete -> {
                viewModelScope.launch {
                    repo.deleteActivity(event.activity)
                    _channel.send("${event.activity.name.capitalizeEachWord()} activity has been deleted")
                }
            }
        }
    }
}