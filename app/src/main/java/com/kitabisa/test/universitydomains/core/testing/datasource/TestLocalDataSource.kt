package com.kitabisa.test.universitydomains.core.testing.datasource

import com.kitabisa.test.universitydomains.core.datastore.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestLocalDataSource : LocalDataSource {

    private val _favoritesFlow = MutableStateFlow<Set<String>>(emptySet())

    private val _isFetchFlow = MutableStateFlow<Boolean>(false)

    override fun getFavorites(): Flow<Set<String>> = _favoritesFlow

    override suspend fun toggleFavorite(name: String, isFavorite: Boolean) {
        _favoritesFlow.update { current ->
            if (isFavorite) current + name else current - name
        }
    }

    override fun getIsFetch(): Flow<Boolean> = _isFetchFlow

    override suspend fun setDataIsFetch() {
        _isFetchFlow.value = true
    }
}
