package com.kitabisa.test.universitydomains.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitabisa.test.universitydomains.core.data.repository.HomeRepository
import com.kitabisa.test.universitydomains.core.data.state.DataState
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchUniversities()
    }

    fun fetchUniversities() {
        viewModelScope.launch {
            homeRepository.getUniversities().collect { dataState ->
                _uiState.update {
                    when (dataState) {
                        is DataState.Loading -> HomeUiState.Loading
                        is DataState.Error -> HomeUiState.Error(dataState.message)
                        is DataState.Success -> if (dataState.data.isEmpty()) {
                            HomeUiState.Empty
                        } else {
                            HomeUiState.Success(dataState.data)
                        }
                    }
                }
            }
        }
    }

    fun toggleFavorite(savableUniversity: SavableUniversity) {
        viewModelScope.launch {
            homeRepository.toggleFavorite(savableUniversity)
        }
    }
}