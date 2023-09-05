package com.example.fitnessapp.nutrition_calculator_feature.presentation

import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionViewModel @Inject constructor() : NavigationDrawerViewModel() {

    private val _channel = Channel<Int>()
    val pagerFlow = _channel.receiveAsFlow()

    fun onPageChange(page: Int) {
        viewModelScope.launch {
            _channel.send(page)
        }
    }
}