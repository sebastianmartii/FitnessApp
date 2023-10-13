package com.example.fitnessapp.history_feature.presentation

import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.navigation_drawer.NavigationDrawerViewModel
import com.example.fitnessapp.history_feature.data.mappers.toActivity
import com.example.fitnessapp.history_feature.data.mappers.toMeal
import com.example.fitnessapp.history_feature.domain.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val calendar: Calendar,
    private val repo: HistoryRepository,
    currentUserDao: CurrentUserDao
) : NavigationDrawerViewModel(currentUserDao) {

    private val _selectedTimeMillis = MutableStateFlow(System.currentTimeMillis())
    private val _dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private val _state = MutableStateFlow(HistoryState())
    val state = _state.combine(_selectedTimeMillis) { state, selectedTimeMillis ->
        state.copy(
            selectedTimeMillis = selectedTimeMillis,
            selectedDateString = _dateFormat.format(Date(selectedTimeMillis)),
            selectedDay = getSelectedDayFromTimeMillis(selectedTimeMillis)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HistoryState())

    init {
        viewModelScope.launch {
            repo.getMonthActivities(calendar.get(Calendar.MONTH)).collectLatest { activityHistoryEntities ->
                _state.update {
                    it.copy(
                        activities = activityHistoryEntities.map { entity -> entity.toActivity() }
                    )
                }
            }
        }
        viewModelScope.launch {
            repo.getMonthNutrition(calendar.get(Calendar.MONTH)).collectLatest { nutritionHistoryEntities ->
                _state.update {
                    it.copy(
                        nutrition = nutritionHistoryEntities.map { entities -> entities.toMeal() }
                    )
                }
            }
        }
    }

    fun onEvent(event: HistoryEvent) {
        when(event) {
            is HistoryEvent.OnDatePickerDialogDateConfirm -> {
                if (event.selectedDateMillis != null) {
                    _selectedTimeMillis.value = event.selectedDateMillis
                }
                _state.update {
                    it.copy(
                        isDatePickerDialogVisible = false
                    )
                }
            }
            is HistoryEvent.OnDatePickerDialogDismiss -> {
                _state.update {
                    it.copy(
                        isDatePickerDialogVisible = false
                    )
                }
            }
            is HistoryEvent.OnDatePickerDialogShow -> {
                _state.update {
                    it.copy(
                        isDatePickerDialogVisible = true
                    )
                }
            }
        }
    }

    private fun getSelectedDayFromTimeMillis(selectedTimeMillis: Long): Int {
        calendar.timeInMillis = selectedTimeMillis
        return calendar.get(Calendar.DAY_OF_MONTH)
    }
}