package com.example.fitnessapp.core.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyActivitiesDao
import com.example.fitnessapp.daily_overview_feature.data.local.dao.DailyNutritionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DailyOverviewDataManager(
    private val dataStore: DataStore<Preferences>,
    private val dailyNutritionDao: DailyNutritionDao,
    private val dailyActivitiesDao: DailyActivitiesDao
) {
    private companion object {
        val SAVED_DATE = stringPreferencesKey("saved_date")
    }

    val savedDate: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            }
            else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[SAVED_DATE] ?: ""
        }

    suspend fun resetDailyOverview() {
        dailyActivitiesDao.resetDailyActivities()
        dailyNutritionDao.resetDailyNutrition()
    }

    suspend fun saveCurrentDate(currentDate: String) {
        dataStore.edit { preferences ->
            preferences[SAVED_DATE] = currentDate
        }
    }
}