package com.kitabisa.test.universitydomains.ui

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
import com.kitabisa.test.universitydomains.feature.favorite.navigation.navigateToFavorite
import com.kitabisa.test.universitydomains.feature.home.navigation.navigateToHome
import com.kitabisa.test.universitydomains.feature.search.navigation.navigateToSearch
import com.kitabisa.test.universitydomains.navigation.TopLevelDestination
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

    val currentTopLevelDestination: TopLevelDestination
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            } ?: TopLevelDestination.HOME
        }

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