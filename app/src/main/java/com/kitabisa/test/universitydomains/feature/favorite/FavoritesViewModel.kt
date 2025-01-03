package com.kitabisa.test.universitydomains.feature.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitabisa.test.universitydomains.core.data.repository.FavoriteRepository
import com.kitabisa.test.universitydomains.core.data.state.DataState
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteUniversities()
                .catch { exception ->
                    _uiState.update { FavoritesUiState.Error(exception.localizedMessage.orEmpty()) }
                }
                .collect { dataState ->
                    _uiState.update {
                        when (dataState) {
                            is DataState.Loading -> FavoritesUiState.Loading
                            is DataState.Success -> if (dataState.data.isEmpty()) {
                                FavoritesUiState.Empty
                            } else {
                                FavoritesUiState.Success(dataState.data)
                            }

                            is DataState.Error -> FavoritesUiState.Error(dataState.message)
                        }
                    }
                }
        }
    }

    fun toggleFavorite(savableUniversity: SavableUniversity) {
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(savableUniversity)
        }
    }
}
