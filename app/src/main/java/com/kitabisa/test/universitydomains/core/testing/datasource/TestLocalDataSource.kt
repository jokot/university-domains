package com.kitabisa.test.universitydomains.core.testing.datasource

import com.kitabisa.test.universitydomains.core.datastore.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestLocalDataSource : LocalDataSource {

    private val _favoritesFlow = MutableStateFlow<Set<Int>>(emptySet())

    override fun getFavorites(): Flow<Set<Int>> = _favoritesFlow

    override suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        _favoritesFlow.update { current ->
            if (isFavorite) current + id else current - id
        }
    }

    override fun getIsFetch(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setDataIsFetch() {
        TODO("Not yet implemented")
    }
}
