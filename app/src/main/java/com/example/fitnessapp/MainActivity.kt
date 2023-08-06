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
import com.example.fitnessapp.ui.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var currentUserDao: CurrentUserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FitnessAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavGraphDestinations.StartDestination.route
                ) {
                    composable(route = NavGraphDestinations.StartDestination.route) {
                        LaunchedEffect(key1 = true) {
                            currentUserDao.getCurrentUser().collectLatest {
                                if (it != null) {
                                    navController.navigate(NavGraphDestinations.MainNavGraph.route)
                                } else {
                                    navController.navigate(NavGraphDestinations.SignInNavGraph.route)
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