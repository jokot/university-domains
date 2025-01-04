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
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Loading)
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        getUniversities()
    }

    fun getUniversities() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteUniversities()
                .catch { exception ->
                    _uiState.update { FavoriteUiState.Error(exception.localizedMessage.orEmpty()) }
                }
                .collect { dataState ->
                    _uiState.update {
                        when (dataState) {
                            is DataState.Loading -> FavoriteUiState.Loading
                            is DataState.Success -> if (dataState.data.isEmpty()) {
                                FavoriteUiState.Empty
                            } else {
                                FavoriteUiState.Success(dataState.data)
                            }

                            is DataState.Error -> FavoriteUiState.Error(dataState.message)
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
