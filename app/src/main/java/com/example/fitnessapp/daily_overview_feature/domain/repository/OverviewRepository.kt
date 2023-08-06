package com.example.fitnessapp.daily_overview_feature.domain.repository

import kotlinx.coroutines.flow.Flow

interface OverviewRepository {

    fun getCurrentUserCaloriesRequirements(): Flow<Int>
}