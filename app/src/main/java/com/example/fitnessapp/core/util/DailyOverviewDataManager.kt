package com.example.fitnessapp.core.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
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
        val DAY_OF_MONTH = intPreferencesKey("day_of_month")
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

    private val dayOfMonth: Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[DAY_OF_MONTH] ?: 0
        }

    suspend fun resetDailyOverview(month: Int, year: Int) {
        addDailyActivityToHistory(month, year)
        addDailyNutritionToHistory(month, year)
        dailyActivitiesDao.resetDailyActivities()
        dailyNutritionDao.resetDailyNutrition()
    }

    suspend fun saveCurrentDate(currentDate: String) {
        dataStore.edit { preferences ->
            preferences[SAVED_DATE] = currentDate
        }
    }

    suspend fun saveDayOfMonth(day: Int) {
        dataStore.edit { preferences ->
            preferences[DAY_OF_MONTH] = day
        }
    }

    private suspend fun addDailyNutritionToHistory(month: Int, year: Int) {
        val nutrition = dailyNutritionDao.getDailyNutrition().first()
        nutritionHistoryDao.addToHistory(nutrition.map { it.toNutritionHistoryEntity(dayOfMonth.first(), month, year) })
    }

    private suspend fun addDailyActivityToHistory(month: Int, year: Int) {
        val activities = dailyActivitiesDao.getDailyActivities().first()
        activityHistoryDao.addToHistory(activities.map { it.toActivityHistoryEntity(dayOfMonth.first(), month, year) })
    }
}