package com.example.fitnessapp.history_feature.presentation

import com.example.fitnessapp.core.util.calendarYear
import com.example.fitnessapp.history_feature.domain.model.Activity
import com.example.fitnessapp.history_feature.domain.model.Meal

data class HistoryState(
    val year: Int = 0,
    val months: Map<Int, String> = calendarYear,
    val currentMonth: String = "",
    val calendarDaysSuffix: Int = 0,
    val currentMonthDaysNumber: Int = 0,
    val calendarDaysPrefix: Int = 0,
    val currentMonthActivities: List<Activity> = emptyList(),
    val currentMonthNutrition: List<Meal> = emptyList(),
)