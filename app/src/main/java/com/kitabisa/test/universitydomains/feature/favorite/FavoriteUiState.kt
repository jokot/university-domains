package com.kitabisa.test.universitydomains.feature.favorite

import com.kitabisa.test.universitydomains.core.model.SavableUniversity

sealed interface FavoriteUiState {
    data object Loading : FavoriteUiState
    data class Success(val data: List<SavableUniversity>) : FavoriteUiState
    data object Empty : FavoriteUiState
    data class Error(val message: String) : FavoriteUiState
}
