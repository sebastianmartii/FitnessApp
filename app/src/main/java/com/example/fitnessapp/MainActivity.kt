package com.example.fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitnessapp.core.database.dao.CurrentUserDao
import com.example.fitnessapp.core.navigation.NavGraphDestinations
import com.example.fitnessapp.core.navigation.StartDestinationScreen
import com.example.fitnessapp.core.navigation.mainNavGraph
import com.example.fitnessapp.core.navigation.signInNavGraph
import com.example.fitnessapp.core.util.DailyOverviewDataManager
import com.example.fitnessapp.core.util.currentDate
import com.example.fitnessapp.ui.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var currentUserDao: CurrentUserDao
    @Inject
    lateinit var dailyOverviewDataManager: DailyOverviewDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val currentDate = currentDate(day, month, year)

        setContent {
            FitnessAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavGraphDestinations.StartDestination.route
                ) {
                    composable(route = NavGraphDestinations.StartDestination.route) {
                        LaunchedEffect(key1 = true) {
                            launch {
                                dailyOverviewDataManager.savedDate.collectLatest { savedDate ->
                                    if (savedDate == "") {
                                        dailyOverviewDataManager.saveCurrentDate(currentDate)
                                    } else if (savedDate != currentDate) {
                                        val savedDay = savedDate.split(",")[0].toInt()
                                        val savedMonth = savedDate.split(",")[1].toInt()
                                        val savedYear = savedDate.split(",")[2].toInt()
                                        dailyOverviewDataManager.saveAndResetDailyOverview(savedDay, savedMonth, savedYear)
                                        dailyOverviewDataManager.saveCurrentDate(currentDate)
                                    }
                                }
                            }
                            launch {
                                delay(1.seconds)
                                currentUserDao.getCurrentUser().collectLatest {
                                    if (it != null) {
                                        navController.navigate(NavGraphDestinations.MainNavGraph.route)
                                    } else {
                                        navController.navigate(NavGraphDestinations.SignInNavGraph.route)
                                    }
                                }
                            }
                        }
                        StartDestinationScreen()
                    }
                    signInNavGraph(
                        navController
                    )
                    mainNavGraph(
                        navController
                    )
                }
            }
        }
    }
}