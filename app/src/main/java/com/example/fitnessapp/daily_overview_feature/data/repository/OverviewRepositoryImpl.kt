package com.example.fitnessapp.daily_overview_feature.data.repository

import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.daily_overview_feature.domain.repository.OverviewRepository
import kotlinx.coroutines.flow.Flow

class OverviewRepositoryImpl(
    private val currentUserDao: CurrentUserDao
) : OverviewRepository {

    override fun getCurrentUserCaloriesRequirements(): Flow<Int> {
        return currentUserDao.getCurrentUserCaloriesRequirements()
    }
}