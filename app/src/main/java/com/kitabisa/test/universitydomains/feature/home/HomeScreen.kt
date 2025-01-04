package com.kitabisa.test.universitydomains.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(500)
            viewModel.fetchUniversities()
            isRefreshing = false
        }
    }

    HomeScreen(
        uiState = uiState,
        isRefreshing = isRefreshing,
        refreshState = refreshState,
        onRefresh = onRefresh,
        onFavoriteClick = viewModel::toggleFavorite,
        onRetryClick = viewModel::fetchUniversities,
        modifier = modifier
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    isRefreshing: Boolean,
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    onRefresh: () -> Unit,
    onFavoriteClick: (SavableUniversity) -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTag.HOME_SCREEN),
    ) {
        Appbar(title = stringResource(TopLevelDestination.HOME.titleId))

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize(),
            state = refreshState,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is HomeUiState.Loading -> {
                    LoadingState()
                }

                is HomeUiState.Error -> {
                    ErrorState(onRetryClick = onRetryClick)
                }

                is HomeUiState.Empty -> {
                    EmptyState(
                        title = stringResource(R.string.feature_home_empty_title),
                        message = stringResource(R.string.feature_home_empty_message)
                    )
                }

                is HomeUiState.Success -> {
                    UniversityFeed(
                        savableUniversities = uiState.data,
                        onFavoriteClick = onFavoriteClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
private fun HomeScreenSuccessPreview() {
    UniversityDomainsTheme {
        HomeScreen(
            uiState = HomeUiState.Success(UniversityTestData.savableUniversities),
            isRefreshing = false,
            onRefresh = {},
            onFavoriteClick = {},
            onRetryClick = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
private fun HomeScreenLoadingPreview() {
    UniversityDomainsTheme {
        HomeScreen(
            uiState = HomeUiState.Loading,
            isRefreshing = false,
            onRefresh = {},
            onFavoriteClick = {},
            onRetryClick = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
private fun HomeScreenErrorPreview() {
    UniversityDomainsTheme {
        HomeScreen(
            uiState = HomeUiState.Error(UniversityTestData.errorMessage),
            isRefreshing = false,
            onRefresh = {},
            onFavoriteClick = {},
            onRetryClick = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
private fun HomeScreenEmptyPreview() {
    UniversityDomainsTheme {
        HomeScreen(
            uiState = HomeUiState.Empty,
            isRefreshing = false,
            onRefresh = {},
            onFavoriteClick = {},
            onRetryClick = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
private fun HomeScreenRefreshingPreview() {
    UniversityDomainsTheme {
        HomeScreen(
            uiState = HomeUiState.Success(UniversityTestData.savableUniversities),
            isRefreshing = true,
            onRefresh = {},
            onFavoriteClick = {},
            onRetryClick = {},
        )
    }
}