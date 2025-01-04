package com.example.universitydomains.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.universitydomains.core.testing.constant.TestTag
import com.example.universitydomains.core.testing.data.UniversityTestData
import com.example.universitydomains.feature.favorite.FavoriteScreen
import com.example.universitydomains.feature.favorite.FavoriteUiState
import org.junit.Rule
import org.junit.Test

class FavoriteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun universitiesAreDisplayed() {
        composeTestRule.setContent {
            FavoriteScreen(
                uiState = FavoriteUiState.Success(UniversityTestData.savableUniversities),
                onFavoriteClick = {},
                onRetryClick = {},
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.FAVORITE_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(UniversityTestData.savableUniversities.first().university.name)
            .assertExists()
    }

    @Test
    fun loadingStateAreDisplayed() {
        composeTestRule.setContent {
            FavoriteScreen(
                uiState = FavoriteUiState.Loading,
                onFavoriteClick = {},
                onRetryClick = {},
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.FAVORITE_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.LOADING_STATE)
            .assertIsDisplayed()
    }

    @Test
    fun errorStateAreDisplayed() {
        composeTestRule.setContent {
            FavoriteScreen(
                uiState = FavoriteUiState.Error(UniversityTestData.errorMessage),
                onFavoriteClick = { },
                onRetryClick = {},
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.FAVORITE_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.ERROR_STATE)
            .assertIsDisplayed()
    }

    @Test
    fun emptyStateAreDisplayed() {
        composeTestRule.setContent {
            FavoriteScreen(
                uiState = FavoriteUiState.Empty,
                onFavoriteClick = {},
                onRetryClick = {},
            )
        }

        composeTestRule
            .onNodeWithTag(TestTag.FAVORITE_SCREEN)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.EMPTY_STATE)
            .assertIsDisplayed()
    }
}
