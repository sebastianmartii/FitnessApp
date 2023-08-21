package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.food_nutrition_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.util.Resource
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodNutritionSearchViewModel @Inject constructor(
    private val repo: NutritionCalculatorRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FoodNutritionSearchState())
    val state = _state.asStateFlow()

    fun onEvent(event: FoodNutritionSearchEvent) {
        when(event) {
            is FoodNutritionSearchEvent.OnFoodItemSelect -> {
                val currentState = _state.value
                val updatedItems = currentState.foodItems.toMutableList()

                val itemIndex = updatedItems.indexOf(event.foodItem)
                if (itemIndex != -1) {
                    updatedItems[itemIndex] = event.foodItem.copy(isSelected = !event.foodItem.isSelected)
                    val updatedState = currentState.copy(foodItems = updatedItems)
                    _state.value = updatedState
                }
            }
            is FoodNutritionSearchEvent.OnQueryChange -> {
                _state.update {
                    it.copy(
                        query = event.query
                    )
                }
            }
            FoodNutritionSearchEvent.OnFoodItemsSave -> {
                viewModelScope.launch {
                    val selectedFoodItems = _state.value.foodItems.filter { it.isSelected }
                    repo.cacheChosenProducts(selectedFoodItems)
                }
            }
            FoodNutritionSearchEvent.OnNutritionSearch -> {
                viewModelScope.launch {
                    repo.getFoodNutrition(_state.value.query).collectLatest { result ->
                        when(result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        foodItems = result.data ?: emptyList(),
                                        errorMessage = result.message ?: "",
                                        isLoading = false
                                    )
                                }
                            }
                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(
                                        foodItems = result.data ?: emptyList(),
                                        isLoading = true
                                    )
                                }
                            }
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        foodItems = result.data ?: emptyList(),
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}