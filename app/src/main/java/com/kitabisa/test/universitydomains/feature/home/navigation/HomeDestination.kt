package com.kitabisa.test.universitydomains.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitabisa.test.universitydomains.feature.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object BaseRoute

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(HomeRoute, navOptions)

fun NavGraphBuilder.homeScreen(
    onUrlClick: (String) -> Unit
) {
    navigation<BaseRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreen(onUrlClick)
        }
    }
}