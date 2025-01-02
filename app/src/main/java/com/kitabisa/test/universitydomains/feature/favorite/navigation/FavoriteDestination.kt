package com.kitabisa.test.universitydomains.feature.favorite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kitabisa.test.universitydomains.feature.favorite.FavoriteScreen
import kotlinx.serialization.Serializable

@Serializable
data object FavoriteRoute

fun NavController.navigateToFavorite(navOptions: NavOptions? = null) =
    navigate(FavoriteRoute, navOptions)

fun NavGraphBuilder.favoriteScreen(
    onUrlClick: (String) -> Unit
) {
    composable<FavoriteRoute> {
        FavoriteScreen(onUrlClick)
    }
}