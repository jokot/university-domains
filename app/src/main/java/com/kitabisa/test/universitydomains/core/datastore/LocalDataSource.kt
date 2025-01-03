package com.kitabisa.test.universitydomains.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface LocalDataSource {
    fun getFavorites(): Flow<Set<String>>
    suspend fun toggleFavorite(name: String, isFavorite: Boolean)
    fun getIsFetch(): Flow<Boolean>
    suspend fun setDataIsFetch()
}

class LocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalDataSource {
    override fun getFavorites(): Flow<Set<String>> = dataStore.data
        .map { preferences ->
            preferences[DatastoreKey.FAVORITES].orEmpty()
        }

    override suspend fun toggleFavorite(name: String, isFavorite: Boolean) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[DatastoreKey.FAVORITES].orEmpty().toMutableSet()

            if (isFavorite) {
                currentFavorites.add(name)
            } else {
                currentFavorites.remove(name)
            }

            preferences[DatastoreKey.FAVORITES] = currentFavorites
        }
    }

    override fun getIsFetch(): Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DatastoreKey.IS_FETCHED] ?: false
        }

    override suspend fun setDataIsFetch() {
        dataStore.edit { preferences ->
            preferences[DatastoreKey.IS_FETCHED] = true
        }
    }
}