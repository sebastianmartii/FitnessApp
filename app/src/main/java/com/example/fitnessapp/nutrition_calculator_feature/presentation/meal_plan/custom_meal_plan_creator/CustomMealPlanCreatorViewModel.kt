package com.example.fitnessapp.nutrition_calculator_feature.presentation.meal_plan.custom_meal_plan_creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.nutrition_calculator_feature.data.local.entity.MealPlanEntity
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Meal
import com.example.fitnessapp.nutrition_calculator_feature.domain.repository.CustomMealPlanCreatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomMealPlanCreatorViewModel @Inject constructor(
    private val repo: CustomMealPlanCreatorRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CustomMealPlanCreatorState())
    val state = _state.asStateFlow()

    fun onEvent(event: CustomMealPlanCreatorEvent) {
        when(event) {
            CustomMealPlanCreatorEvent.OnAddMeal -> {
                _state.value.mealList.add(Meal())
            }
            CustomMealPlanCreatorEvent.OnChangeMealPlanName -> {
                _state.update {
                    it.copy(
                        isMealPlanNameEditable = true
                    )
                }
            }
            CustomMealPlanCreatorEvent.OnDeleteMealPlan -> {
                viewModelScope.launch {
                    repo.deleteMealPlan()
                }
            }
            CustomMealPlanCreatorEvent.OnSaveMealPlan -> {
                viewModelScope.launch {
                    repo.changeMealPlan(MealPlanEntity(
                        id = 1,
                        planName = _state.value.planName,
                        meals = _state.value.mealList
                    ))
                }
            }
            is CustomMealPlanCreatorEvent.OnMealPlanNameChange -> {
                _state.update {
                    it.copy(
                        planName = event.newName
                    )
                }
            }
            CustomMealPlanCreatorEvent.OnChangeMealName -> {
                _state.update {
                    it.copy(
                        isMealNameEditable = true
                    )
                }
            }
            is CustomMealPlanCreatorEvent.OnMealNameChange -> {
                _state.value.mealList[event.mealIndex].name = event.newName
            }
            is CustomMealPlanCreatorEvent.OnDeleteMeal -> {
                _state.value.mealList.removeAt(event.mealIndex)
            }
        }
    }
}