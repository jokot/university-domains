package com.kitabisa.test.universitydomains.feature.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kitabisa.test.universitydomains.R
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import com.kitabisa.test.universitydomains.core.testing.constant.TestTag
import com.kitabisa.test.universitydomains.core.testing.data.UniversityTestData
import com.kitabisa.test.universitydomains.core.ui.component.Appbar
import com.kitabisa.test.universitydomains.core.ui.component.EmptyState
import com.kitabisa.test.universitydomains.core.ui.component.ErrorState
import com.kitabisa.test.universitydomains.core.ui.component.LoadingState
import com.kitabisa.test.universitydomains.core.ui.component.UniversityFeed
import com.kitabisa.test.universitydomains.core.ui.theme.UniversityDomainsTheme
import com.kitabisa.test.universitydomains.navigation.TopLevelDestination

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FavoriteScreen(
        modifier = modifier,
        uiState = uiState,
        onFavoriteClick = viewModel::toggleFavorite,
        onRetryClick = viewModel::getUniversities
    )
}

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    uiState: FavoriteUiState,
    onFavoriteClick: (SavableUniversity) -> Unit,
    onRetryClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTag.FAVORITE_SCREEN)
    ) {
        Appbar(title = stringResource(TopLevelDestination.FAVORITE.titleId))
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is FavoriteUiState.Loading -> {
                    LoadingState()
                }

                is FavoriteUiState.Success -> {
                    UniversityFeed(
                        savableUniversities = uiState.data,
                        onFavoriteClick = onFavoriteClick
                    )
                }

                is FavoriteUiState.Empty -> {
                    EmptyState(
                        title = stringResource(R.string.feature_favorite_empty_title),
                        message = stringResource(R.string.feature_favorite_empty_message)
                    )
                }

                is FavoriteUiState.Error -> {
                    ErrorState(onRetryClick = onRetryClick)
                }
            }
        }
    }
}

@Preview()
@Composable
private fun FavoriteScreenSuccessPreview() {
    UniversityDomainsTheme {
        FavoriteScreen(
            uiState = FavoriteUiState.Success(UniversityTestData.favoriteSavableUniversities),
            onFavoriteClick = {},
            onRetryClick = {}
        )
    }
}

@Preview()
@Composable
private fun FavoriteScreenLoadingPreview() {
    UniversityDomainsTheme {
        FavoriteScreen(
            uiState = FavoriteUiState.Loading,
            onFavoriteClick = {},
            onRetryClick = {}
        )
    }
}

@Preview()
@Composable
private fun FavoriteScreenErrorPreview() {
    UniversityDomainsTheme {
        FavoriteScreen(
            uiState = FavoriteUiState.Error(UniversityTestData.errorMessage),
            onFavoriteClick = {},
            onRetryClick = {}
        )
    }
}

@Preview()
@Composable
private fun FavoriteScreenEmptyPreview() {
    UniversityDomainsTheme {
        FavoriteScreen(
            uiState = FavoriteUiState.Empty,
            onFavoriteClick = {},
            onRetryClick = {}
        )
    }
}
