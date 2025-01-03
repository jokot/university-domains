package com.ajaib.assessment.github.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kitabisa.test.universitydomains.core.ui.component.RecentSearchItem
import com.kitabisa.test.universitydomains.core.ui.component.RecentSearchLabel

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