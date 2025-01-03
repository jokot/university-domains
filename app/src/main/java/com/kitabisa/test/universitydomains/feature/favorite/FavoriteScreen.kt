package com.kitabisa.test.universitydomains.feature.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kitabisa.test.universitydomains.R
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import com.kitabisa.test.universitydomains.core.ui.component.Appbar
import com.kitabisa.test.universitydomains.core.ui.component.EmptyState
import com.kitabisa.test.universitydomains.core.ui.component.ErrorState
import com.kitabisa.test.universitydomains.core.ui.component.LoadingState
import com.kitabisa.test.universitydomains.core.ui.component.UniversityFeed
import com.kitabisa.test.universitydomains.navigation.TopLevelDestination

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FavoriteScreen(
        modifier = modifier,
        uiState = uiState,
        onFavoriteClick = viewModel::toggleFavorite,
        onRetryClick = viewModel::getUsers
    )
}

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    uiState: FavoritesUiState,
    onFavoriteClick: (SavableUniversity) -> Unit,
    onRetryClick: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Appbar(title = stringResource(TopLevelDestination.FAVORITE.titleId))
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is FavoritesUiState.Loading -> {
                    LoadingState()
                }

                is FavoritesUiState.Success -> {
                    UniversityFeed(
                        savableUniversities = uiState.data,
                        onFavoriteClick = onFavoriteClick
                    )
                }

                is FavoritesUiState.Empty -> {
                    EmptyState(
                        title = stringResource(R.string.feature_favorite_empty_title),
                        message = stringResource(R.string.feature_favorite_empty_message)
                    )
                }

                is FavoritesUiState.Error -> {
                    ErrorState(onRetryClick = onRetryClick)
                }
            }
        }
    }
}
