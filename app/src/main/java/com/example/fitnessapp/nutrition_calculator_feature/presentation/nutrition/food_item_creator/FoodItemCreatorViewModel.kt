package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition.food_item_creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toCalories
import com.example.fitnessapp.nutrition_calculator_feature.data.mappers.toGrams
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodItemCreatorViewModel @Inject constructor(
    private val repo: NutritionCalculatorRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(FoodItemCreatorState())
    val state = _state.asStateFlow()

    private val _channel = Channel<UiEvent>()
    val snackbarFlow = _channel.receiveAsFlow()

    fun onEvent(event: FoodItemCreatorEvent) {
        when(event) {
            is FoodItemCreatorEvent.OnCaloriesChange -> {
                _state.update {
                    it.copy(
                        calories = event.calories
                    )
                }
            }
            is FoodItemCreatorEvent.OnCarbsChange -> {
                _state.update {
                    it.copy(
                        carbs = event.carbs
                    )
                }
            }
            is FoodItemCreatorEvent.OnFiberChange -> {
                _state.update {
                    it.copy(
                        fiber = event.fiber
                    )
                }
            }
            is FoodItemCreatorEvent.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is FoodItemCreatorEvent.OnProteinChange -> {
                _state.update {
                    it.copy(
                        protein = event.protein
                    )
                }
            }
            is FoodItemCreatorEvent.OnSaturatedFatChange -> {
                _state.update {
                    it.copy(
                        saturatedFat = event.saturatedFat
                    )
                }
            }
            is FoodItemCreatorEvent.OnServingSizeChange -> {
                _state.update {
                    it.copy(
                        servingSize = event.servingSize
                    )
                }
            }
            is FoodItemCreatorEvent.OnSugarChange -> {
                _state.update {
                    it.copy(
                        sugar = event.sugar
                    )
                }
            }
            is FoodItemCreatorEvent.OnTotalFatChange -> {
                _state.update {
                    it.copy(
                        totalFat = event.totalFat
                    )
                }
            }
            is FoodItemCreatorEvent.OnFoodItemCreated -> {
                viewModelScope.launch {
                    if (event.isFoodItemValid) {
                        repo.insertFoodItem(
                            FoodItem(
                                name = _state.value.name,
                                servingSize = _state.value.servingSize.toGrams(),
                                calories = _state.value.calories.toCalories(),
                                carbs = _state.value.carbs.toGrams(),
                                protein = _state.value.protein.toGrams(),
                                totalFat = _state.value.totalFat.toGrams(),
                                saturatedFat = _state.value.saturatedFat.toGrams(),
                                fiber = _state.value.fiber.toGrams(),
                                sugar = _state.value.sugar.toGrams()
                            )
                        )
                        _channel.send(UiEvent.NavigateBack)
                    } else {
                        _channel.send(UiEvent.ShowSnackbar("Some Components Are Not Valid." +
                                " Provide Valid Components To Create A Food Item"))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object NavigateBack : UiEvent()
    }
}