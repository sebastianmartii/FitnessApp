package com.example.fitnessapp.history_feature.presentation

import com.example.fitnessapp.history_feature.domain.model.Activity
import com.example.fitnessapp.history_feature.domain.model.Meal

data class HistoryState(
    val isDatePickerDialogVisible: Boolean = false,
    val selectedTimeMillis: Long = 0,
    val selectedDateString: String = "",
    val selectedDay: Int = 0,
    val activities: List<Activity> = emptyList(),
    val nutrition: List<Meal> = emptyList(),
)
