package com.kitabisa.test.universitydomains.core.testing.datasource

import com.kitabisa.test.universitydomains.core.datastore.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class TestLocalDataSource : LocalDataSource {

    private val _favoritesFlow = MutableStateFlow<Set<String>>(emptySet())

    private val _isFetchFlow = MutableStateFlow<Boolean>(false)

    // Flag to simulate errors
    private val _simulateError = MutableStateFlow<String?>(null)

    fun sendFavorites(favorites: Set<String>) {
        _favoritesFlow.tryEmit(favorites)
    }

    override fun getFavorites(): Flow<Set<String>> = _simulateError.flatMapLatest { error ->
        if (error != null) {
            flow { throw Exception(error) } // Emit an error flow
        } else {
            _favoritesFlow // Proceed with the actual flow
        }
    }

    override suspend fun toggleFavorite(name: String, isFavorite: Boolean) {
        _favoritesFlow.update { current ->
            if (isFavorite) current + name else current - name
        }
    }

    override fun getIsFetch(): Flow<Boolean> = _isFetchFlow

    override suspend fun setDataIsFetch() {
        _isFetchFlow.value = true
    }

    // Function to simulate an error
    fun simulateError(errorMessage: String) {
        _simulateError.value = errorMessage
    }

    // Function to clear simulated errors
    fun clearError() {
        _simulateError.value = null
    }
}
