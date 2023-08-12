package com.example.fitnessapp.nutrition_calculator_feature.presentation.nutrition_calculator.food_item_creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.FoodItem
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.NutritionCalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodItemCreatorViewModel @Inject constructor(
    private val repo: NutritionCalculatorRepository
) : ViewModel() {

    private val _foodComponents = MutableStateFlow(initialFoodComponents)

    private val _foodItem = MutableStateFlow(FoodItem(
        name = "",
        servingSize = 0.0,
        calories = 0.0,
        carbs = 0.0,
        protein = 0.0,
        totalFat = 0.0,
        saturatedFat = 0.0,
        fiber = 0.0,
        sugar = 0.0
    ))

    private val _state = MutableStateFlow(FoodItemCreatorState())
    val state = _state.combine(_foodComponents) { state, foodComponents ->
        state.copy(
            foodComponents = foodComponents
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), FoodItemCreatorState())

    fun onEvent(event: FoodItemCreatorEvent) {
        when(event) {
            is FoodItemCreatorEvent.OnFoodComponentChange -> {
                _foodComponents.value[event.index].value = event.value
            }
            FoodItemCreatorEvent.OnFoodItemCreated -> {
                viewModelScope.launch {
                    _foodComponents.value.onEach {
                        when(it.type) {
                            FoodComponentType.NAME -> {
                                _foodItem.update {  item ->
                                    item.copy(
                                        name = it.value
                                    )
                                }
                            }
                            FoodComponentType.SERVING_SIZE -> {
                                _foodItem.update {  item ->
                                    item.copy(
                                        servingSize = it.value.toDouble()
                                    )
                                }
                            }
                            FoodComponentType.CALORIES -> {
                                _foodItem.update {  item ->
                                    item.copy(
                                        calories = it.value.toDouble()
                                    )
                                }
                            }
                            FoodComponentType.CARBS -> {
                                _foodItem.update {  item ->
                                    item.copy(
                                        carbs = it.value.toDouble()
                                    )
                                }
                            }
                            FoodComponentType.PROTEIN -> {
                                _foodItem.update {  item ->
                                    item.copy(
                                        protein = it.value.toDouble()
                                    )
                                }
                            }
                            FoodComponentType.FAT -> {
                                _foodItem.update {  item ->
                                    item.copy(
                                        totalFat = it.value.toDouble()
                                    )
                                }
                            }
                            FoodComponentType.SATURATED_FAT -> {
                                _foodItem.update {  item ->
                                    item.copy(
                                        saturatedFat = it.value.toDouble()
                                    )
                                }
                            }
                            FoodComponentType.FIBER -> {
                                _foodItem.update {  item ->
                                    item.copy(
                                        fiber = it.value.toDouble()
                                    )
                                }
                            }
                            FoodComponentType.SUGAR -> {
                                _foodItem.update {  item ->
                                    item.copy(
                                        sugar = it.value.toDouble()
                                    )
                                }
                            }
                        }
                    }
                    repo.insertFoodItem(_foodItem.value)
                }
            }
        }
    }
}