package com.example.universitydomains.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.universitydomains.R
import com.example.universitydomains.core.ui.icon.UniversityIcons
import com.example.universitydomains.feature.favorite.navigation.FavoriteRoute
import com.example.universitydomains.feature.home.navigation.BaseRoute
import com.example.universitydomains.feature.home.navigation.HomeRoute
import com.example.universitydomains.feature.search.navigation.SearchRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val titleId: Int,
    @StringRes val labelId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route
) {
    HOME(
        selectedIcon = UniversityIcons.Home,
        unselectedIcon = UniversityIcons.HomeBorder,
        titleId = R.string.feature_home_title,
        labelId = R.string.feature_home_label,
        route = HomeRoute::class,
        baseRoute = BaseRoute::class
    ),
    SEARCH(
        selectedIcon = UniversityIcons.Search,
        unselectedIcon = UniversityIcons.Search,
        titleId = R.string.feature_search_title,
        labelId = R.string.feature_search_label,
        route = SearchRoute::class
    ),
    FAVORITE(
        selectedIcon = UniversityIcons.Favorite,
        unselectedIcon = UniversityIcons.FavoriteBorder,
        titleId = R.string.feature_favorite_title,
        labelId = R.string.feature_favorite_label,
        route = FavoriteRoute::class
    )
}