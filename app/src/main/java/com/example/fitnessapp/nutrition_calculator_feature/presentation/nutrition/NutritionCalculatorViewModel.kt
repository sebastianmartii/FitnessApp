package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toFoodItem
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionCalculatorViewModel @Inject constructor(
    private val repo: NutritionCalculatorRepository,
) :ViewModel() {

    val meals = repo.getMeals()

    private val _state = MutableStateFlow(NutritionCalculatorState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAllCachedFoodItems().collectLatest { list ->
                _state.update {
                    it.copy(
                        cachedProducts = list.map { entity ->
                            entity.toFoodItem()
                        }
                    )
                }
            }
        }
    }

    private val _eventChannel = Channel<UiEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onEvent(event: NutritionCalculatorEvent) {
        when(event) {
            is NutritionCalculatorEvent.OnFoodItemSelectedChange -> {
                viewModelScope.launch {
                    val updatedItems = _state.value.cachedProducts.toMutableList()
                    updatedItems[event.itemIndex] = event.foodItem.copy(isSelected = !event.foodItem.isSelected)

                    _state.update {
                        it.copy(
                            cachedProducts = updatedItems,
                            isFABVisible = updatedItems.any { foodItem ->  foodItem.isSelected }
                        )
                    }
                }
            }
            is NutritionCalculatorEvent.OnFoodItemDelete -> {
                viewModelScope.launch {
                    repo.deleteFoodItems(_state.value.cachedProducts.filter { it.isSelected })
                    _state.update {
                        it.copy(
                            isFABVisible = false
                        )
                    }
                }
            }
            is NutritionCalculatorEvent.OnFoodItemsAdd -> {
                if (event.areMealsEmpty) {
                    viewModelScope.launch {
                        _eventChannel.send(UiEvent.NavigateToMealPlanScreen)
                    }
                } else {
                    _state.update {
                        it.copy(
                            isMealSelectionDialogVisible = true
                        )
                    }
                }
            }
            is NutritionCalculatorEvent.OnMealSelectionDialogConfirm -> {
                viewModelScope.launch {
                    repo.addFoodItemsToDailyNutrition(
                        _state.value.cachedProducts.filter { it.isSelected },
                        event.meal!!
                    )
                    _state.update {
                        it.copy(
                            selectedMeal = null
                        )
                    }
                }
            }
            NutritionCalculatorEvent.OnMealSelectionDialogDismiss -> {
                _state.update {
                    it.copy(
                        isMealSelectionDialogVisible = false,
                        cachedProducts = _state.value.cachedProducts.map { foodItem ->
                            foodItem.copy(
                                isSelected = false
                            )
                        },
                        isFABVisible = false
                    )
                }
            }
            is NutritionCalculatorEvent.OnMealSelectionDialogMealSelect -> {
                _state.update {
                    it.copy(
                        selectedMeal = event.meal
                    )
                }
            }
        }
    }

    sealed class UiEvent {
        object NavigateToMealPlanScreen : UiEvent()
    }
}