package com.example.fitnessapp.history_feature.presentation

sealed interface HistoryEvent {
    object OnDatePickerDialogShow : HistoryEvent
    object OnDatePickerDialogDismiss : HistoryEvent
    data class OnDatePickerDialogDateConfirm(val selectedDateMillis: Long?) : HistoryEvent
}