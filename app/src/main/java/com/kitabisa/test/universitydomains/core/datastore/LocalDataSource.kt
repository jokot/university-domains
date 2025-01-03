package com.kitabisa.test.universitydomains.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface LocalDataSource {
    fun getFavorites(): Flow<Set<Int>>
    suspend fun toggleFavorite(id: Int, isFavorite: Boolean)
    fun getIsFetch(): Flow<Boolean>
    suspend fun setDataIsFetch()
}

class LocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalDataSource {
    override fun getFavorites(): Flow<Set<Int>> = dataStore.data
        .map { preferences ->
            preferences[DatastoreKey.FAVORITES]
                .orEmpty()
                .mapNotNull { it.toIntOrNull() }
                .toSet()
        }

    override suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[DatastoreKey.FAVORITES].orEmpty().toMutableSet()

            if (isFavorite) {
                currentFavorites.add(id.toString())
            } else {
                currentFavorites.remove(id.toString())
            }

            preferences[DatastoreKey.FAVORITES] = currentFavorites
        }
    }

    override fun getIsFetch(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setDataIsFetch() {
        TODO("Not yet implemented")
    }

}