package com.example.universitydomains.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.universitydomains.feature.favorite.navigation.navigateToFavorite
import com.example.universitydomains.feature.home.navigation.navigateToHome
import com.example.universitydomains.feature.search.navigation.navigateToSearch
import com.example.universitydomains.navigation.TopLevelDestination
import kotlin.reflect.KClass

@Composable
fun rememberUniversityAppState(
    navController: NavHostController = rememberNavController()
): UniversityAppState {
    return remember(navController) {
        UniversityAppState(navController)
    }
}

@Stable
class UniversityAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            restoreState = true
            launchSingleTop = true
        }

        when (destination) {
            TopLevelDestination.HOME -> navController.navigateToHome(navOptions)
            TopLevelDestination.SEARCH -> navController.navigateToSearch(navOptions)
            TopLevelDestination.FAVORITE -> navController.navigateToFavorite(navOptions)
        }
    }

    fun isRouteInHierarchies(
        destination: NavDestination?,
        route: KClass<*>
    ): Boolean =
        destination?.hierarchy?.any {
            it.hasRoute(route)
        } ?: false
}