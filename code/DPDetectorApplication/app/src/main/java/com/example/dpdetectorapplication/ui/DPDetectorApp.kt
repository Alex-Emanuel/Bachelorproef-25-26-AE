package com.example.dpdetectorapplication.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dpdetectorapplication.ui.detail.DetailScreen
import com.example.dpdetectorapplication.ui.home.HomeScreen
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition

@Composable
fun DPDetectorApp() {

    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {

            composable(Screen.Home.route) {
                HomeScreen(
                    onItemClick = { id ->
                        navController.navigate(
                            Screen.Detail.createRoute(id)
                        )
                    }
                )
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getString("id")

                DetailScreen(
                    id = id,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}