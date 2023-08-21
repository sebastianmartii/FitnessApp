package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toFoodItem
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionCalculatorViewModel @Inject constructor(
    private val repo: NutritionCalculatorRepository
) :ViewModel() {

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

    fun onEvent(event: NutritionCalculatorEvent) {
        when(event) {
            is NutritionCalculatorEvent.OnFoodItemSelectedChange -> {
                val currentState = _state.value
                val updatedItems = currentState.cachedProducts.toMutableList()

                val itemIndex = updatedItems.indexOf(event.foodItem)
                if (itemIndex != -1) {
                    updatedItems[itemIndex] = event.foodItem.copy(isSelected = !event.foodItem.isSelected)
                    val updatedState = currentState.copy(cachedProducts = updatedItems)
                    _state.value = updatedState
                }
            }
            is NutritionCalculatorEvent.OnFoodItemDelete -> {
                viewModelScope.launch {
                    repo.deleteFoodItem(event.foodItem)
                }
            }
        }
    }
}