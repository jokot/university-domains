package com.kitabisa.test.universitydomains.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.text.input.TextFieldValue
import com.kitabisa.test.universitydomains.core.testing.constant.TestTag
import com.kitabisa.test.universitydomains.core.testing.data.UniversityTestData
import com.kitabisa.test.universitydomains.feature.search.RecentSearchesUiState
import com.kitabisa.test.universitydomains.feature.search.SearchResultUiState
import com.kitabisa.test.universitydomains.feature.search.SearchScreen
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun universitiesAreDisplayed() {
        composeTestRule.setContent {
            SearchScreen(
                searchResultUiState = SearchResultUiState.Success(UniversityTestData.savableUniversities),
                recentSearchesUiState = RecentSearchesUiState.Empty,
                query = TextFieldValue(""),
                onQueryChange = {},
                onSearchTriggered = {},
                onRecentSearchClick = {},
                onRemoveRecentSearchClick = {},
                onClearRecentSearches = {},
                onFavoriteClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.SEARCH_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(UniversityTestData.savableUniversities.first().university.name)
            .assertExists()
    }

    @Test
    fun loadingStateAreDisplayed() {
        composeTestRule.setContent {
            SearchScreen(
                searchResultUiState = SearchResultUiState.Loading,
                recentSearchesUiState = RecentSearchesUiState.Empty,
                query = TextFieldValue(""),
                onQueryChange = {},
                onSearchTriggered = {},
                onRecentSearchClick = {},
                onRemoveRecentSearchClick = {},
                onClearRecentSearches = {},
                onFavoriteClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.SEARCH_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.LOADING_STATE)
            .assertIsDisplayed()
    }

    @Test
    fun errorStateAreDisplayed() {
        composeTestRule.setContent {
            SearchScreen(
                searchResultUiState = SearchResultUiState.Error("test"),
                recentSearchesUiState = RecentSearchesUiState.Empty,
                query = TextFieldValue(""),
                onQueryChange = {},
                onSearchTriggered = {},
                onRecentSearchClick = {},
                onRemoveRecentSearchClick = {},
                onClearRecentSearches = {},
                onFavoriteClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.SEARCH_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.ERROR_STATE)
            .assertIsDisplayed()
    }

    @Test
    fun emptyStateAreDisplayed() {
        composeTestRule.setContent {
            SearchScreen(
                searchResultUiState = SearchResultUiState.Empty,
                recentSearchesUiState = RecentSearchesUiState.Empty,
                query = TextFieldValue(""),
                onQueryChange = {},
                onSearchTriggered = {},
                onRecentSearchClick = {},
                onRemoveRecentSearchClick = {},
                onClearRecentSearches = {},
                onFavoriteClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.SEARCH_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.EMPTY_STATE)
            .assertIsDisplayed()
    }

    @Test
    fun idleRecentStateAreDisplayed() {
        composeTestRule.setContent {
            SearchScreen(
                searchResultUiState = SearchResultUiState.Idle,
                recentSearchesUiState = RecentSearchesUiState.Recent(UniversityTestData.recentSearch),
                query = TextFieldValue(""),
                onQueryChange = {},
                onSearchTriggered = {},
                onRecentSearchClick = {},
                onRemoveRecentSearchClick = {},
                onClearRecentSearches = {},
                onFavoriteClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.SEARCH_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.RECENT_STATE)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(UniversityTestData.recentSearch.first())
            .assertExists()
    }

    @Test
    fun idleNoRecentStateAreDisplayed() {
        composeTestRule.setContent {
            SearchScreen(
                searchResultUiState = SearchResultUiState.Idle,
                recentSearchesUiState = RecentSearchesUiState.Empty,
                query = TextFieldValue(""),
                onQueryChange = {},
                onSearchTriggered = {},
                onRecentSearchClick = {},
                onRemoveRecentSearchClick = {},
                onClearRecentSearches = {},
                onFavoriteClick = {},
                onRetryClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.SEARCH_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.IDLE_STATE)
            .assertIsDisplayed()
    }
}
