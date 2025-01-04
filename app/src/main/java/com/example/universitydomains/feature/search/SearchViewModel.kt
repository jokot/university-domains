package com.example.universitydomains.feature.search

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.universitydomains.core.data.repository.RecentSearchesRepository
import com.example.universitydomains.core.data.repository.SearchRepository
import com.example.universitydomains.core.data.state.DataState
import com.example.universitydomains.core.model.SavableUniversity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val recentSearchesRepository: RecentSearchesRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _query = MutableStateFlow(TextFieldValue(""))
    val query: StateFlow<TextFieldValue> = _query.asStateFlow()

    private val _searchResultUiState =
        MutableStateFlow<SearchResultUiState>(SearchResultUiState.Idle)
    val searchResultUiState: StateFlow<SearchResultUiState> = _searchResultUiState.asStateFlow()

    private val _recentSearchesUiState =
        MutableStateFlow<RecentSearchesUiState>(RecentSearchesUiState.Empty)
    val recentSearchesUiState: StateFlow<RecentSearchesUiState> =
        _recentSearchesUiState.asStateFlow()

    init {
        observeQueryChanges()
        observeRecentSearchesChanges()
    }

    fun updateQuery(newQuery: TextFieldValue) {
        _query.value = newQuery
    }

    fun onRecentSearchClick(recent: String) {
        _query.value = TextFieldValue(recent, TextRange(recent.length))
    }

    private fun observeQueryChanges() {
        viewModelScope.launch {
            _query.collectLatest { query ->
                if (query.text.isBlank()) {
                    // When query is empty, switch to idle state
                    _searchResultUiState.update {
                        SearchResultUiState.Idle
                    }
                }
            }
        }
    }

    private fun observeRecentSearchesChanges() {
        viewModelScope.launch {
            recentSearchesRepository.getRecentSearch().collect() { recentSearches ->
                _recentSearchesUiState.update {
                    if (recentSearches.isEmpty()) {
                        RecentSearchesUiState.Empty
                    } else {
                        RecentSearchesUiState.Recent(recentSearches)
                    }
                }
            }
        }
    }

    // Trigger search when the user clicks the search icon on the keyboard
    fun onSearchTriggered() {
        val currentQuery = _query.value.text
        if (currentQuery.isEmpty()) return
        performSearch(currentQuery)
    }

    // Perform a search using the query
    fun performSearch(query: String) {
        searchJob?.cancel() // Cancel previous search if any
        searchJob = viewModelScope.launch {
            searchRepository.searchUniversity(query)
                .catch { exception ->
                    _searchResultUiState.update {
                        SearchResultUiState.Error(exception.localizedMessage.orEmpty())
                    }
                }
                .collect { dataState ->
                    _searchResultUiState.update {
                        when (dataState) {
                            is DataState.Loading -> SearchResultUiState.Loading
                            is DataState.Success -> {
                                saveRecentSearch(query)
                                if (dataState.data.isEmpty()) {
                                    SearchResultUiState.Empty
                                } else {
                                    SearchResultUiState.Success(dataState.data)
                                }
                            }

                            is DataState.Error -> SearchResultUiState.Error(dataState.message)
                        }
                    }
                }
        }
    }

    fun toggleFavorite(savableUniversity: SavableUniversity) {
        viewModelScope.launch {
            searchRepository.toggleFavorite(savableUniversity)
        }
    }

    private fun saveRecentSearch(name: String) {
        viewModelScope.launch {
            recentSearchesRepository.saveRecentSearch(name)
        }
    }

    fun removeRecentSearch(name: String) {
        viewModelScope.launch {
            recentSearchesRepository.removeRecentSearch(name)
        }
    }

    fun clearRecentSearches() {
        viewModelScope.launch {
            recentSearchesRepository.clearRecentSearch()
        }
    }
}
