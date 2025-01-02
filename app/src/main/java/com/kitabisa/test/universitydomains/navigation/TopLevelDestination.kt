package com.kitabisa.test.universitydomains.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.kitabisa.test.universitydomains.R
import com.kitabisa.test.universitydomains.core.ui.icon.UniversityIcons
import com.kitabisa.test.universitydomains.feature.favorite.navigation.FavoriteRoute
import com.kitabisa.test.universitydomains.feature.home.navigation.BaseRoute
import com.kitabisa.test.universitydomains.feature.home.navigation.HomeRoute
import com.kitabisa.test.universitydomains.feature.search.navigation.SearchRoute
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