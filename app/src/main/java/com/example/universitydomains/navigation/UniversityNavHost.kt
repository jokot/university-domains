package com.example.universitydomains.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.universitydomains.feature.favorite.navigation.favoriteScreen
import com.example.universitydomains.feature.home.navigation.BaseRoute
import com.example.universitydomains.feature.home.navigation.homeScreen
import com.example.universitydomains.feature.search.navigation.searchScreen

@Composable
fun UniversityNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BaseRoute,
        modifier = modifier
    ) {
        homeScreen()
        searchScreen()
        favoriteScreen()
    }
}