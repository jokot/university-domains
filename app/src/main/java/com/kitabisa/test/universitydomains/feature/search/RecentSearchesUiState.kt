package com.kitabisa.test.universitydomains.feature.search

sealed interface RecentSearchesUiState {
    data class Recent(val data: List<String>) : RecentSearchesUiState
    data object Empty : RecentSearchesUiState
}
