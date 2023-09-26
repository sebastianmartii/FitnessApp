package com.example.fitnessapp.history_feature.presentation

import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import com.example.fitnessapp.core.util.calendarYear
import com.example.fitnessapp.history_feature.data.mappers.toActivity
import com.example.fitnessapp.history_feature.data.mappers.toMeal
import com.example.fitnessapp.history_feature.domain.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val calendar: Calendar,
    private val repo: HistoryRepository,
    currentUserDao: CurrentUserDao
) : NavigationDrawerViewModel(currentUserDao) {

    private val _initialPage = MutableStateFlow(0)
    val initialPage = _initialPage.asStateFlow()

    private val _currentMonthDaysNumber = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    private val _calendarPrefix = calculateCalendarPrefix(calendar.get(Calendar.DAY_OF_MONTH), toWeekday(calendar.get(Calendar.DAY_OF_WEEK)))

    private val _state = MutableStateFlow(
        HistoryState(
            year = calendar.get(Calendar.YEAR),
            currentMonth = calendarYear[calendar.get(Calendar.MONTH) + 1] ?: "",
            currentMonthDaysNumber = _currentMonthDaysNumber,
            calendarDaysPrefix = _calendarPrefix,
            calendarDaysSuffix = calculateCalendarSuffix(_currentMonthDaysNumber, _calendarPrefix)
        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getMonthActivities(calendar.get(Calendar.MONTH)).collectLatest { activityHistoryEntities ->
                _state.update {
                    it.copy(
                        currentMonthActivities = activityHistoryEntities.map { entity -> entity.toActivity() }
                    )
                }
            }
        }
        viewModelScope.launch {
            repo.getMonthNutrition(calendar.get(Calendar.MONTH)).collectLatest { nutritionHistoryEntities ->
                _state.update {
                    it.copy(
                        currentMonthNutrition = nutritionHistoryEntities.map { entities -> entities.toMeal() }
                    )
                }
            }
        }
    }

    fun setInitialPage(page: Int) {
        _initialPage.value = page
    }

    private fun calculateCalendarSuffix(monthDaysNumber: Int, calendarOffset: Int): Int {
        return 7 - ((monthDaysNumber + calendarOffset)%7)
    }

    private fun calculateCalendarPrefix(monthDay: Int, weekday: Int): Int {
        var dayOfTheMonth = monthDay
        while(dayOfTheMonth>7) {
            dayOfTheMonth -= 7
        }
        return when {
            dayOfTheMonth == weekday -> {
                0
            }
            dayOfTheMonth < weekday -> {
                weekday - dayOfTheMonth
            }
            else -> {
                7 - (dayOfTheMonth - weekday)
            }
        }
    }

    private fun toWeekday(day: Int): Int {
        return if (day == 1) {
            7
        } else day - 1
    }
}
