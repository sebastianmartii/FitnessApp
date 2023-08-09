package com.example.fitnessapp.nutrition_calculator_feature.presentation.custom_meal_plan_creator

import androidx.lifecycle.ViewModel
import com.example.fitnessapp.nutrition_calculator_feature.domain.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CustomMealPlanCreatorViewModel @Inject constructor(

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
                TODO("delete meal plan from database after showing a dialog")
            }
            CustomMealPlanCreatorEvent.OnSaveMealPlan -> {
                TODO("add meal plan to database")
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