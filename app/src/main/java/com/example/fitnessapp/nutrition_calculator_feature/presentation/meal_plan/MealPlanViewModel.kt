package com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.filled.Looks3
import androidx.compose.material.icons.filled.Looks4
import androidx.compose.material.icons.filled.Looks5
import androidx.compose.material.icons.outlined.Filter3
import androidx.compose.material.icons.outlined.Filter4
import androidx.compose.material.icons.outlined.Filter5
import androidx.compose.material.icons.outlined.Quiz
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.CustomMealPlanCreatorRepository
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
class MealPlanViewModel @Inject constructor(
    private val repo: CustomMealPlanCreatorRepository
) : ViewModel() {

    private val _fiveMealPLan = MutableStateFlow(MealPlan(
        name = "Five Meal Plan",
        type = MealPlanType.FIVE,
        leadingIcon = Icons.Outlined.Filter5,
        selectedLeadingIcon = Icons.Filled.Looks5,
        isExpanded = false,
        meals = mutableListOf(
            "Breakfast",
            "Second Breakfast",
            "Lunch",
            "Snack",
            "Dinner"
        )
    ))

    private val _fourMealPlan = MutableStateFlow(MealPlan(
        name = "Four Meal Plan",
        type = MealPlanType.FOUR,
        leadingIcon = Icons.Outlined.Filter4,
        selectedLeadingIcon = Icons.Filled.Looks4,
        isExpanded = false,
        meals = mutableListOf(
            "Breakfast",
            "Second Breakfast",
            "Lunch",
            "Dinner"
        )
    ))

    private val _threeMealPlan = MutableStateFlow(MealPlan(
        name = "Three Meal Plan",
        type = MealPlanType.THREE,
        leadingIcon = Icons.Outlined.Filter3,
        selectedLeadingIcon = Icons.Filled.Looks3,
        isExpanded = false,
        meals = mutableListOf(
            "Breakfast",
            "Lunch",
            "Dinner"
        )
    ))

    private val _customMealPlan = MutableStateFlow(MealPlan(
        name = "Custom Meal Plan",
        type = MealPlanType.CUSTOM,
        leadingIcon = Icons.Outlined.Quiz,
        selectedLeadingIcon = Icons.AutoMirrored.Filled.HelpCenter,
        isExpanded = false,
        meals = mutableListOf(
            "Meal"
        )
    ))

    private val _state = MutableStateFlow(MealPlanState())

    init {
        viewModelScope.launch {
            repo.getMealPlan().collectLatest { selectedMealPlan ->
                try {
                    _state.update {
                        it.copy(
                            selectedMealPlan = selectedMealPlan.mealPlanType
                        )
                    }
                } catch (_: NullPointerException) {

                }
            }
        }
    }

    val state = combine(
        _state,
        _fiveMealPLan,
        _fourMealPlan,
        _threeMealPlan,
        _customMealPlan
    ) { state, fiveMealPlan, fourMealPlan, threeMealPlan, customMealPlan ->
        state.copy(
            fiveMealPlan = fiveMealPlan,
            fourMealPlan = fourMealPlan,
            threeMealPlan = threeMealPlan,
            customMealPlan = customMealPlan
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MealPlanState())

    private val _channel = Channel<UiEvent>()
    val eventFlow = _channel.receiveAsFlow()

    fun onEvent(event: MealPlanEvent) {
        when(event) {
            is MealPlanEvent.OnMealPlanExpandedChange -> {
                when(event.type) {
                    MealPlanType.FIVE -> {
                        _fiveMealPLan.update {
                            it.copy(
                                isExpanded = event.isExpanded
                            )
                        }
                    }
                    MealPlanType.FOUR -> {
                        _fourMealPlan.update {
                            it.copy(
                                isExpanded = event.isExpanded
                            )
                        }
                    }
                    MealPlanType.THREE -> {
                        _threeMealPlan.update {
                            it.copy(
                                isExpanded = event.isExpanded
                            )
                        }
                    }
                    MealPlanType.CUSTOM -> {
                        _customMealPlan.update {
                            it.copy(
                                isExpanded = event.isExpanded
                            )
                        }
                    }
                }
            }
            is MealPlanEvent.OnMealPlanSelectedChange -> {
                viewModelScope.launch {
                    if (event.type == MealPlanType.CUSTOM) {
                        _channel.send(UiEvent.OnBottomSheetOpen)
                    } else {
                        _state.update {
                            it.copy(
                                selectedMealPlan = event.type
                            )
                        }
                        repo.changeMealPlan(event.plan)
                    }
                }
            }
            MealPlanEvent.OnAddMeal -> {
                val currentCustomMealPlan = _customMealPlan.value
                val updatedItems = currentCustomMealPlan.meals.toMutableList()
                updatedItems.add("Meal")
                val updatedMealPlan = currentCustomMealPlan.copy(meals = updatedItems)
                _customMealPlan.value = updatedMealPlan
            }
            is MealPlanEvent.OnDeleteMeal -> {
                val currentCustomMealPlan = _customMealPlan.value
                val updatedItems = currentCustomMealPlan.meals.toMutableList()
                updatedItems.removeAt(event.mealIndex)
                val updatedMealPlan = currentCustomMealPlan.copy(meals = updatedItems)
                _customMealPlan.value = updatedMealPlan
            }
            is MealPlanEvent.OnMealNameChange -> {
                val currentCustomMealPlan = _customMealPlan.value
                val updatedItems = currentCustomMealPlan.meals.toMutableList()
                updatedItems[event.index] = event.name
                val updatedMealPlan = currentCustomMealPlan.copy(meals = updatedItems)
                _customMealPlan.value = updatedMealPlan
            }
            MealPlanEvent.OnSheetClose -> {
                viewModelScope.launch {
                    _channel.send(UiEvent.OnBottomSheetClose)
                }
            }
            is MealPlanEvent.OnCustomMealPlanSave -> {
                _state.update {
                    it.copy(
                        selectedMealPlan = MealPlanType.CUSTOM
                    )
                }
                viewModelScope.launch {
                    repo.changeMealPlan(event.plan)
                }
            }
        }
    }

    sealed class UiEvent {
        object OnBottomSheetOpen : UiEvent()
        object OnBottomSheetClose : UiEvent()
    }
}