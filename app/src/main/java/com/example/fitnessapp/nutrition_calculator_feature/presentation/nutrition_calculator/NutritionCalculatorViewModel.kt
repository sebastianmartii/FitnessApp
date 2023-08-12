package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    private var searchJob: Job? = null

    fun onEvent(event: NutritionCalculatorEvent) {
        when(event) {
            is NutritionCalculatorEvent.OnFoodItemExpandedChange -> {
                if (event.isExpanded) {
                    _state.value.expandedFoodItems.remove(event.foodItem)
                } else {
                    _state.value.expandedFoodItems.add(event.foodItem)
                }
            }
            is NutritionCalculatorEvent.OnFoodItemSelectedChange -> {
                if (event.isSelected) {
                    _state.value.selectedProducts.remove(event.foodItem)
                } else {
                    _state.value.selectedProducts.add(event.foodItem)
                }
            }
            is NutritionCalculatorEvent.OnIsSearchBarActiveChange -> {
                _state.update {
                    it.copy(
                        isSearchBarActive = event.isActive
                    )
                }
            }
            is NutritionCalculatorEvent.OnNutritionCalculate -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    repo.getFoodNutrition(event.query).collectLatest { result ->
                        when(result) {
                            is Resource.Error -> {
                                println(result.message)
                                _state.update {
                                    it.copy(
                                        calculatedProducts = result.data ?: emptyList()
                                    )
                                }
                            }
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(
                                        calculatedProducts = result.data ?: emptyList(),
                                        isCalculationLoading = true
                                    )
                                }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        calculatedProducts = result.data ?: emptyList()
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is NutritionCalculatorEvent.OnQueryChange -> {
                _state.update {
                    it.copy(
                        query = event.query
                    )
                }
            }
        }
    }
}