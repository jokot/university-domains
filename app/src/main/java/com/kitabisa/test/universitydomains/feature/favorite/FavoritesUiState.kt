package com.kitabisa.test.universitydomains.feature.favorite

import com.kitabisa.test.universitydomains.core.model.SavableUniversity

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data class Success(val data: List<SavableUniversity>) : FavoritesUiState
    data object Empty : FavoritesUiState
    data class Error(val message: String) : FavoritesUiState
}
