package com.example.fitnessapp.core.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyActivitiesDao
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyNutritionDao
import com.example.fitnessapp.history_feature.data.local.dao.ActivityHistoryDao
import com.example.fitnessapp.history_feature.data.local.dao.NutritionHistoryDao
import com.example.fitnessapp.history_feature.data.mappers.toActivityHistoryEntity
import com.example.fitnessapp.history_feature.data.mappers.toNutritionHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class DailyOverviewDataManager(
    private val dataStore: DataStore<Preferences>,
    private val dailyNutritionDao: DailyNutritionDao,
    private val dailyActivitiesDao: DailyActivitiesDao,
    private val nutritionHistoryDao: NutritionHistoryDao,
    private val activityHistoryDao: ActivityHistoryDao
) {
    private companion object {
        val SAVED_DATE = stringPreferencesKey("saved_date")
    }

    val savedDate: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[SAVED_DATE] ?: ""
        }

    suspend fun saveAndResetDailyOverview(day: Int,month: Int, year: Int) {
        saveAndResetDailyNutrition(day, month, year)
        saveAndResetDailyActivity(day, month, year)
    }

    suspend fun saveCurrentDate(currentDate: String) {
        dataStore.edit { preferences ->
            preferences[SAVED_DATE] = currentDate
        }
    }

    private suspend fun saveAndResetDailyNutrition(day: Int, month: Int, year: Int) {
        val nutrition = dailyNutritionDao.getDailyNutrition().first()
        nutritionHistoryDao.addToHistory(nutrition.map { it.toNutritionHistoryEntity(day, month, year) })
        val savedNutritionPerMonth = nutritionHistoryDao.getMonthNutrition(month).first()
        val isNutritionInHistory = savedNutritionPerMonth.any { it.day == day }
        if (isNutritionInHistory) {
            dailyNutritionDao.resetDailyNutrition()
        }
    }

    private suspend fun saveAndResetDailyActivity(day: Int, month: Int, year: Int) {
        val activities = dailyActivitiesDao.getDailyActivities().first()
        activityHistoryDao.addToHistory(activities.map { it.toActivityHistoryEntity(day, month, year) })
        val savedActivityPerMonth = activityHistoryDao.getMonthActivities(month).first()
        val areActivitiesInHistory = savedActivityPerMonth.any { it.day == day }
        if (areActivitiesInHistory) {
            dailyActivitiesDao.resetDailyActivities()
        }
    }
}