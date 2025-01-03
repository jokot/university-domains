package com.kitabisa.test.universitydomains.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {
    fun getFavorites(): Flow<Set<String>>
    suspend fun toggleFavorite(id: Int, isFavorite: Boolean)
    fun getIsFetch(): Flow<Boolean>
    suspend fun setDataIsFetch()
}

class LocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): LocalDataSource {
    override fun getFavorites(): Flow<Set<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getIsFetch(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setDataIsFetch() {
        TODO("Not yet implemented")
    }

}