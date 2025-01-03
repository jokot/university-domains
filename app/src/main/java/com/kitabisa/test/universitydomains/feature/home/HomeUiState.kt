package com.kitabisa.test.universitydomains.feature.home

import com.kitabisa.test.universitydomains.core.model.SavableUniversity

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Empty : HomeUiState
    data class Error(val message: String) : HomeUiState
    data class Success(val data: List<SavableUniversity>) : HomeUiState
}