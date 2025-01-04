package com.kitabisa.test.universitydomains.feature.search

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ajaib.assessment.github.core.ui.RecentState
import com.kitabisa.test.universitydomains.R
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import com.kitabisa.test.universitydomains.core.testing.constant.TestTag
import com.kitabisa.test.universitydomains.core.testing.data.UniversityTestData
import com.kitabisa.test.universitydomains.core.ui.component.EmptyState
import com.kitabisa.test.universitydomains.core.ui.component.ErrorState
import com.kitabisa.test.universitydomains.core.ui.component.IdleState
import com.kitabisa.test.universitydomains.core.ui.component.LoadingState
import com.kitabisa.test.universitydomains.core.ui.component.SearchAppBar
import com.kitabisa.test.universitydomains.core.ui.component.UniversityFeed
import com.kitabisa.test.universitydomains.core.ui.theme.UniversityDomainsTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResultUiState by viewModel.searchResultUiState.collectAsState()
    val recentSearchesUiState by viewModel.recentSearchesUiState.collectAsState()
    val query by viewModel.query.collectAsState()

    SearchScreen(
        modifier = modifier,
        searchResultUiState = searchResultUiState,
        recentSearchesUiState = recentSearchesUiState,
        query = query,
        onQueryChange = viewModel::updateQuery,
        onSearchTriggered = viewModel::onSearchTriggered,
        onRecentSearchClick = viewModel::onRecentSearchClick,
        onRemoveRecentSearchClick = viewModel::removeRecentSearch,
        onClearRecentSearches = viewModel::clearRecentSearches,
        onFavoriteClick = viewModel::toggleFavorite,
        onRetryClick = viewModel::onSearchTriggered
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchResultUiState: SearchResultUiState,
    recentSearchesUiState: RecentSearchesUiState,
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchTriggered: () -> Unit,
    onFavoriteClick: (SavableUniversity) -> Unit,
    onRecentSearchClick: (String) -> Unit,
    onRemoveRecentSearchClick: (String) -> Unit,
    onClearRecentSearches: () -> Unit,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTag.SEARCH_SCREEN)
    ) {
        SearchAppBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearchTriggered = onSearchTriggered,
            onRefreshClick = onRetryClick
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (searchResultUiState) {
                is SearchResultUiState.Idle -> {
                    when (recentSearchesUiState) {
                        is RecentSearchesUiState.Empty -> {
                            IdleState()
                        }

                        is RecentSearchesUiState.Recent -> {
                            RecentState(
                                recentSearches = recentSearchesUiState.data,
                                onClearRecentSearches = onClearRecentSearches,
                                onRecentSearchClick = onRecentSearchClick,
                                onRemoveRecentSearchClick = onRemoveRecentSearchClick
                            )
                        }
                    }
                }

                is SearchResultUiState.Loading -> {
                    LoadingState()
                }

                is SearchResultUiState.Success -> {
                    UniversityFeed(
                        savableUniversities = searchResultUiState.data,
                        onFavoriteClick = onFavoriteClick
                    )
                }

                is SearchResultUiState.Empty -> {
                    EmptyState(
                        title = stringResource(R.string.feature_search_empty_title),
                        message = stringResource(R.string.feature_search_empty_message)
                    )
                }

                is SearchResultUiState.Error -> {
                    ErrorState(
                        onRetryClick = onRetryClick
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenSuccessPreview() {
    UniversityDomainsTheme {
        SearchScreen(
            searchResultUiState = SearchResultUiState.Success(UniversityTestData.universitiesFilterByQuery),
            recentSearchesUiState = RecentSearchesUiState.Empty,
            query = TextFieldValue(UniversityTestData.query),
            onQueryChange = {},
            onSearchTriggered = {},
            onRecentSearchClick = {},
            onRemoveRecentSearchClick = {},
            onClearRecentSearches = {},
            onFavoriteClick = {},
            onRetryClick = {}
        )
    }
}

@Preview
@Composable
fun SearchScreenLoadingPreview() {
    UniversityDomainsTheme {
        SearchScreen(
            searchResultUiState = SearchResultUiState.Loading,
            recentSearchesUiState = RecentSearchesUiState.Empty,
            query = TextFieldValue(UniversityTestData.query),
            onQueryChange = {},
            onSearchTriggered = {},
            onRecentSearchClick = {},
            onRemoveRecentSearchClick = {},
            onClearRecentSearches = {},
            onFavoriteClick = {},
            onRetryClick = {}
        )
    }
}

@Preview
@Composable
fun SearchScreenErrorPreview() {
    UniversityDomainsTheme {
        SearchScreen(
            searchResultUiState = SearchResultUiState.Error(UniversityTestData.errorMessage),
            recentSearchesUiState = RecentSearchesUiState.Empty,
            query = TextFieldValue(UniversityTestData.query),
            onQueryChange = {},
            onSearchTriggered = {},
            onRecentSearchClick = {},
            onRemoveRecentSearchClick = {},
            onClearRecentSearches = {},
            onFavoriteClick = {},
            onRetryClick = {}
        )
    }
}

@Preview
@Composable
fun SearchScreenEmptyPreview() {
    UniversityDomainsTheme {
        SearchScreen(
            searchResultUiState = SearchResultUiState.Empty,
            recentSearchesUiState = RecentSearchesUiState.Empty,
            query = TextFieldValue(UniversityTestData.query),
            onQueryChange = {},
            onSearchTriggered = {},
            onRecentSearchClick = {},
            onRemoveRecentSearchClick = {},
            onClearRecentSearches = {},
            onFavoriteClick = {},
            onRetryClick = {}
        )
    }
}

@Preview
@Composable
fun SearchScreenIdlePreview() {
    UniversityDomainsTheme {
        SearchScreen(
            searchResultUiState = SearchResultUiState.Idle,
            recentSearchesUiState = RecentSearchesUiState.Empty,
            query = TextFieldValue(UniversityTestData.query),
            onQueryChange = {},
            onSearchTriggered = {},
            onRecentSearchClick = {},
            onRemoveRecentSearchClick = {},
            onClearRecentSearches = {},
            onFavoriteClick = {},
            onRetryClick = {}
        )
    }
}

@Preview
@Composable
fun SearchScreenRecentPreview() {
    UniversityDomainsTheme {
        SearchScreen(
            searchResultUiState = SearchResultUiState.Idle,
            recentSearchesUiState = RecentSearchesUiState.Recent(UniversityTestData.recentSearch),
            query = TextFieldValue(UniversityTestData.query),
            onQueryChange = {},
            onSearchTriggered = {},
            onRecentSearchClick = {},
            onRemoveRecentSearchClick = {},
            onClearRecentSearches = {},
            onFavoriteClick = {},
            onRetryClick = {}
        )
    }
}