package com.example.universitydomains.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.universitydomains.core.testing.constant.TestTag
import com.example.universitydomains.core.testing.data.UniversityTestData
import com.example.universitydomains.core.ui.theme.UniversityDomainsTheme

@Composable
fun RecentState(
    recentSearches: List<String>,
    onClearRecentSearches: () -> Unit,
    onRecentSearchClick: (String) -> Unit,
    onRemoveRecentSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTag.RECENT_STATE)
    ) {
        RecentSearchLabel(onClearClick = onClearRecentSearches)
        recentSearches.forEach { recent ->
            RecentSearchItem(
                recent = recent,
                onRecentSearchClick = onRecentSearchClick,
                onRemoveRecentSearchClick = onRemoveRecentSearchClick
            )
        }
    }
}

@Preview
@Composable
private fun RecentStatePreview() {
    UniversityDomainsTheme {
        RecentState(
            recentSearches = UniversityTestData.recentSearch,
            onClearRecentSearches = {},
            onRecentSearchClick = {},
            onRemoveRecentSearchClick = {}
        )
    }
}