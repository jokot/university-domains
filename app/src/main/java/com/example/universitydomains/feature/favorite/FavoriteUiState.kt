package com.example.universitydomains.feature.favorite

import com.example.universitydomains.core.model.SavableUniversity

sealed interface FavoriteUiState {
    data object Loading : FavoriteUiState
    data class Success(val data: List<SavableUniversity>) : FavoriteUiState
    data object Empty : FavoriteUiState
    data class Error(val message: String) : FavoriteUiState
}
