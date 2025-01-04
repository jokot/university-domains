package com.example.universitydomains.feature.search

import com.example.universitydomains.core.model.SavableUniversity

sealed interface SearchResultUiState {
    data object Idle : SearchResultUiState
    data object Loading : SearchResultUiState
    data class Success(val data: List<SavableUniversity>) : SearchResultUiState
    data object Empty : SearchResultUiState
    data class Error(val message: String) : SearchResultUiState
}
