package com.kitabisa.test.universitydomains.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.kitabisa.test.universitydomains.core.testing.constant.TestTag
import com.kitabisa.test.universitydomains.core.testing.data.UniversityTestData
import com.kitabisa.test.universitydomains.feature.home.HomeScreen
import com.kitabisa.test.universitydomains.feature.home.HomeUiState
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun universitiesAreDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = HomeUiState.Success(UniversityTestData.savableUniversities),
                isRefreshing = false,
                onRefresh = {},
                onFavoriteClick = {},
                onRetryClick = {},
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.HOME_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(UniversityTestData.savableUniversities[0].university.name)
            .assertExists()
    }

    @Test
    fun loadingStateAreDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = HomeUiState.Loading,
                isRefreshing = false,
                onRefresh = {},
                onFavoriteClick = {},
                onRetryClick = {},
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.HOME_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.LOADING_STATE)
            .assertIsDisplayed()
    }

    @Test
    fun errorStateAreDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = HomeUiState.Error(UniversityTestData.errorMessage),
                isRefreshing = false,
                onRefresh = {},
                onFavoriteClick = {},
                onRetryClick = {},
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.HOME_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.ERROR_STATE)
            .assertIsDisplayed()
    }

    @Test
    fun emptyStateAreDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = HomeUiState.Empty,
                isRefreshing = false,
                onRefresh = {},
                onFavoriteClick = {},
                onRetryClick = {},
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.HOME_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.EMPTY_STATE)
            .assertIsDisplayed()
    }
}
